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
public class HelpCommand extends Command {

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
