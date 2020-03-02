package wtf.violet.bot.command.ping;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.util.EmbedUtil;

class PingEmbed {
  private String discord = "Pinging...";
  private String postgres = "Pinging...";
  private MessageReceivedEvent event;

  public PingEmbed(MessageReceivedEvent event) {
    this.event = event;
  }

  public MessageEmbed getEmbed() {
    return EmbedUtil.getBasicEmbed(event)
        .addField("Discord", discord, true)
        .addField("PostgreSQL", postgres, true)
        .build();
  }

  public MessageEmbed update(String discord, String postgres) {
    this.discord = discord;
    this.postgres = postgres;

    return getEmbed();
  }
}
