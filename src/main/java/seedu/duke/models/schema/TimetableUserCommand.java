package seedu.duke.models.schema;

import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;

//import seedu.duke.utils.Parser.parseArgumentsTimetableModify;

//import java.io.InvalidObjectException;


public class TimetableUserCommand {

    private static final String ERROR_INVALID_NUMBER_OF_ARGUMENTS = "Invalid Number of Arguments";
    private static final String ERROR_MODULE_DOES_NOT_EXIST = " does not exist in your schedule.";

    private static final int NUMBER_OF_ARGUMENTS_EXIT = 1;
    private static final int NUMBER_OF_ARGUMENTS_CLEAR = 2;
    private static final int NUMBER_OF_ARGUMENTS_LESSON = 5;

    //private final String userTimetableInput;
    //private final String commandWord;
    private String[] arguments;
    private Student student;
    //private final boolean isValid;


    public TimetableUserCommand(Student student, String userTimetableInput)
            throws InvalidTimetableUserCommandException {
        this.student = student;
        arguments = userTimetableInput.split(" ");
        cutArguments();
        //cleanArguments();
    }

    private void cutArguments() throws InvalidTimetableUserCommandException {
        String[] cutArguments = new String[NUMBER_OF_ARGUMENTS_LESSON];

        int currentIndex = 0;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].isEmpty()) {
                continue;
            }
            try {
                cutArguments[currentIndex] = arguments[i].strip();
                currentIndex++;
            } catch (IndexOutOfBoundsException e) {
                // too many arguments
                throw new InvalidTimetableUserCommandException(ERROR_INVALID_NUMBER_OF_ARGUMENTS);
            }
        }

        if (currentIndex == NUMBER_OF_ARGUMENTS_EXIT || currentIndex == NUMBER_OF_ARGUMENTS_CLEAR ||
                currentIndex == NUMBER_OF_ARGUMENTS_LESSON) {
            arguments = cutArguments;
            return;
        }

        // invalid number of arguments
        throw new InvalidTimetableUserCommandException(ERROR_INVALID_NUMBER_OF_ARGUMENTS);
    }

    /*
    private void cleanArguments() throws InvalidTimetableUserCommandException {
    // ROHIT HERE!!
    // check module exist in student modulesPlanned current sem
    try {
        student.getModulesPlanned().existsByCode(arguments[0]);
    } catch (InvalidObjectException e) {
        throw new InvalidTimetableUserCommandException(arguments[0] + ERROR_MODULE_DOES_NOT_EXIST);
    }
    //String[] cleanArguments = new String[NUMBER_OF_ARGUMENTS_LESSON];


    }
    */

    /*
    ROHIT
    public void processTimetableCommand()
    */

    public String[] getArguments() {
        return arguments;
    }

    public void printArguments() {
        for (String argument : arguments) {
            System.out.println(argument);
        }
    }
}



   // }


