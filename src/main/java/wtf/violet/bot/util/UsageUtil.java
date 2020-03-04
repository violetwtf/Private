package wtf.violet.bot.util;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.command.CommandDetails;

public class UsageUtil {

  private MessageReceivedEvent event;
  private CommandDetails details;
  private String prefix, commandLabel;

  public UsageUtil(
      MessageReceivedEvent event, CommandDetails details, String prefix, String commandLabel
  ) {
    this.event = event;
    this.details = details;
    this.prefix = prefix;
    this.commandLabel = commandLabel;
  }

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
