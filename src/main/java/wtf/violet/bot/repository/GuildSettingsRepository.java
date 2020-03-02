package wtf.violet.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtf.violet.bot.model.GuildSettings;

import java.util.UUID;

public interface GuildSettingsRepository extends JpaRepository<GuildSettings, UUID> {

  GuildSettings findByDiscordId(long discordId);

}
