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

package wtf.violet.bot.service.override;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtf.violet.bot.model.override.DiscordIdOverride;
import wtf.violet.bot.model.override.GuildOverride;
import wtf.violet.bot.model.override.PrivateOverride;
import wtf.violet.bot.model.override.UserOverride;
import wtf.violet.bot.repository.override.GuildOverrideRepository;
import wtf.violet.bot.repository.override.UserOverrideRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OverrideServiceImpl implements OverrideService {

  @Autowired @Getter private UserOverrideRepository userOverrideRepository;
  @Autowired @Getter private GuildOverrideRepository guildOverrideRepository;

  @Getter private final List<DiscordIdOverride> userOverrideCache = new ArrayList<>();
  @Getter private final List<DiscordIdOverride> guildOverrideCache = new ArrayList<>();

  @Override
  public boolean userHasOverride(User user, PrivateOverride override) {
    long userId = user.getIdLong();

    DiscordIdOverride genericOverride = new DiscordIdOverride(userId, override);

    if (userOverrideCache.contains(genericOverride)) {
      System.out.println("cached override");
      return true;
    }

    UserOverride userOverride = userOverrideRepository.findByDiscordIdAndOverride(
        userId, override
    );

    if (userOverride != null) {
      userOverrideCache.add(genericOverride);
      return true;
    }

    return false;
  }

  @Override
  public boolean guildOverrideEnabled(Guild guild, PrivateOverride override) {
    long guildId = guild.getIdLong();

    DiscordIdOverride genericOverride = new DiscordIdOverride(guildId, override);

    if (guildOverrideCache.contains(genericOverride)) {
      System.out.println("cached override");
      return true;
    }

    GuildOverride guildOverride = guildOverrideRepository.findByDiscordIdAndOverride(
        guildId, override
    );

    if (guildOverride != null) {
      guildOverrideCache.add(genericOverride);
      return true;
    }

    return false;
  }

  @Override
  public void addUserOverride(User user, PrivateOverride override) {
    UserOverride userOverride = new UserOverride();
    userOverride.setDiscordId(user.getIdLong());
    userOverride.setOverride(override);
    userOverrideCache.add(DiscordIdOverride.of(userOverride));
    userOverrideRepository.save(userOverride);
  }

  @Override
  public void addGuildOverride(Guild guild, PrivateOverride override) {
    GuildOverride guildOverride = new GuildOverride();
    guildOverride.setDiscordId(guild.getIdLong());
    guildOverride.setOverride(override);
    guildOverrideCache.add(DiscordIdOverride.of(guildOverride));
    guildOverrideRepository.save(guildOverride);
  }

  @Override
  public void removeUserOverride(User user, PrivateOverride override) {
    long discordId = user.getIdLong();
    userOverrideCache.remove(new DiscordIdOverride(discordId, override));
    userOverrideRepository.deleteByDiscordId(discordId);
  }

  @Override
  public void removeGuildOverride(Guild guild, PrivateOverride override) {
    long discordId = guild.getIdLong();
    guildOverrideCache.remove(new DiscordIdOverride(discordId, override));
    guildOverrideRepository.deleteByDiscordId(discordId);
  }

  @Override
  public void expireOverride(PrivateOverride override) {
    userOverrideRepository.deleteAllByOverride(override);
    guildOverrideRepository.deleteAllByOverride(override);
    flushCacheOfOverride(override, userOverrideCache);
    flushCacheOfOverride(override, guildOverrideCache);
  }

  private static void flushCacheOfOverride(
      PrivateOverride override, List<DiscordIdOverride> cache
  ) {
    cache.removeIf(genericOverride -> genericOverride.getOverride() == override);
  }

}
