/*
 *     Private - Discord bot that protects your server
 *     Copyright (C) 2020  Violet M. <vi@violet.wtf>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package wtf.violet.bot.service.guildsettings;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtf.violet.bot.model.GuildSettings;
import wtf.violet.bot.repository.GuildSettingsRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Definition of the GuildSettings service.
 * @author Violet M. vi@violet.wtf
 */
@Service
public final class GuildSettingsServiceImpl implements GuildSettingsService {

  @Getter private Map<Long, GuildSettings> cache = new HashMap<>();
  private Date expires = getExpires();
  @Autowired @Getter private GuildSettingsRepository repository;

  /** Returns a GuildSettings instance by its Discord ID */
  @Override
  public GuildSettings findByDiscordId(long discordId) {
    // Expire cache
    if (new Date().after(expires)) {
      cache.clear();
      expires = getExpires();
    } else if (cache.containsKey(discordId)) {
      // Else if, because if it's clear it's not gonna be in the cache
      return cache.get(discordId);
    }

    GuildSettings settings = repository.findByDiscordId(discordId);

    if (settings == null) {
      // Do default settings
      settings = new GuildSettings();
      settings.setDiscordId(discordId);
      repository.save(settings);
    }

    cache.put(discordId, settings);

    return settings;
  }

  @Override
  public void save(GuildSettings settings) {
    cache.put(settings.getDiscordId(), settings);
    repository.save(settings);
  }

  /** Returns the time the cache should expire from now. */
  private Date getExpires() {
    Date expires = new Date();
    // Expire every 10 minutes
    expires.setTime(expires.getTime() + 1000 * 60 * 60 * 10);
    return expires;
  }

}
