package wtf.violet.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Definition of the Admin entity.
 * @author Violet M. vi@violet.wtf
 */
@Entity
public class Admin {

  @Id
  private UUID id = UUID.randomUUID();

  private long discordId;

  public void setDiscordId(long discordId) {
    this.discordId = discordId;
  }

  public long getDiscordId() {
    return discordId;
  }

  public UUID getId() {
    return id;
  }

}
