package wtf.violet.bot.service.guildsettings;

import wtf.violet.bot.model.GuildSettings;
import wtf.violet.bot.repository.GuildSettingsRepository;

/** @see wtf.violet.bot.service.guildsettings.GuildSettingsServiceImpl */
public interface GuildSettingsService {

  GuildSettings findByDiscordId(long discordId);
  GuildSettingsRepository getRepository();
  void save(GuildSettings settings);

}
