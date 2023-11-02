package seedu.duke.models.schema;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the command system for the application.
 * It stores and provides descriptions for each available command.
 */
public class Command {
    private HashMap<String, String> commandsAndDescription;

    public Command() {
        commandsAndDescription = new HashMap<>();
        addCurrentCommands();
    }

    /**
     * Populates the commandsAndDescription HashMap with the current commands and their descriptions.
     */
    private void addCurrentCommands() {
        commandsAndDescription.put("info", "Displays information about a specific module.");
        commandsAndDescription.put("left", "Displays a list of remaining modules.");
        commandsAndDescription.put("pace", "Computes and displays your graduation pace.");
        commandsAndDescription.put("prereq", "Displays the prerequisites for a specific module.");
        commandsAndDescription.put("test", "Displays requirements for a specific major.");
        commandsAndDescription.put("recommend", "Displays a recommended schedule based on a keyword.");
        commandsAndDescription.put("major", "Updates and displays your current major.");
        commandsAndDescription.put("required", "Displays the full requirements for your major.");
        commandsAndDescription.put("complete", "Marks a module as complete and displays any unlocked modules.");
        commandsAndDescription.put("bye", "Exits the program");
        commandsAndDescription.put("help", "Shows the list of commands");
    }

    /**
     * Retrieves the description for a specific command.
     *
     * @param command The command to retrieve the description for.
     * @return The description of the command, or "Command not recognized" if the command is not in the HashMap.
     */
    public String getDescription(String command) {
        return commandsAndDescription.getOrDefault(command, "Command not recognized");
    }

    /**
     * Retrieves a list of all available commands.
     *
     * @return An ArrayList containing all available commands.
     */
    public ArrayList<String> getListOfCommands() {
        return new ArrayList<>(commandsAndDescription.keySet());
    }


    /**
     * Returns a formatted list of all commands and their descriptions.
     *
     * @return An ArrayList of strings, where each string represents a command and its description.
     */
    public ArrayList<String> printListOfCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        for (HashMap.Entry<String, String> entry : commandsAndDescription.entrySet()) {
            String command = entry.getKey();
            String description = entry.getValue();
            commandList.add(command + ": " + description);
        }
        return commandList;
    }
}
