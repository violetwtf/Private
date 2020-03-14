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

package wtf.violet.bot.repository.override;

import org.springframework.data.jpa.repository.JpaRepository;
import wtf.violet.bot.model.override.GuildOverride;
import wtf.violet.bot.model.override.PrivateOverride;
import wtf.violet.bot.model.override.UserOverride;

import java.util.UUID;

public interface UserOverrideRepository extends JpaRepository<UserOverride, UUID> {

  UserOverride findByDiscordIdAndOverride(long discordId, PrivateOverride override);
  void deleteByDiscordId(long discordId);
  void deleteAllByOverride(PrivateOverride override);

}
