package seedu.duke.utils;

import seedu.duke.models.schema.ModuleWeekly;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;


import java.util.ArrayList;
import java.util.List;

import static seedu.duke.utils.Parser.removeNulls;
import static seedu.duke.utils.Parser.hasNoNulls;
import static seedu.duke.utils.Parser.isStringInteger;
import static seedu.duke.utils.Parser.isValidLessonType;
import static seedu.duke.utils.Parser.isDayValid;

public class TimetableParser {
    private static final String ERROR_INVALID_LESSON_TYPE = "Invalid Lesson Type. Lesson Types available: Lecture, " +
            "Tutorial, Lab. ";

    private static final String ERROR_TIME_TYPE_IS_WRONG = "Invalid Time. Time should be a integer from 5 to 23. " +
            "5 represents 5am, 23 represents 11pm";

    private static final String ERROR_DURATION_TYPE_IS_WRONG = "Invalid Duration. Duration should be a integer " +
            "representing the number of hours.";
    private static final String ERROR_INVALID_DAY = "Invalid Day. Examples of day: Monday, Tuesday, Wednesday. " +
            "representing the number of hours.";

    private static final List<String> lessonTypes = List.of("LECTURE", "TUTORIAL", "LAB");
    private static final List<String> days = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
            "SATURDAY", "SUNDAY");

    private static final String DELIMITER = " ";


    /**
     * Checks if the provided array of arguments indicates an "EXIT" command for modifying the timetable.
     *
     * @author @rohitcube
     * @param arguments An array of strings representing user input arguments.
     * @return true if the command is to exit the modify mode, false otherwise.
     */
    public static boolean isExitModify(String[] arguments) {
        String[] argumentsNoNulls = removeNulls(arguments);
        if ((argumentsNoNulls.length == 1) && argumentsNoNulls[0].strip().equalsIgnoreCase("EXIT")) {
            return true;
        }
        return false;
    }


    /**
     * Checks if the provided array of arguments indicates a "clear" command for modifying the timetable.
     *
     * @author @rohitcube
     * @param arguments An array of strings representing user input arguments.
     * @return true if the command is to clear a modification, false otherwise.
     */
    public static boolean isModifyClear(String[] arguments) {
        try {
            if (arguments[2] != null || !arguments[2].isEmpty()) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e ) {
            System.out.println();
        }
        if (arguments[1].strip().equalsIgnoreCase("clear")) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the provided array of arguments is valid for modifying the timetable and throws exceptions if not.
     *
     * @author @rohitcube
     * @param arguments               An array of strings representing user input arguments.
     * @param currentSemesterModulesWeekly A list of ModuleWeekly objects representing modules in the current semester.
     * @return true if the modification is valid, false otherwise.
     * @throws InvalidTimetableUserCommandException If the arguments are invalid.
     */
    public static boolean isModifyValid(String[] arguments, ArrayList <ModuleWeekly> currentSemesterModulesWeekly)
            throws InvalidTimetableUserCommandException {
        String[] argumentsNoNulls = removeNulls(arguments);
        if (!hasNoNulls(arguments)) {
            throw new InvalidTimetableUserCommandException("Invalid number of arguments");
        }
        if (argumentsNoNulls.length == 1) {
            if (!arguments[0].strip().equalsIgnoreCase("EXIT")) {
                throw new InvalidTimetableUserCommandException("Invalid argument");
            }
        }
        if (argumentsNoNulls.length == 2) {
            String moduleCode = arguments[0].toUpperCase();
            if (!isExistInCurrentSemesterModules(moduleCode, currentSemesterModulesWeekly)) {
                throw new InvalidTimetableUserCommandException("Module does not exist in current semester.");
            }
            if (!argumentsNoNulls[1].strip().equalsIgnoreCase("clear")) {
                throw new InvalidTimetableUserCommandException("Invalid argument");
            }
            return true;
        }

        if (argumentsNoNulls.length == 5) {
            String moduleCode = arguments[0].toUpperCase();
            String lessonType = arguments[1].toUpperCase();
            String timeString = arguments[2];
            String durationString = arguments[3];
            String day = arguments[4].toUpperCase();
            if (!isExistInCurrentSemesterModules(moduleCode, currentSemesterModulesWeekly)) {
                throw new InvalidTimetableUserCommandException("Module does not exist in current semester");
            }
            if (!isValidLessonType(lessonType)) {
                throw new InvalidTimetableUserCommandException("Invalid lesson type");
            }
            if (!isStringInteger(timeString)) {
                throw new InvalidTimetableUserCommandException("Input for time is not an integer");
            }
            int time = Integer.parseInt(timeString);
            if (time < 5 || time > 23) {
                throw new InvalidTimetableUserCommandException("Input for time is outside the valid range. " +
                        "Please try again!");
            }
            if (!isStringInteger(durationString)) {
                throw new InvalidTimetableUserCommandException("Input for duration is not an integer");
            }
            int duration = Integer.parseInt(durationString);
            if (duration < 0) {
                throw new InvalidTimetableUserCommandException("Input for duration must be at least 0");
            }
            if (duration > 23 - time) {
                throw new InvalidTimetableUserCommandException("Input for duration exceeds valid hours" +
                        " on the timetable");
            }
            if (!isDayValid(day)) {
                throw new InvalidTimetableUserCommandException("Invalid day");
            }
            return true;
        }
        return false;
    }

    //@@author janelleenqi
    /**
     * Checks if a module with a given module code exists in the current semester modules.
     * @author @rohitcube
     * @param moduleCode                   The module code to search for.
     * @param currentSemesterModulesWeekly The list of ModuleWeekly objects for the current semester.
     * @return true if the module exists, false otherwise.
     */
    public static boolean isExistInCurrentSemesterModules(String moduleCode,
                                                          ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        for (ModuleWeekly module : currentSemesterModulesWeekly) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    // if return true,
    public static boolean validateClearCommand(String[] argument,
                                               ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        if (isExistInCurrentSemesterModules(argument[0].strip().toUpperCase(), currentSemesterModulesWeekly) &&
                argument[1].strip().equalsIgnoreCase("clear")) {
            System.out.println(argument[0].strip().toUpperCase());
            System.out.println(argument[1].strip().toUpperCase());
            System.out.println("Module does not exist in current semester.");
            System.out.println("validate clear.");
            return true;
        }
        return false;
    }

    /**
     * Parses the module code by converting it to uppercase.
     *
     * @param supposedModuleCode The supposed module code to be parsed.
     * @return The parsed module code in uppercase.
     */
    public static String parseModuleCode(String supposedModuleCode) {
        supposedModuleCode = supposedModuleCode.toUpperCase();

        return supposedModuleCode;
    }

    /**
     * Parses the lesson type by converting it to uppercase and validating its existence in the predefined list.
     *
     * @param supposedLesson The supposed lesson type to be parsed.
     * @return The parsed lesson type in uppercase.
     * @throws InvalidTimetableUserCommandException If the lesson type is invalid.
     */
    public static String parseLessonType(String supposedLesson) throws InvalidTimetableUserCommandException {
        supposedLesson = supposedLesson.toUpperCase();
        if (!lessonTypes.contains(supposedLesson)) {
            throw new InvalidTimetableUserCommandException(ERROR_INVALID_LESSON_TYPE);
        }

        return supposedLesson;
    }

    /**
     * Parses the time by converting it to an integer.
     *
     * @param supposedTime The supposed time to be parsed.
     * @return The parsed time as an integer.
     * @throws InvalidTimetableUserCommandException If the time format is incorrect.
     */
    public static int parseTime(String supposedTime) throws InvalidTimetableUserCommandException {
        try {
            return Integer.parseInt(supposedTime);
        } catch (NumberFormatException e) {
            throw new InvalidTimetableUserCommandException(ERROR_TIME_TYPE_IS_WRONG);
        }
    }

    /**
     * Parses the duration by converting it to an integer.
     *
     * @param supposedDuration The supposed duration to be parsed.
     * @return The parsed duration as an integer.
     * @throws InvalidTimetableUserCommandException If the duration format is incorrect.
     */
    public static int parseDuration(String supposedDuration) throws InvalidTimetableUserCommandException {
        try {
            return Integer.parseInt(supposedDuration);
        } catch (NumberFormatException e) {
            throw new InvalidTimetableUserCommandException(ERROR_DURATION_TYPE_IS_WRONG);
        }
    }

    /**
     * Parses the day by converting it to uppercase and validating its existence in the predefined list.
     *
     * @param supposedDay The supposed day to be parsed.
     * @return The parsed day in uppercase.
     * @throws InvalidTimetableUserCommandException If the day is invalid.
     */
    public static String parseDay(String supposedDay) throws InvalidTimetableUserCommandException {
        supposedDay = supposedDay.toUpperCase();
        if (!days.contains(supposedDay)) {
            throw new InvalidTimetableUserCommandException(ERROR_INVALID_DAY);
        }

        return supposedDay;
    }



}
