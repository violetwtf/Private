package wtf.violet.bot.command.prefix;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.ArgumentType;
import wtf.violet.bot.command.ArgumentWrapper;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.model.GuildSettings;
import wtf.violet.bot.service.guildsettings.GuildSettingsServiceImpl;
import wtf.violet.bot.util.EmbedUtil;

import java.util.List;

public class PrefixCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("prefix")
        .setDescription("Set my server's prefix for Private")
        .setUsage("<prefix>")
        .setPermissions(Permission.ADMINISTRATOR)
        .setArgumentTypes(ArgumentType.LONG_TEXT)
        // Bot doesn't need anything to set its own prefix
        .setBotPermissions();
  }

  @Override
  public void execute(MessageReceivedEvent event, List<ArgumentWrapper> args) {
    GuildSettingsServiceImpl service = Bot.getInstance().getGuildSettingsService();
    GuildSettings settings = service.findByDiscordId(event.getGuild().getIdLong());

    String prefix = args.get(0).getText();

    settings.setPrefix(args.get(0).getText());
    service.save(settings);

    event.getChannel()
        .sendMessage(
            EmbedUtil.getBasicEmbed(event)
                .setDescription("Your server's prefix is now `" + prefix + "`")
                .build()
        )
        .queue();
  }

}
