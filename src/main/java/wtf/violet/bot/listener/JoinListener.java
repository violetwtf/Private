package wtf.violet.bot.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import wtf.violet.bot.util.GuildWhitelistUtil;

import javax.annotation.Nonnull;

/**
 * Listens for GuildJoinEvent to enforce whitelist.
 * @author Violet M. vi@violet.wtf
 */
public class JoinListener extends ListenerAdapter {

  @Override
  public void onGuildJoin(@Nonnull GuildJoinEvent event) {
    GuildWhitelistUtil.check(event.getGuild());
  }
}
