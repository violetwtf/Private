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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Details of a command.
 * @author Violet M. vi@violet.wtf
 */
public final class CommandDetails {

  @Getter private String[] labels;
  private ArgumentType[] argumentTypes;
  @Getter private boolean adminOnly = false;
  private boolean argumentCommand = false;
  private boolean sendLabel = false;
  private String usage;
  private String description = "Uh oh! This command has no description.";

  @Getter private Permission[] permissions;

  @Getter
  private Permission[] botPermissions;

  public CommandDetails(String... labels) {
    this.labels = labels;
  }

  public String[] getLabels() {
    return labels;
  }

  /** If the command can only be used by admins */
  public boolean isAdminOnly() {
    return adminOnly;
  }

  public CommandDetails setAdminOnly(boolean adminOnly) {
    this.adminOnly = adminOnly;
    return this;
  }

  public ArgumentType[] getArgumentTypes() {
    return argumentTypes;
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

  public String getDescription() {
    return description;
  }

  /** 
   * Returns true if it should be executed with arguments
   * @see wtf.violet.bot.command.Command#execute(MessageReceivedEvent, List)  
   */
  public boolean isArgumentCommand() {
    return argumentCommand;
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

  /**
   * Returns true if command details should be sent.
   * @see wtf.violet.bot.command.Command#execute(MessageReceivedEvent, String, String) 
   */
  public boolean isSendLabel() {
    return sendLabel;
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

}
