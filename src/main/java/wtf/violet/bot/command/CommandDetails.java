package wtf.violet.bot.command;

import net.dv8tion.jda.api.Permission;
import org.jolokia.restrictor.policy.MBeanAccessChecker;

public class CommandDetails {

  private String[] labels;
  private ArgumentType[] argumentTypes;
  private boolean adminOnly = false;
  private boolean argumentCommand = false;
  private boolean sendLabel = false;
  private String usage;
  private String description = "Uh oh! This command has no description.";
  private Permission[] permissions;

  public CommandDetails(String label) {
    this.labels = new String[]{label};
  }

  public CommandDetails(String[] labels) {
    this.labels = labels;
  }

  public String[] getLabels() {
    return labels;
  }

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

  public boolean isSendLabel() {
    return sendLabel;
  }

  public CommandDetails setPermissions(Permission ...permissions) {
    this.permissions = permissions;
    return this;
  }

  public Permission[] getPermissions() {
    return permissions;
  }
}
