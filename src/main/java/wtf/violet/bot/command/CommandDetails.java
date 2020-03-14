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

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import wtf.violet.bot.model.override.PrivateOverride;

/**
 * Details of a command.
 * @author Violet M. vi@violet.wtf
 */
public final class CommandDetails {

  private String usage;

  @Getter private ArgumentType[] argumentTypes;
  @Getter private boolean argumentCommand = false;
  @Getter private boolean sendLabel = false;
  @Getter private String description = "Uh oh! This command has no description.";
  @Getter private String[] labels;
  @Getter private boolean adminOnly = false;
  @Getter private Permission[] permissions;
  @Getter private Permission[] botPermissions;
  @Getter private PrivateOverride override;

  public CommandDetails(String... labels) {
    this.labels = labels;
  }

  public CommandDetails setAdminOnly(boolean adminOnly) {
    this.adminOnly = adminOnly;
    return this;
  }

  public CommandDetails setArgumentTypes(ArgumentType ...argumentTypes) {
    this.argumentCommand = true;
    this.argumentTypes = argumentTypes;
    return this;
  }

  public CommandDetails setUsage(String usage) {
    this.usage = usage;
    return this;
  }

  public CommandDetails setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getUsage(String prefix, String label) {
    if (usage == null) {
      return prefix + label;
    } else {
      return prefix + label + " " + usage;
    }
  }

  public CommandDetails setSendLabel(boolean sendLabel) {
    this.sendLabel = sendLabel;
    return this;
  }
  
  public CommandDetails setPermissions(Permission ...permissions) {
    this.permissions = permissions;
    botPermissions = permissions;
    return this;
  }

  public CommandDetails setBotPermissions(Permission ...botPermissions) {
    this.botPermissions = botPermissions;
    return this;
  }

  public CommandDetails setOverride(PrivateOverride override) {
    this.override = override;
    return this;
  }

}
