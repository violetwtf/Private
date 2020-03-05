package wtf.violet.bot.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import wtf.violet.bot.Bot;

/**
 * Tools for the guild whitelist.
 */
public class GuildWhitelistUtil {

  /**
   * Check if a guild is on the whitelist.
   * @param guild Guild to check
   */
  public static void check(Guild guild) {
    if (
        Bot.getInstance()
            .getGuildWhitelistRepository()
            .findByDiscordId(guild.getIdLong())
            == null
    ) {
      TextChannel channel = guild.getDefaultChannel();
      if (channel != null) {
        guild.getDefaultChannel()
            .sendMessage(
                "Sorry! The bot is in beta right now, so we can't let you use it yet. Stay tuned!")
            .queue();
      }
      guild.leave().queue();
    }
  }

}
