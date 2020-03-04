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
