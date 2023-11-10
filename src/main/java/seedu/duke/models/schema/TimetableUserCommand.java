package seedu.duke.models.schema;

import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.views.TimetableView;

import java.util.ArrayList;

import static seedu.duke.models.schema.Timetable.getIndexOfModuleWeekly;
import static seedu.duke.utils.Parser.removeNulls;
import static seedu.duke.utils.TimetableParser.*;
import seedu.duke.models.schema.Timetable;

//import java.io.InvalidObjectException;


public class TimetableUserCommand {

    private static final String ERROR_INVALID_NUMBER_OF_ARGUMENTS = "Invalid Number of Arguments";

    private static final int NUMBER_OF_ARGUMENTS_EXIT = 1;
    private static final int NUMBER_OF_ARGUMENTS_CLEAR = 2;
    private static final int NUMBER_OF_ARGUMENTS_LESSON = 5;
    private static final String DELIMITER = " ";

    //private final String userTimetableInput;
    //private final String commandWord;
    private String[] arguments;
    private Student student;
    private ArrayList<ModuleWeekly> currentSemesterModulesWeekly;
    //private final boolean isValid;


    public TimetableUserCommand(Student student, ArrayList<ModuleWeekly> currentSemesterModulesWeekly,
                                String userTimetableInput)
            throws InvalidTimetableUserCommandException {
        this.student = student;
        this.currentSemesterModulesWeekly = currentSemesterModulesWeekly;
        arguments = userTimetableInput.split(DELIMITER);
        cutArguments();
        clearNullArguments();
        //cleanArguments();
    }

    private void cutArguments() throws InvalidTimetableUserCommandException {
        String[] cutArguments = new String[NUMBER_OF_ARGUMENTS_LESSON];

        int currentIndex = 0;
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = arguments[i].strip();
            if (arguments[i].isEmpty()) {
                continue;
            }
            try {
                cutArguments[currentIndex] = arguments[i];
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

    private void clearNullArguments() throws InvalidTimetableUserCommandException {
        String[] argumentsNotNull = removeNulls(arguments);
        if (!isModifyValid(arguments, currentSemesterModulesWeekly)) {
            throw new InvalidTimetableUserCommandException("Please try again");
        }
        arguments = argumentsNotNull;
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


    public void processTimetableCommand(ArrayList<ModuleWeekly> currentSemesterModulesWeekly) throws InvalidTimetableUserCommandException {
        String moduleCode = parseModuleCode(arguments[0]);
        int indexOfModuleWeeklyToModify = getIndexOfModuleWeekly(moduleCode, currentSemesterModulesWeekly);
        if (indexOfModuleWeeklyToModify == -1) {
            throw new InvalidTimetableUserCommandException(moduleCode + " does not exist in your schedule.");
        }

        if (isModifyClear(arguments)) {
            currentSemesterModulesWeekly.get(indexOfModuleWeeklyToModify).clearLessons();
            TimetableView.printTimetable(currentSemesterModulesWeekly);
            System.out.println("All lessons for selected module are cleared.");
            return;
        }

        String lessonType = parseLessonType(arguments[1]);
        int time = parseTime(arguments[2]);
        int duration = parseDuration(arguments[3]);
        String day = parseDay(arguments[4]);

        switch (lessonType) {
            case "LECTURE": {
                currentSemesterModulesWeekly.get(indexOfModuleWeeklyToModify).addLecture(day,
                        time, duration);
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                return;
            }
            case "TUTORIAL": {
                currentSemesterModulesWeekly.get(indexOfModuleWeeklyToModify).addTutorial(day,
                        time, duration);
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                return;
            }
            case "LAB": {
                currentSemesterModulesWeekly.get(indexOfModuleWeeklyToModify).addLab(day,
                        time, duration);
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                return;
            }
            default: {
                System.out.println("Invalid Command. Please try again!");
            }
        }
    }


    public String[] getArguments() {
        return arguments;
    }

    public void printArguments() {
        for (String argument : arguments) {
            System.out.println(argument);
        }
    }
}
