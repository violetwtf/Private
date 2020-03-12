/*
 *     Private - Discord bot that protects your server
 *     Copyright (C) 2020  Violet M. <vi@violet.wtf>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package wtf.violet.bot;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import wtf.violet.bot.command.CommandManager;
import wtf.violet.bot.command.about.AboutCommand;
import wtf.violet.bot.command.ban.BanCommand;
import wtf.violet.bot.command.help.HelpCommand;
import wtf.violet.bot.command.eval.EvalCommand;
import wtf.violet.bot.command.ping.PingCommand;
import wtf.violet.bot.command.prefix.PrefixCommand;
import wtf.violet.bot.command.rebuild.RebuildCommand;
import wtf.violet.bot.listener.JoinListener;
import wtf.violet.bot.listener.MessageListener;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.repository.GuildWhitelistRepository;
import wtf.violet.bot.service.admin.AdminServiceImpl;
import wtf.violet.bot.service.guildsettings.GuildSettingsServiceImpl;
import wtf.violet.bot.util.GuildWhitelistUtil;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

/**
 * Private: A privacy-respecting military-like Discord bot.
 * @author Violet M.
 */
@Service
public final class Bot implements BotService {

  @Getter private static Bot instance;
  @Getter private static final String crab = "\uD83E\uDD80";

  @Getter private CommandManager commandManager = new CommandManager();
  @Getter private JDA jda;
  @Getter private UUID adminCode;
  @Getter @Setter private boolean adminCodeClaimable = false;

  @Autowired @Getter private GuildSettingsServiceImpl guildSettingsService;
  @Autowired @Getter private AdminServiceImpl adminService;
  @Autowired @Getter private GuildWhitelistRepository guildWhitelistRepository;

  /**
   * The Discord bot!
   * @throws LoginException Discord couldn't login
   */
  public Bot() throws LoginException {
    instance = this;

    CommandManager.register(new PingCommand());
    CommandManager.register(new EvalCommand());
    CommandManager.register(new HelpCommand());
    CommandManager.register(new BanCommand());
    CommandManager.register(new RebuildCommand());
    CommandManager.register(new AboutCommand());
    CommandManager.register(new PrefixCommand());

    jda = new JDABuilder()
        .setToken(System.getenv("DISCORD_TOKEN"))
        // Disable all cache flags
        .setDisabledCacheFlags(EnumSet.allOf(CacheFlag.class))
        .setEnabledCacheFlags(EnumSet.of(CacheFlag.VOICE_STATE))
        .setActivity(Activity.watching("your privacy rights!"))
        .addEventListeners(new MessageListener(), new JoinListener())
        .build();
  }

  /** Called when Spring is ready - sets up admin stuff */
  @EventListener(ApplicationReadyEvent.class)
  public void onReady() {
    List<Admin> admin = adminService.findAll();
    if (admin.size() == 0) {
      // I know it's not super secure but it's only run once ever
      adminCode = UUID.randomUUID();
      adminCodeClaimable = true;
      System.out.println("Send this code to become admin: " + adminCode);
    }

    for (Guild guild : jda.getGuilds()) {
      // So moving servers doesn't result in me getting locked out of my bot
      String officialGuildsRaw = System.getenv("GUILD_WHITELIST_BYPASS");
      String guildId = guild.getId();
      if (
          officialGuildsRaw == null ||
              !Arrays.asList(officialGuildsRaw.split(",")).contains(guildId))
      {
        GuildWhitelistUtil.check(guild);
      } else {
        System.out.println("Ignoring bypassed guild: " + guildId);
      }
    }
  }

  public boolean isProduction() {
    String env = System.getenv("ENVIRONMENT");
    return (env != null) && (env.equals("production"));
  }

  public String getVersion() {
    // Get gradle version. This is null if it's run from bootRun instead of bootJar.
    String version = getClass().getPackage().getImplementationVersion();
    return version == null ? "Development (Run from IDE)" : version;
  }

}
