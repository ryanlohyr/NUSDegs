package seedu.duke.utils;

import seedu.duke.models.schema.Major;
import seedu.duke.models.schema.UserCommandWord;
import seedu.duke.utils.errors.UserError;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    public static final String DELIMITER = " ";

    /**
     * Parses a user input string to extract and return the main command.
     * @author ryanlohyr
     * @param userInput The user input string.
     * @return The main command from the input string.
     */
    public static String parseCommand(String userInput) {
        if (userInput == null || userInput.isEmpty()) {
            return null;
        }
        String[] keywords = userInput.split(DELIMITER);

        return keywords[0].toLowerCase().trim();
    }

    //public static String parseTimetable

    /**
     * Excludes the command and extracts and returns an array of arguments from a user input string.
     * @author ryanlohyr
     * @param userInput The user input string.
     * @return An array of arguments from the input string.
     */
    public static String[] parseArguments(String userInput){
        if (userInput.isEmpty()){
            return null;
        }
        String[] keywords = userInput.split(DELIMITER);

        return Arrays.copyOfRange(keywords, 1, keywords.length);
    }


    /**
     * Checks if the given academic year input is valid.
     * The academic year should be in the format "Yn/Sx", where 'n' represents the year
     * and 'x' represents the semester (e.g., Y1/S1, Y2/S2).
     *
     *
     * @author @ryanlohyr
     * @param userInput The academic year input to be validated.
     * @return true if the input is a valid academic year, false otherwise.
     *
     * @throws IllegalArgumentException if the input format is incorrect or if the year or semester is invalid.
     *
     */
    public static boolean isValidAcademicYear( String userInput ) {
        try {
            String[] parts = userInput.split("/");
            if(parts.length != 2){
                throw new IllegalArgumentException("Needs to be in format of Y2/S1");
            }
            String year = parts[0].toUpperCase();
            String semester = parts[1].toUpperCase();

            //last year
            if(year.equals("Y4") && semester.equals("S2")){
                throw new IllegalArgumentException("Its your last sem!! A bit too late ya....");
            }
            //validate semester
            if(!semester.equals("S1") && !semester.equals("S2")){
                throw new IllegalArgumentException("Invalid Semester");
            }

            //validate year
            if (!(year.equals("Y1") || year.equals("Y2") || year.equals("Y3") || year.equals("Y4"))) {
                // The input is not "Y1," "Y2," "Y3," or "Y4"
                throw new IllegalArgumentException("Invalid Year");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean checkNameInput(String userInput, ArrayList<String> forbiddenCommands) {
        // Check for non-empty string
        if (userInput.trim().isEmpty()) {
            System.out.println("Name cannot be empty. Please enter a valid name.");
            return false;
        }

        // Check for length constraints
        int minLength = 2;  // Minimum length for a valid name
        int maxLength = 50; // Maximum length for a valid name
        if (userInput.length() < minLength || userInput.length() > maxLength) {
            System.out.println("Name must be between " + minLength + " and " + maxLength + " characters.");
            return false;
        }

        // Check for valid characters
        if (!userInput.matches("[a-zA-Z- ']+")) {
            System.out.println("Name can only contain letters, spaces, hyphens, and apostrophes.");
            return false;
        }

        // Check for no leading or trailing spaces
        if (!userInput.equals(userInput.trim())) {
            System.out.println("Name cannot start or end with a space.");
            return false;
        }


        if (forbiddenCommands.contains(userInput.trim().toLowerCase())) {
            System.out.println("Invalid name. This name is reserved for commands. Please enter a different name.");
            return false;
        }

        return true;
    }

    /**
     * Checks the validity of user input based on the provided command and words array.
     *
     * @param commandWord The command provided by the user.
     * @param arguments   An array of words parsed from the user input.
     * @return        True if the input is valid, false otherwise.
     */
    public static boolean isValidInputForCommand(String commandWord, String[] arguments) {
        int argumentsCounter = 0;

        //shift forward available arguments
        if (arguments != null) {
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = arguments[i].trim();
                if (arguments[i].isEmpty()) {
                    continue;
                }
                arguments[argumentsCounter] = arguments[i];
                argumentsCounter++;
            }
        }

        switch (commandWord) {
        case UserCommandWord.COMPLETE_MODULE_COMMAND:
        case UserCommandWord.PREREQUISITE_COMMAND: {
            if (argumentsCounter < 1) {
                return false;
            }
            break;
        }
        case UserCommandWord.VIEW_SCHEDULE_COMMAND:
        case UserCommandWord.CLEAR_SCHEDULE_COMMAND:
        case UserCommandWord.RECOMMEND_COMMAND: {
            if (argumentsCounter > 0) {
                return false;
            }
            break;
        }
        case UserCommandWord.SET_MAJOR_COMMAND: {
            if (argumentsCounter == 0) {
                return true;
            }
            if (argumentsCounter > 1) {
                UserError.invalidMajorFormat();
                return false;
            }
            try {
                Major.valueOf(arguments[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                String availableMajors = Arrays.toString(Major.values());
                UserError.invalidMajor(availableMajors);
                return false;
            }
            break;
        }
        case UserCommandWord.LEFT_COMMAND:
        case UserCommandWord.REQUIRED_MODULES_COMMAND:
        case UserCommandWord.HELP_COMMAND:
        case UserCommandWord.BYE_COMMAND:{
            if (argumentsCounter == 0) {
                return true;
            }
            return false;
        }
        case UserCommandWord.ADD_MODULE_COMMAND: {
            if (argumentsCounter != 2) {
                UserError.invalidAddFormat();
                return false;
            }
            try {
                Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                UserError.invalidSemester();
                return false;
            } catch (IndexOutOfBoundsException e) {
                UserError.invalidAddFormat();
                return false;
            }
            break;
        }
        case UserCommandWord.DELETE_MODULE_COMMAND: {
            if (argumentsCounter != 1) {
                UserError.invalidDeleteFormat();
                return false;
            }
            break;
        }
        case UserCommandWord.SHIFT_MODULE_COMMAND: {
            if (argumentsCounter != 2) {
                UserError.invalidShiftFormat();
                return false;
            }
            try {
                Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                UserError.invalidSemester();
                return false;
            } catch (IndexOutOfBoundsException e) {
                UserError.invalidShiftFormat();
                return false;
            }
            break;
        }
        case UserCommandWord.INFO_COMMAND: {
            if (argumentsCounter < 1) {
                UserError.emptyInputforInfoCommand();
                return false;
            }
            if (!arguments[0].equals("description")) {
                UserError.invalidCommandforInfoCommand();
                return false;
            }

            break;
        }
        case UserCommandWord.TIMETABLE_COMMAND: {
            if (argumentsCounter < 1) {
                UserError.emptyInputforTimetableCommand();
                return false;
            }
            break;
            // add check for modules that are in the current sem
            // if argument[1] is !show or in currSemModules, return false
        }
        default: {
            return true;
        }

        }
        return true;
    }

    /**
     * Checks the validity of keyword input for a search command.
     *
     * @author rohitcube
     * @param userInput The user input string containing the search command and keywords.
     * @return          True if the keyword input is valid, false otherwise.
     */
    public static boolean isValidKeywordInput(String userInput) {
        String keywords = userInput.substring(userInput.indexOf("search") + 6);
        // need to add a function to make search case-insensitive
        return !keywords.trim().isEmpty();
    }


    /**
     * Parses the year and semester from the given input string.
     *
     * @author rohitcube
     * @param yearAndSem The input string containing year and semester information.
     * @return An array containing the parsed year and semester as integers.
     */
    public static int[] parseStudentYear(String yearAndSem) {
        char yearValue = yearAndSem.charAt(1);
        int year = Character.getNumericValue(yearValue);
        char semValue = yearAndSem.charAt(4);
        int sem = Character.getNumericValue(semValue);
        return new int[]{year, sem};
    }

    /**
     * Checks if the given user input is a valid lesson type (lecture, tutorial, or lab).
     *
     * @author rohitcube
     * @param userInput The user input to be validated.
     * @return true if the input is a valid lesson type, false otherwise.
     */
    public static boolean isValidLessonType(String userInput) {
        String lowerCaseInput = userInput.toLowerCase();
        return lowerCaseInput.equals("lecture") ||
                lowerCaseInput.equals("tutorial") ||
                lowerCaseInput.equals("lab");
    }

    /**
     * Checks if the given string can be converted to an integer.
     *
     * @author rohitcube
     * @param input The string to be checked for integer conversion.
     * @return true if the string can be converted to an integer, false otherwise.
     */
    public static boolean isStringInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given day is a valid day of the week (case-insensitive).
     *
     * @author rohitcube
     * @param day The day to be validated.
     * @return true if the day is a valid day of the week, false otherwise.
     */
    public static boolean isDayValid(String day) {
        String uppercaseDay = day.toUpperCase();
        return uppercaseDay.equals("MONDAY") ||
                uppercaseDay.equals("TUESDAY") ||
                uppercaseDay.equals("WEDNESDAY") ||
                uppercaseDay.equals("THURSDAY") ||
                uppercaseDay.equals("FRIDAY") ||
                uppercaseDay.equals("SATURDAY") ||
                uppercaseDay.equals("SUNDAY");
    }

    /**
     * Checks if there are no null elements in the given array.
     *
     * @author rohitcube
     * @param array The array to be checked for null elements.
     * @return true if there are no null elements, false otherwise.
     */
    public static boolean hasNoNulls(Object[] array) {
        for (Object element : array) {
            if (element == null) {
                return false;
            }
        }
        return true;
    }


    /**
     * Removes null elements from the given string array and returns a new array.
     *
     * @author rohitcube
     * @param inputArray The array from which null elements need to be removed.
     * @return A new array with null elements removed.
     */
    public static String[] removeNulls(String[] inputArray) {
        int newSize = 0;
        for (String element : inputArray) {
            if (element != null) {
                newSize++;
            }
        }

        String[] resultArray = new String[newSize];
        int index = 0;

        for (String element : inputArray) {
            if (element != null) {
                resultArray[index++] = element;
            }
        }

        return resultArray;
    }


}
