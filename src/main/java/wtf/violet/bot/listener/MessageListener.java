package wtf.violet.bot.listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.ArgumentType;
import wtf.violet.bot.command.ArgumentWrapper;
import wtf.violet.bot.command.Command;
import wtf.violet.bot.command.CommandDetails;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.model.GuildWhitelist;
import wtf.violet.bot.service.admin.AdminService;
import wtf.violet.bot.util.EmbedUtil;
import wtf.violet.bot.util.UsageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for messages for Private.
 * @author Violet M. vi@violet.wtf
 */
@Component
public class MessageListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    Message message = event.getMessage();
    User author = event.getAuthor();
    Bot instance = Bot.getInstance();
    String content = message.getContentStripped();
    long authorId = author.getIdLong();
    boolean authorIsAdmin = instance.getAdminService().isAdmin(author);
    AdminService adminService = instance.getAdminService();

    if (!event.isFromGuild()) {
      MessageChannel channel = event.getChannel();

      if (instance.isAdminCodeClaimable() && content.equals(instance.getAdminCode().toString())) {
        // Admin code
        Admin admin = new Admin();
        admin.setDiscordId(authorId);
        adminService.save(admin);
        instance.setAdminCodeClaimable(false);
        channel.sendMessage("You are now admin.").queue();
      } else if (authorIsAdmin && content.startsWith("whitelist")) {
        // Guild whitelist
        GuildWhitelist whitelist = new GuildWhitelist();
        long whitelistId = Long.parseLong(content.split(" ")[1]);
        whitelist.setDiscordId(whitelistId);
        instance.getGuildWhitelistRepository().save(whitelist);
        channel.sendMessage("Whitelisted " + whitelistId).queue();
      }
      return;
    }

    TextChannel channel = event.getTextChannel();

    if (event.isWebhookMessage() || author.isBot()) {
      return;
    }

    Guild guild = event.getGuild();

    String prefix =
        instance.getGuildSettingsService().findByDiscordId(guild.getIdLong()).getPrefix();

    if (content.startsWith(prefix)) {
      List<String> rawArgs =
          new ArrayList<>(List.of(content.substring(prefix.length()).split(" ")));
      String commandLabel = rawArgs.get(0);
      rawArgs.remove(0);
      Command command = instance.getCommandManager().getCommandByLabel(commandLabel);

      if (command != null) {
        CommandDetails details = command.getDetails();

        List<ArgumentWrapper> args = new ArrayList<>();

        if (details.isAdminOnly() && !authorIsAdmin) {
          return;
        }

        Member executorMember = event.getMember();
        Permission[] permissions = details.getPermissions();

        if (executorMember != null
            && permissions != null
            && !executorMember.hasPermission(details.getPermissions())) {
          return;
        }

        if (details.getBotPermissions() != null) {
          for (Permission permission : details.getBotPermissions()) {
            if (!guild.getSelfMember().hasPermission(permission)) {
              channel
                  .sendMessage(
                      EmbedUtil.getBasicEmbed(event)
                          .addField(
                              "Error",
                              "I need the permission **"
                                  + permission.getName()
                                  + "** to run this command.",
                              false)
                          .build())
                  .queue();
              return;
            }
          }
        }

        UsageUtil usageUtil = new UsageUtil(event, details, prefix, commandLabel);

        // Parse args
        if (details.isArgumentCommand()) {
          ArgumentType[] argumentTypes = details.getArgumentTypes();

          // Don't set this ==, because longtext could make it different. It just has to be at
          // LEAST that length.
          if (rawArgs.size() < argumentTypes.length) {
            usageUtil.sendUsage();
            return;
          }

          for (ArgumentType type : details.getArgumentTypes()) {
            switch (type) {
              case LONG_TEXT:
                args.add(new ArgumentWrapper(String.join(" ", rawArgs)));
                rawArgs.clear();
                break;
              case MEMBER:
                if (message.getMentionedMembers().size() > 0 && rawArgs.get(0).startsWith("@")) {
                  args.add(new ArgumentWrapper(message.getMentionedMembers().get(0)));
                  rawArgs.remove(0);
                } else {
                  usageUtil.sendUsage();
                  return;
                }
              default:
                break;
            }
          }
          command.execute(event, args);
        } else if (details.isSendLabel()) {
          command.execute(event, commandLabel, prefix);
        } else {
          command.execute(event);
        }
      }
    }
  }
}
