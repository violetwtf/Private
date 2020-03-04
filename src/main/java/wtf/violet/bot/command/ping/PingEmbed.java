package wtf.violet.bot.command.ping;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import wtf.violet.bot.util.EmbedUtil;

/**
 * Embed for the ping command.
 * @author Violet M. vi@violet.wtf
 */
class PingEmbed {
  private String discord = "Pinging...";
  private String postgres = "Pinging...";
  private MessageReceivedEvent event;

  /** Construct a starter PingEmbed */
  public PingEmbed(MessageReceivedEvent event) {
    this.event = event;
  }

  /** Get the stored embed */
  public MessageEmbed getEmbed() {
    return EmbedUtil.getBasicEmbed(event)
        .addField("Discord", discord, true)
        .addField("PostgreSQL", postgres, true)
        .build();
  }

  /** Update the embed and get the MessageEmbed */
  public MessageEmbed update(String discord, String postgres) {
    this.discord = discord;
    this.postgres = postgres;

    return getEmbed();
  }
}
