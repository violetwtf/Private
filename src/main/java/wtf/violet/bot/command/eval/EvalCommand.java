package wtf.violet.bot.command.eval;

import groovy.util.Eval;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.command.ArgumentType;
import wtf.violet.bot.command.ArgumentWrapper;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

import java.util.List;

/**
 * private eval: Evaluates a line of Groovy
 * @author Violet M. vi@violet.wtf
 * @see wtf.violet.bot.command.Command
 */
public class EvalCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("eval")
        .setUsage("<code>")
        .setDescription("Evaluate some Java (Groovy) code in the context of the bot.")
        .setArgumentTypes(ArgumentType.LONG_TEXT)
        .setAdminOnly(true);
  }

  @Override
  public void execute(MessageReceivedEvent event, List<ArgumentWrapper> args) {
    String imports = getImportString(new String[]{
        "wtf.violet.bot.Bot",
        "wtf.violet.bot.repository.*",
        "wtf.violet.bot.service.guildsettings.*"
    });

    String text, name = "Result";
    String source = imports + args.get(0).getText().replaceAll(
        "```java", "").replaceAll("```", ""
    );

    try {
      text = Eval.me("event", event, source).toString();
    } catch (Exception e) {
      text = e.getMessage();
      name = "Exception";
    }

    EmbedBuilder builder = EmbedUtil.getBasicEmbed(event)
        .addField(name, code(text), false);

    event.getChannel().sendMessage(builder.build()).queue();
  }

  /** Returns the string wrapped in a Discord Java code block */
  private String code(String code) {
    return "```java\n" + code + "\n```";
  }

  /** Returns the string wrapped in "import " and ";" */
  private String getImportString(String[] packages) {
    StringBuilder end = new StringBuilder();
    for (String pack : packages) {
      end.append("import ").append(pack).append(";\n");
    }
    return end.toString();
  }

}
