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
