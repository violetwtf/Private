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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

/**
 * A helpful util to build embeds.
 * @author Violet M. vi@violet.wtf
 */
public class EmbedUtil {

  /**
   * Get the basic command embed.
   * @param event The MessageReceivedEvent from the command
   * @return The EmbedBuilder you can expand upon
   */
  public static EmbedBuilder getBasicEmbed(MessageReceivedEvent event) {
    User author = event.getAuthor();
    SelfUser self = event.getJDA().getSelfUser();
    Color color = Color.YELLOW;
    Member member = event.getGuild().getMember(author);

    if (member != null) {
      color = member.getColor();
    }

    return new EmbedBuilder()
        .setColor(color)
        .setAuthor(
            author.getAsTag(), "https://github.com/violetwtf/private", author.getAvatarUrl()
        )
        .setFooter(self.getAsTag(), self.getAvatarUrl());
  }

}
