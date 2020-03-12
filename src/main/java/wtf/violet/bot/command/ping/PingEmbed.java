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

package wtf.violet.bot.command.ping;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.util.EmbedUtil;

/**
 * Embed for the ping command.
 * @author Violet M. vi@violet.wtf
 */
final class PingEmbed {
  private String discord = "Pinging...";
  private String postgres = "Pinging...";
  private MessageReceivedEvent event;

  /** Construct a starter PingEmbed */
  public PingEmbed(MessageReceivedEvent event) {
    this.event = event;
  }

  /** Get the stored embed */
  public MessageEmbed getEmbed() {
    return EmbedUtil.getBasicEmbed(event)
        .addField("Discord", discord, true)
        .addField("PostgreSQL", postgres, true)
        .build();
  }

  /** Update the embed and get the MessageEmbed */
  public MessageEmbed update(String discord, String postgres) {
    this.discord = discord;
    this.postgres = postgres;

    return getEmbed();
  }
}
