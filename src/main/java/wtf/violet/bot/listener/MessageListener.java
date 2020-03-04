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
import wtf.violet.bot.service.admin.AdminService;
import wtf.violet.bot.util.EmbedUtil;
import wtf.violet.bot.util.UsageUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    Message message = event.getMessage();
    User author = event.getAuthor();
    Bot instance = Bot.getInstance();
    String content = message.getContentStripped();
    long authorId = author.getIdLong();
    AdminService adminService = instance.getAdminService();

    // Admin code
    if (!event.isFromGuild()) {
      if (instance.isAdminCodeClaimable() && content.equals(instance.getAdminCode().toString())) {
        Admin admin = new Admin();
        admin.setDiscordId(authorId);
        adminService.save(admin);
        instance.setAdminCodeClaimable(false);
      }
      return;
    }

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

        TextChannel channel = event.getTextChannel();

        if (details.isAdminOnly()) {
          if (!adminService.isAdmin(author)) {
            channel
                .sendMessage(
                    EmbedUtil.getBasicEmbed(event)
                        .addField("Error", "You must be admin to use this command.", false)
                        .build())
                .queue();
            return;
          }
        }

        boolean badPermissions = false;
        String whoPermissions = "You";

        Member executorMember = event.getMember();
        Permission[] permissions = details.getPermissions();

        if (executorMember != null && permissions != null) {
          for (Permission permission : permissions) {
            if (!executorMember.hasPermission(permission)) {
              badPermissions = true;
            } else if (!guild.getSelfMember().hasPermission(permission)) {
              badPermissions = true;
              whoPermissions = "I";
            }

            if (badPermissions) {
              channel
                  .sendMessage(
                      EmbedUtil.getBasicEmbed(event)
                          .addField(
                              "Error",
                              whoPermissions
                                  + " need the permission **"
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
          if (rawArgs.size() == 0) {
            usageUtil.sendUsage();
            return;
          }

          List<Member> mentionedMembers = message.getMentionedMembers();

          for (ArgumentType type : details.getArgumentTypes()) {
            switch (type) {
              case LONG_TEXT:
                args.add(new ArgumentWrapper(String.join(" ", rawArgs)));
                rawArgs.clear();
                break;
              case MEMBER:
                if (message.getMentionedMembers().size() > 0 && rawArgs.get(0).startsWith("@")) {
                  args.add(new ArgumentWrapper(mentionedMembers.get(0)));
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
