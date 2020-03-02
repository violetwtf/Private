package wtf.violet.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class EmbedUtil {

  public static EmbedBuilder getBasicEmbed(MessageReceivedEvent event) {
    User author = event.getAuthor();
    SelfUser self = event.getJDA().getSelfUser();
    Color color = Color.YELLOW;
    Member member = event.getGuild().getMember(author);

    if (member != null) {
      color = member.getColor();
    }

    return new EmbedBuilder()
        .setColor(color)
        .setAuthor(
            author.getAsTag(), "https://github.com/violetwtf/private", author.getAvatarUrl()
        )
        .setFooter(self.getAsTag(), self.getAvatarUrl());
  }

}
