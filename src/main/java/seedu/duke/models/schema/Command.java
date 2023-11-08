package seedu.duke.models.schema;

public class Command {
    private String commandName;
    private String description;
    private String arguments;
    public Command(String commandName, String description) {
        this.commandName = commandName;
        this.description = description;
        this.arguments = "";
    }
    public Command(String commandName, String arguments, String description) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.description = description;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        int rightPadding = 50 - commandName.length() - arguments.length();
        return commandName + " " + arguments + " ".repeat(rightPadding) + description;
    }
}
