package wtf.violet.bot.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

  private static Map<String, Command> commandsByLabel = new HashMap<>();

  public static void register(Command command) {
      for (String label : command.getDetails().getLabels()) {
        commandsByLabel.put(label, command);
      }
  }

  public Command getCommandByLabel(String label) {
    return commandsByLabel.get(label);
  }

}
