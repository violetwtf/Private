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

package wtf.violet.bot.command.about;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

public final class AboutCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("about")
        .setDescription("Information about Private");
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    Bot instance = Bot.getInstance();

    event.getChannel()
        .sendMessage(
            EmbedUtil.getBasicEmbed(event)
                .addField("About", "Discord bot with military-like efficiency", false)
                .addField("Developer", "[Violet#6096](https://violet.wtf)", true)
                .addField("Version", instance.getVersion(), true)
                .build())
        .queue();
  }
}
