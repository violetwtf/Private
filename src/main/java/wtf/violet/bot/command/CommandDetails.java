package wtf.violet.bot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Details of a command.
 * @author Violet M. vi@violet.wtf
 */
public class CommandDetails {

  private String[] labels;
  private ArgumentType[] argumentTypes;
  private boolean adminOnly = false;
  private boolean argumentCommand = false;
  private boolean sendLabel = false;
  private String usage;
  private String description = "Uh oh! This command has no description.";
  private Permission[] permissions;
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

  public Permission[] getPermissions() {
    return permissions;
  }

  public Permission[] getBotPermissions() {
    return botPermissions;
  }

}
