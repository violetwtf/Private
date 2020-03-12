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

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.command.CommandDetails;

/**
 * A nice little util to send information about command usage.
 * @author Violet M. vi@violet.wtf
 */
public class UsageUtil {

  private MessageReceivedEvent event;
  private CommandDetails details;
  private String prefix, commandLabel;

  /**
   * Construct a UsageUtil.
   * @param event MessageReceived event from the executor
   * @param details CommandDetails from the command
   * @param prefix Prefix from the executor
   * @param commandLabel Command label from the executor
   */
  public UsageUtil(
      MessageReceivedEvent event, CommandDetails details, String prefix, String commandLabel
  ) {
    this.event = event;
    this.details = details;
    this.prefix = prefix;
    this.commandLabel = commandLabel;
  }

  /** Sends the usage of a command. */
  public void sendUsage() {
    event.getChannel().sendMessage(
        EmbedUtil.getBasicEmbed(event)
            .addField("Error", "Invalid usage.", false)
            .addField(
                "Correct Usage", details.getUsage(prefix, commandLabel), false
            )
            .build()
    ).queue();
  }

}
