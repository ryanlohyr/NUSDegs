package seedu.duke.models.schema;

import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import static seedu.duke.utils.Parser.parseArguments;
import static seedu.duke.utils.Parser.parseCommand;

public class UserCommand implements UserCommandWord {

    private String userInput;
    private String commandWord;
    private String[] arguments;
    private boolean isValid;

    private CommandManager commandManager;

    public UserCommand(String userInput) {
        this.userInput = userInput;
        commandWord = parseCommand(userInput);
        arguments = parseArguments(userInput);
        isValid = true;
        this.commandManager = new CommandManager();

        if (!commandManager.getListOfCommandNames().contains(commandWord)){
            UserError.displayInvalidInputCommand(commandWord);
            isValid = false;
        } else {

            boolean validInput = Parser.isValidInputForCommand(commandWord, arguments);

            if (!validInput) {
                UserError.displayInvalidMethodCommand(commandWord);
                isValid = false;

            }
        }
    }

    public UserCommand() {
        userInput = null;
        commandWord = null;
        arguments = null;
        isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean isBye() {
        if (commandWord == null) {
            return false;
        }
        return this.commandWord.equals(UserCommandWord.EXIT_COMMAND);
    }

}
