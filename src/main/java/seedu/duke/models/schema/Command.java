package seedu.duke.models.schema;

/**
 * Represents a command with its associated information such as command name, arguments, and description.
 * The class provides methods to retrieve details of the command and to generate a formatted string
 * for displaying the command's information.
 */
public class Command {
    private static final int JUSTIFY_LENGTH = 30;
    private final String commandName;
    private final String description;
    private final String arguments;

    //@@author janelleenqi
    /**
     * Constructs a Command object with the specified command name and description.
     *
     * @param commandName The name of the command. Must not be null.
     * @param description The description of the command. Must not be null.
     */
    public Command(String commandName, String description) {
        this.commandName = commandName;
        this.description = description;
        this.arguments = "";
    }

    /**
     * Constructs a Command object with the specified command name, arguments, and description.
     *
     * @param commandName The name of the command. Must not be null.
     * @param arguments   The arguments of the command. Must not be null.
     * @param description The description of the command. Must not be null.
     */
    public Command(String commandName, String arguments, String description) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.description = description;
    }

    //@@author janelleenqi
    /**
     * Gets the name of the command.
     *
     * @return The command name.
     */
    public String getCommandName() {
        return commandName;
    }

    //@@author janelleenqi
    /**
     * Gets the description of the command.
     *
     * @return The command description.
     */
    public String getDescription() {
        return description;
    }

    //@@author janelleenqi
    /**
     * Generates a formatted string representation of the command for display purposes.
     *
     * @return The formatted string containing the command name, arguments, and description.
     */
    @Override
    public String toString() {
        int rightPadding = JUSTIFY_LENGTH - commandName.length() - arguments.length();
        return commandName + " " + arguments + " ".repeat(rightPadding) + description;
    }
}
