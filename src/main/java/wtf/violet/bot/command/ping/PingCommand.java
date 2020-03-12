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

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;

import java.util.Date;

/**
 * private ping: Calculate the ping to private's services
 * @author Violet M. vi@violet.wtf
 * @see wtf.violet.bot.command.Command
 */
public final class PingCommand extends Command {

  @Override
  public void execute(MessageReceivedEvent event) {
    long before = getTime();

    TextChannel channel = event.getTextChannel();
    PingEmbed embed = new PingEmbed(event);

    channel.sendMessage(embed.getEmbed())
        .flatMap(message -> {
          String discord = getPing(before);
          // Test postgres ping
          long postgresBefore = getTime();

          Bot.getInstance().getGuildSettingsService().getRepository().findByDiscordId(
              event.getGuild().getIdLong()
          );
          return message.editMessage(embed.update(discord, getPing(postgresBefore)));
        })
        .queue();
  }

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("ping")
        .setDescription("Calculate the ping of the bot to its services.");
  }

  /** Shorthand for new Date().getTime() */
  private long getTime() {
    return new Date().getTime();
  }

  /** Calculates the time in millis it's been since before, and adds "ms" */
  private String getPing(long before) {
    return getTime() - before + "ms";
  }

}
