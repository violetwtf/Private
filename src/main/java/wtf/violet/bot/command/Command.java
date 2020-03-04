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
