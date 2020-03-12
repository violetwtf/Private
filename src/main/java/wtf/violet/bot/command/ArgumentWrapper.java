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

package wtf.violet.bot.command;

import net.dv8tion.jda.api.entities.Member;

/**
 * Used to store data from arguments and pass it to commands.
 * @author Violet M. vi@violet.wtf
 */
public class ArgumentWrapper {

  private String text;
  private Member member;

  public ArgumentWrapper(String text) {
    this.text = text;
  }

  public ArgumentWrapper(Member member) {
    this.member = member;
  }

  public String getText() {
    return text;
  }

  public Member getMember() {
    return member;
  }
}
