package wtf.violet.bot.command.ban;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.command.ArgumentType;
import wtf.violet.bot.command.ArgumentWrapper;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

import java.util.List;

/**
 * private ban: Bans a user from the server.
 * @author Violet M. vi@violet.wtf
 * @see wtf.violet.bot.command.Command
 */
public class BanCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("ban")
        .setDescription("Ban a user")
        .setPermissions(Permission.BAN_MEMBERS)
        .setUsage("<user> <reason>")
        .setArgumentTypes(ArgumentType.MEMBER, ArgumentType.LONG_TEXT);
  }

  @Override
  public void execute(MessageReceivedEvent event, List<ArgumentWrapper> args) {
    Guild guild = event.getGuild();
    Member member = args.get(0).getMember();
    String reason = args.get(1).getText();
    TextChannel channel = event.getTextChannel();

    if (!guild.getSelfMember().canInteract(member)) {
      channel.sendMessage(
          EmbedUtil.getBasicEmbed(event)
              .addField("Error", "I can't ban that member.", false)
              .build()
      ).queue();
      return;
    }

    boolean couldNotPm = false;

    User user = member.getUser();

    try {
      PrivateChannel privateChannel = user.openPrivateChannel().complete();
      privateChannel.sendMessage(
          buildWithReason(
              EmbedUtil.getBasicEmbed(event).addField("", guild.getName(), false),
              reason
          )
      ).complete();
    } catch (Exception e) {
      couldNotPm = true;
    }

    member.ban(7, reason).complete();

    EmbedBuilder builder = EmbedUtil.getBasicEmbed(event).addField(
        "Banned", user.getAsTag(), false
    );

    if (couldNotPm) {
      builder.addField(
          "Note", "I could not PM this user notifying them of their ban.", false
      );
    }

    channel.sendMessage(buildWithReason(builder, reason)).queue();
  }

  /**
   * Attaches a reason field to an EmbedBuilder and builds it.
   * @param builder EmbedBuilder
   * @param reason The ban reason
   * @return The built MessageEmbed
   */
  private static MessageEmbed buildWithReason(EmbedBuilder builder, String reason) {
    return builder.addField("Reason", reason, false).build();
  }

}
