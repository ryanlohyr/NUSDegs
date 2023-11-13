package seedu.duke.models.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the command system for the application.
 * It stores and provides descriptions for each available command.
 */
public class CommandManager {
    private ArrayList<Command> commandArrayList;

    public CommandManager() {
        commandArrayList = new ArrayList<>();
        addCurrentCommands();
    }

    /**
     * Populates the commandsAndDescription HashMap with the current commands and their descriptions.
     */
    private void addCurrentCommands() {
        commandArrayList.addAll(List.of(
                new Command("help", "Shows the list of commands."),

                new Command("required", "Displays the full requirements for your major."),
                new Command("recommend", "Displays a recommended schedule based on a keyword."),

                new Command("search", "KEYWORD",
                        "Searches for modules to take based on keyword"),
                new Command("info", "COMMAND MODULE_CODE",
                        "Displays information about a specific module."),
                new Command("prereq", "MODULE_CODE",
                        "Displays the prerequisites for a specific module."),

                new Command("schedule", "Shows schedule planner"),
                new Command("add", "MODULE_CODE SEMESTER",
                        "Adds module to the schedule planner."),
                new Command("delete", "MODULE_CODE",
                        "Deletes module from the schedule planner."),
                new Command("shift", "MODULE_CODE SEMESTER",
                        "Shifts module in the schedule planner."),
                new Command("clear", "Clears all schedule planner and completion data."),

                new Command("complete", "MODULE_CODE",
                        "Marks a module as complete on schedule planner."),
                new Command("left", "Displays a list of remaining required modules."),
                new Command("pace", "[CURRENT_SEMESTER]", "Computes and displays your graduation pace."),

                new Command("timetable", "COMMAND", "Displays a grid containing this semester's classes"),

                new Command("bye", "Saves user's schedule and timetable and exits program.")));
    }


    /*
     * Retrieves the description for a specific command.
     *
     * @param command The command to retrieve the description for.
     * @return The description of the command, or "Command not recognized" if the command is not in the HashMap.
     */
    public String getDescription(String commandName) {
        for (Command command : commandArrayList) {
            if (commandName.equals(command.getCommandName())) {
                return command.getDescription();
            }
        }
        return "Command not recognized";
        //return commandArrayList.getOrDefault(command, "Command not recognized");
    }


    /**
     * Retrieves a list of all available commands.
     *
     * @return An ArrayList containing all available commands.
     */
    public ArrayList<String> getListOfCommandNames() {
        ArrayList<String> commandNameArrayList = new ArrayList<String>();
        for (Command command : commandArrayList) {
            commandNameArrayList.add(command.getCommandName());
        }
        return commandNameArrayList;
    }


    /**
     * Returns a formatted list of all commands and their descriptions.
     *
     * @return An ArrayList of strings, where each string represents a command and its description.
     */
    public ArrayList<String> printListOfCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        for (Command command : commandArrayList) {
            commandList.add(command.toString());
        }
        return commandList;
    }
}
