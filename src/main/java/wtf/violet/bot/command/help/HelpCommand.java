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

package wtf.violet.bot.command.help;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

import java.util.Collection;

/**
 * private help: Gets help on Private commands
 * @author Violet M. vi@violet.wtf
 * @see wtf.violet.bot.command.Command
 */
public final class HelpCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("help")
        .setDescription("Help with Private's commands!")
        .setSendLabel(true);
  }

  @Override
  public void execute(MessageReceivedEvent event, String label, String prefix) {
    Bot instance = Bot.getInstance();

    Collection<Command> commands = instance.getCommandManager().getAllCommands();
    boolean authorIsAdmin = instance.getAdminService().isAdmin(event.getAuthor());

    EmbedBuilder builder = EmbedUtil.getBasicEmbed(event)
        .setDescription("Don't fear, help is here!");

    for (Command command : commands) {
      boolean isAdmin = command.getDetails().isAdminOnly();

      if (!isAdmin || authorIsAdmin) {
        CommandDetails details = command.getDetails();
        String commandLabel = details.getLabels()[0];
        builder.addField(
            details.getUsage(prefix, commandLabel),
                details.getDescription(),
            true
        );
      }
    }

    event.getChannel().sendMessage(builder.build()).queue();
  }
}
