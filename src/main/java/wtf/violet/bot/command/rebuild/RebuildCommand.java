package wtf.violet.bot.command.rebuild;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.util.EmbedUtil;

/**
 * Rebuild a channel from scratch (clear all messages).
 * @author Violet M. vi@violet.wtf
 */
public class RebuildCommand extends Command {

  @Override
  public CommandDetails getDetails() {
    return new CommandDetails("rebuild")
        .setDescription("Rebuild this channel from scratch")
        .setPermissions(Permission.MANAGE_CHANNEL);
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    Guild guild = event.getGuild();
    TextChannel oldChannel = event.getTextChannel();
    oldChannel.delete().queue();
    int position = oldChannel.getPosition();

    guild.createCopyOfChannel(oldChannel)
        .setName(oldChannel.getName())
        .flatMap(channel -> {
          Category parent = oldChannel.getParent();

          ChannelOrderAction action = guild.modifyTextChannelPositions();

          if (parent != null) {
            action = parent.modifyTextChannelPositions();
          }

          action.selectPosition(channel).moveTo(position).queue();

          return channel.sendMessage(
              EmbedUtil.getBasicEmbed(event)
              .addField(
                  "Re-created!",
                  "This channel was recreated successfully. "+
                      "This message will be deleted in 5 seconds.",
                  false
              )
              .build()
          );
        })
        .flatMap(message -> {
          AuditableRestAction<Void> action;

          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            // This is here because IntelliJ gets mad at returning here
            action = message.delete();
          }

          return action;
        })
        .queue();
  }

}
