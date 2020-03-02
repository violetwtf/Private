package wtf.violet.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.util.EmbedUtil;

import java.util.List;

public class Command {

  private CommandDetails details;

  public void execute(MessageReceivedEvent event) {
    event.getChannel().sendMessage(
        EmbedUtil.getBasicEmbed(event).addField(
            "Uh oh!", "This command hasn't been implemented.", false
        ).build()
    ).queue();
  }

  public void execute(MessageReceivedEvent event, List<ArgumentWrapper> args) {
    execute(event);
  }

  public CommandDetails getDetails() {
    return details;
  }
}
