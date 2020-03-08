package wtf.violet.bot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
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
@EnableAdminServer
public class Bot implements BotService {

  private static Bot instance;

  @Autowired
  private GuildSettingsServiceImpl guildSettingsService;
  @Autowired
  private AdminServiceImpl adminService;
  @Autowired
  private GuildWhitelistRepository guildWhitelistRepository;

  private UUID adminCode;
  private boolean adminCodeClaimable = false;

  private CommandManager commandManager = new CommandManager();
  private JDA jda;

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

  public static Bot getInstance() {
    return instance;
  }

  public GuildSettingsServiceImpl getGuildSettingsService() {
    return guildSettingsService;
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public AdminServiceImpl getAdminService() {
    return adminService;
  }

  public UUID getAdminCode() {
    return adminCode;
  }

  public boolean isAdminCodeClaimable() {
    return adminCodeClaimable;
  }

  public void setAdminCodeClaimable(boolean adminCodeClaimable) {
    this.adminCodeClaimable = adminCodeClaimable;
  }

  public GuildWhitelistRepository getGuildWhitelistRepository() {
    return guildWhitelistRepository;
  }

  public JDA getJda() {
    return jda;
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

  public String getCrab() {
    return "\uD83E\uDD80"; // 🦀🦀🦀🦀Fine, I'll give in.
  }

}
