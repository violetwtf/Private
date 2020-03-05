package wtf.violet.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Definition of the GuildSettings entity.
 * @author Violet M. vi@violet.wtf
 */
@Entity
public class GuildSettings {

  @Id
  private UUID id = UUID.randomUUID();

  private long discordId;
  private String prefix = "private ";

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public long getDiscordId() {
    return discordId;
  }

  public void setDiscordId(long discordId) {
    this.discordId = discordId;
  }

}
