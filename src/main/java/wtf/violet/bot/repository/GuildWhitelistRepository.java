package wtf.violet.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtf.violet.bot.model.GuildWhitelist;

import java.util.UUID;

public interface GuildWhitelistRepository extends JpaRepository<GuildWhitelist, UUID> {

  GuildWhitelist findByDiscordId(long discordId);
  void removeByDiscordId(long discordId);

}
