/*
 *     Private - Discord bot that protects your server
 *     Copyright (C) 2020  Violet M. <vi@violet.wtf>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package wtf.violet.bot.listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wtf.violet.bot.Bot;
import wtf.violet.bot.command.*;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.model.GuildWhitelist;
import wtf.violet.bot.model.override.PrivateOverride;
import wtf.violet.bot.service.admin.AdminService;
import wtf.violet.bot.service.override.OverrideService;
import wtf.violet.bot.util.EmbedUtil;
import wtf.violet.bot.util.UsageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for messages for Private.
 * @author Violet M. vi@violet.wtf
 */
@Component
public final class MessageListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    User author = event.getAuthor();

    if (event.isWebhookMessage() || author.isBot()) {
      return;
    }

    Message message = event.getMessage();
    Bot instance = Bot.getInstance();
    String content = message.getContentStripped();
    long authorId = author.getIdLong();
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
      } else if (adminService.isAdmin(author) && content.startsWith("whitelist")) {
        // Guild whitelist
        GuildWhitelist whitelist = new GuildWhitelist();
        long whitelistId = Long.parseLong(content.split(" ")[1]);
        whitelist.setDiscordId(whitelistId);
        instance.getGuildWhitelistRepository().save(whitelist);
        channel.sendMessage("Whitelisted " + whitelistId).queue();
      }
      return;
    }

    Guild guild = event.getGuild();

    String prefix =
        instance.getGuildSettingsService().findByDiscordId(guild.getIdLong()).getPrefix();

    List<Member> mentionedMembers = message.getMentionedMembers();
    Member selfMember = guild.getSelfMember();

    // https://github.com/meew0/discord-bot-best-practices Rule 10
    if (
        mentionedMembers.size() == 1 &&
            mentionedMembers.get(0) == selfMember &&
            content.startsWith("@")
    ) {
      executeCommand(event, "help", prefix, new ArrayList<>());
    }

    if (content.startsWith(prefix)) {
      List<String> rawArgs =
          new ArrayList<>(List.of(content.substring(prefix.length()).split(" ")));
      String commandLabel = rawArgs.get(0);
      rawArgs.remove(0);

      executeCommand(event, commandLabel, prefix, rawArgs);
    }
  }

  private static void executeCommand(
      MessageReceivedEvent event, String commandLabel, String prefix, List<String> rawArgs
  ) {
    Bot instance = Bot.getInstance();
    Command command = instance.getCommandManager().getCommandByLabel(commandLabel);
    User author = event.getAuthor();
    Guild guild = event.getGuild();

    if (command != null) {
      CommandDetails details = command.getDetails();

      List<ArgumentWrapper> args = new ArrayList<>();

      if (details.isAdminOnly() && !instance.getAdminService().isAdmin(author)) {
        return;
      }

      PrivateOverride override = details.getOverride();
      OverrideService overrideService = instance.getOverrideService();

      if (
          override != null &&
              !(overrideService.guildOverrideEnabled(guild, override)
                  && overrideService.userHasOverride(author, override))
      ) {
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
            event.getChannel()
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
              List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

              if (mentionedMembers.size() > 0 && rawArgs.get(0).startsWith("@")) {
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