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
    private static final String ERROR_MODULE_DOES_NOT_EXIST = " does not exist in your schedule.";
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

    // returns true when exit is called
    public static boolean isExitModify(String[] arguments) {
        String[] argumentsNoNulls = removeNulls(arguments);
        if ((argumentsNoNulls.length == 1) && argumentsNoNulls[0].strip().equalsIgnoreCase("EXIT")) {
            return true;
        }
        return false;
    }

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
            //System.out.println("inside clear if");

            //System.out.println(indexOfModuleWeeklyToModify);
            //line that's causing error

            //System.out.println("removed lesson");

            //System.out.println("All lessons for selected module are cleared.");
        }

        return false;
    }

    public static boolean isModifyValid(String[] arguments, ArrayList <ModuleWeekly> currentSemesterModulesWeekly)
            throws InvalidTimetableUserCommandException {
        // the check for number of valid arguments is already done, its not
        // check if any of the arguments are null
        // not putting in empty checks
        String[] argumentsNoNulls = removeNulls(arguments);
        if (!hasNoNulls(arguments)) {
            throw new InvalidTimetableUserCommandException("Invalid number of arguments");
            //  System.out.println("Invalid number of arguments");
            //  return false;
        }
        if (argumentsNoNulls.length == 1) {
            if (!arguments[0].strip().equalsIgnoreCase("EXIT")) {
                throw new InvalidTimetableUserCommandException("Invalid argument");
                //     System.out.println("Invalid argument.");
                //     return false;
            }
            // return true;
        }
        if (argumentsNoNulls.length == 2) {
            String moduleCode = arguments[0].toUpperCase();
            if (!isExistInCurrentSemesterModules(moduleCode, currentSemesterModulesWeekly)) {
                throw new InvalidTimetableUserCommandException("Module does not exist in current semester.");
                //System.out.println("Module does not exist in current semester. ");
                //return false;
            }
            if (!argumentsNoNulls[1].strip().equalsIgnoreCase("clear")) {
                throw new InvalidTimetableUserCommandException("Invalid argument");
                //System.out.println("Invalid argument" + arguments[1]);
                //return false;
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
                //System.out.println("Module does not exist in current semester.");
                //return false;
            }
            if (!isValidLessonType(lessonType)) {
                throw new InvalidTimetableUserCommandException("Invalid lesson type");
                //System.out.println("Invalid lesson type");
                //return false;
            }
            if (!isStringInteger(timeString)) {
                throw new InvalidTimetableUserCommandException("Input for time is not an integer");
                //System.out.println("Input for time is not an integer");
                //return false;
            }
            int time = Integer.parseInt(timeString);
            if (time < 5 || time > 23) {
                throw new InvalidTimetableUserCommandException("Time not within the valid range. Please try again!");
                // System.out.println("Time not within the valid range. Please try again!");
                //return false;
            }
            if (!isStringInteger(durationString)) {
                throw new InvalidTimetableUserCommandException("Input for duration is not an integer");
                //System.out.println("Input for duration is not an integer");
                //return false;
            }
            int duration = Integer.parseInt(durationString);
            if (duration < 0) {
                throw new InvalidTimetableUserCommandException("Input for duration must be at least 0");
            }
            if (duration > 23 - time) {
                throw new InvalidTimetableUserCommandException("Input for duration exceeds valid hours" +
                        " on the timetable");
                //System.out.println("Input for duration exceeds valid hours on the timetable");
                //return false;
            }
            if (!isDayValid(day)) {
                throw new InvalidTimetableUserCommandException("Invalid number of arguments");
                //System.out.println("Invalid input for day.");
                //return false;
            }
            return true;
        }
        return false;
    }

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

    public static String parseModuleCode(String supposedModuleCode) {
        supposedModuleCode = supposedModuleCode.toUpperCase();

        return supposedModuleCode;
    }

    public static String parseLessonType(String supposedLesson) throws InvalidTimetableUserCommandException {
        supposedLesson = supposedLesson.toUpperCase();
        if (!lessonTypes.contains(supposedLesson)) {
            throw new InvalidTimetableUserCommandException(ERROR_INVALID_LESSON_TYPE);
        }

        return supposedLesson;
    }

    public static int parseTime(String supposedTime) throws InvalidTimetableUserCommandException {
        try {
            return Integer.parseInt(supposedTime);
        } catch (NumberFormatException e) {
            throw new InvalidTimetableUserCommandException(ERROR_TIME_TYPE_IS_WRONG);
        }
    }

    public static int parseDuration(String supposedDuration) throws InvalidTimetableUserCommandException {
        try {
            return Integer.parseInt(supposedDuration);
        } catch (NumberFormatException e) {
            throw new InvalidTimetableUserCommandException(ERROR_DURATION_TYPE_IS_WRONG);
        }
    }

    public static String parseDay(String supposedDay) throws InvalidTimetableUserCommandException {
        supposedDay = supposedDay.toUpperCase();
        if (!days.contains(supposedDay)) {
            throw new InvalidTimetableUserCommandException(ERROR_INVALID_DAY);
        }

        return supposedDay;
    }



}
