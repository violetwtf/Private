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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This holds all of the Commands in private.
 * @author Violet M. vi@violet.wtf
 */
public class CommandManager {

  private static Map<String, Command> commandsByLabel = new HashMap<>();

  /** Register a command */
  public static void register(Command command) {
      for (String label : command.getDetails().getLabels()) {
        commandsByLabel.put(label, command);
      }
  }

  public Command getCommandByLabel(String label) {
    return commandsByLabel.get(label);
  }

  public Collection<Command> getAllCommands() {
    return commandsByLabel.values();
  }

}
