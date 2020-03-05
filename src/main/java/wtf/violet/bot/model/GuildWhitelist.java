package wtf.violet.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Guild whitelist.
 * @author Violet M. vi@violet.wtf
 */
@Entity
public class GuildWhitelist {

  @Id
  private UUID id = UUID.randomUUID();
  private long discordId;

  public UUID getId() {
    return id;
  }

  public long getDiscordId() {
    return discordId;
  }

  public void setDiscordId(long discordId) {
    this.discordId = discordId;
  }

}
