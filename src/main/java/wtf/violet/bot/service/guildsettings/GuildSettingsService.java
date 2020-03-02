package wtf.violet.bot.service.guildsettings;

import wtf.violet.bot.model.GuildSettings;

public interface GuildSettingsService {
  GuildSettings findByDiscordId(long discordId);
}
