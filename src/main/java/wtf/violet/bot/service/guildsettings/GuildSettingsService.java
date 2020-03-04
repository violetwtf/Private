package wtf.violet.bot.service.guildsettings;

import wtf.violet.bot.model.GuildSettings;

/** @see wtf.violet.bot.service.guildsettings.GuildSettingsServiceImpl */
public interface GuildSettingsService {
  GuildSettings findByDiscordId(long discordId);
}
