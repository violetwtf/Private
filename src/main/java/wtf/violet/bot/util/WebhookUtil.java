package wtf.violet.bot.util;

import club.minnced.discord.webhook.WebhookClient;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;

import java.util.Objects;

public class WebhookUtil {

  public static WebhookClient getChannelWebhookClient(TextChannel channel) {
    Guild guild = channel.getGuild();
    Webhook finalWebhook = null;

    for (Webhook webhook : guild.retrieveWebhooks().complete()) {
      if (webhook.getOwner() == guild.getSelfMember() && webhook.getChannel() == channel) {
        finalWebhook = webhook;
      }
    }

    if (finalWebhook == null) {
      finalWebhook = channel.createWebhook("Private #" + channel.getName()).complete();
    }

    return WebhookClient.withId(
        finalWebhook.getIdLong(),
        Objects.requireNonNull(finalWebhook.getToken())
    );
  }

}
