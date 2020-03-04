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
public class PingCommand extends Command {

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
