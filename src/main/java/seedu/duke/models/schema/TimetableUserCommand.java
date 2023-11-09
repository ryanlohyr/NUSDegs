package seedu.duke.models.schema;

import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import static seedu.duke.utils.Parser.parseArguments;
import static seedu.duke.utils.Parser.parseCommand;

public class TimetableUserCommand {

    //private final String userTimetableInput;
    private final String commandWord;
    private final String[] arguments;
    private final boolean isValid;


    public TimetableUserCommand(String userTimetableInput) {
        //this.userTimetableInput = userTimetableInput;
        commandWord = parseCommand(userTimetableInput);
        if (commandWord == null) {

        }
        arguments = parseArguments(userTimetableInput);

        /*
        //if (!commandManager.getListOfCommandNames().contains(commandWord)){
            UserError.displayInvalidInputCommand(commandWord);
            isValid = false;
            return;
        //}

        //boolean validArgument = Parser.isValidInputForCommand(commandWord, arguments);

        //if (!validArgument) {
            UserError.displayInvalidMethodCommand(commandWord);
            isValid = false;
            return;
        //}

         */

        isValid = true;
    }

    public TimetableUserCommand() {
        //userInput = null;
        commandWord = null;
        arguments = null;
        isValid = false;
    }
}
