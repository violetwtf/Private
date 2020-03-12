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

package wtf.violet.bot.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import wtf.violet.bot.util.GuildWhitelistUtil;

import javax.annotation.Nonnull;

/**
 * Listens for GuildJoinEvent to enforce whitelist.
 * @author Violet M. vi@violet.wtf
 */
public class JoinListener extends ListenerAdapter {

  @Override
  public void onGuildJoin(@Nonnull GuildJoinEvent event) {
    GuildWhitelistUtil.check(event.getGuild());
  }
}
