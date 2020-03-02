package wtf.violet.bot.service.guildsettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtf.violet.bot.model.GuildSettings;
import wtf.violet.bot.repository.GuildSettingsRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class GuildSettingsServiceImpl implements GuildSettingsService {

  // So we don't have to query the DB every time
  private Map<Long, GuildSettings> settingsCache = new HashMap<>();
  // TODO EVENTUALLY: Expire individual keys by guild last used time
  private Date expires = getExpires();

  @Autowired
  private GuildSettingsRepository repository;

  @Override
  public GuildSettings findByDiscordId(long discordId) {
    // Expire cache
    if (new Date().after(expires)) {
      settingsCache.clear();
      expires = getExpires();
    } else if (settingsCache.containsKey(discordId)) {
      // Else if, because if it's clear it's not gonna be in the cache
      return settingsCache.get(discordId);
    }

    GuildSettings settings = repository.findByDiscordId(discordId);

    if (settings == null) {
      // Do default settings
      settings = new GuildSettings();
      settings.setDiscordId(discordId);
      repository.save(settings);
    }

    settingsCache.put(discordId, settings);

    return settings;
  }

  public GuildSettingsRepository getRepository() {
    return repository;
  }

  private Date getExpires() {
    Date expires = new Date();
    // Expire every 10 minutes
    expires.setTime(expires.getTime() + 1000 * 60 * 60 * 10);
    return expires;
  }

  public Map<Long, GuildSettings> getCache() {
    return settingsCache;
  }
}
