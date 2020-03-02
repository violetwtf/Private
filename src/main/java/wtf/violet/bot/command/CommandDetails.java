package wtf.violet.bot.command;

import org.jolokia.restrictor.policy.MBeanAccessChecker;

public class CommandDetails {

  private String[] labels;
  private ArgumentType[] argumentTypes;
  private boolean adminOnly = false;
  private boolean argumentCommand = false;
  private String usage;

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

  public CommandDetails setArgumentTypes(ArgumentType[] argumentTypes) {
    this.argumentCommand = true;
    this.argumentTypes = argumentTypes;
    return this;
  }

  public CommandDetails setArgumentType(ArgumentType argumentType) {
    return setArgumentTypes(new ArgumentType[]{argumentType});
  }

  public CommandDetails setUsage(String usage) {
    this.usage = usage;
    return this;
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
}
