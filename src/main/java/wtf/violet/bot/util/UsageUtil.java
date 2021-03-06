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
