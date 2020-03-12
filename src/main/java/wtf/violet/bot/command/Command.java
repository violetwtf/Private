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

package wtf.violet.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.util.EmbedUtil;

import java.util.List;

/**
 * The definition of a command in private, an executor and details.
 * @author Violet M. vi@violet.wtf
 */
public class Command {

  /**
   * Called to execute the command.
   * @param event MessageReceivedEvent
   */
  public void execute(MessageReceivedEvent event) {
    event.getChannel().sendMessage(
        EmbedUtil.getBasicEmbed(event).addField(
            "Uh oh!", "This command hasn't been implemented.", false
        ).build()
    ).queue();
  }

  /** Alternative definition for passing arguments */
  public void execute(MessageReceivedEvent event, List<ArgumentWrapper> args) {
    execute(event);
  }

  /** Alternative definition for passing command info */
  public void execute(MessageReceivedEvent event, String label, String prefix) {
    execute(event);
  }

  /**
   * Get this command's CommandDetails.
   * @return CommandDetails
   */
  public CommandDetails getDetails() {
    return new CommandDetails("error").setDescription("An error has occurred.");
  }
}
