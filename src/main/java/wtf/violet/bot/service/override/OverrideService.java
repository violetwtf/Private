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

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import wtf.violet.bot.model.override.DiscordIdOverride;
import wtf.violet.bot.model.override.PrivateOverride;
import wtf.violet.bot.repository.override.GuildOverrideRepository;
import wtf.violet.bot.repository.override.UserOverrideRepository;

import java.util.List;

public interface OverrideService {

  boolean userHasOverride(User user, PrivateOverride override);
  boolean guildOverrideEnabled(Guild guild, PrivateOverride override);

  void addUserOverride(User user, PrivateOverride override);
  void addGuildOverride(Guild guild, PrivateOverride override);
  void removeUserOverride(User user, PrivateOverride override);
  void removeGuildOverride(Guild guild, PrivateOverride override);

  void expireOverride(PrivateOverride override);

  UserOverrideRepository getUserOverrideRepository();
  GuildOverrideRepository getGuildOverrideRepository();

  List<DiscordIdOverride> getUserOverrideCache();
  List<DiscordIdOverride> getGuildOverrideCache();

}
