package wtf.violet.bot.command.about;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

public class AboutCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("about")
        .setDescription("Information about Private");
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    event.getChannel()
        .sendMessage(
            EmbedUtil.getBasicEmbed(event)
                .addField("About", "Discord bot with military-like efficiency", false)
                .addField("Developer", "[Violet#6096](https://violet.wtf)", true)
                .addField("Version", Bot.getVersion(), true)
                .addField(
                    "Environment",
                    Bot.getInstance().isProduction() ? "Production" : "Development",
                    true
                )
                .build())
        .queue();
  }
}
