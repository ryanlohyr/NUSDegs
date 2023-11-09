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
        if (userInput == null || userInput.equals(" ")) {
            return null;
        }
        String[] keywords = userInput.split(" ");
        return keywords[0];
    }

    //public static String parseTimetable

    /**
     * Excludes the command and extracts and returns an array of arguments from a user input string.
     * @author ryanlohyr
     * @param userInput The user input string.
     * @return An array of arguments from the input string.
     */
    public static String[] parseArguments(String userInput){
        if(userInput.trim().isEmpty()){
            return null;
        }
        String[] keywords = userInput.split(" ");
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
     * @param command The command provided by the user.
     * @param arguments   An array of words parsed from the user input.
     * @return        True if the input is valid, false otherwise.
     */
    public static boolean isValidInputForCommand(String command, String[] arguments) {
        switch (command) {
        case UserCommandWord.COMPLETE_MODULE_COMMAND:
        case UserCommandWord.PREREQUISITE_COMMAND: {
            if (arguments.length < 1) {
                return false;
            }
            break;
        }
        case UserCommandWord.VIEW_SCHEDULE_COMMAND:
        case UserCommandWord.CLEAR_SCHEDULE_COMMAND:
        case UserCommandWord.RECOMMEND_COMMAND: {
            if (arguments.length > 0) {
                return false;
            }
            break;
        }
        case UserCommandWord.SET_MAJOR_COMMAND: {
            if (arguments.length == 0) {
                return true;
            }
            if (arguments.length > 1) {
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
        case UserCommandWord.ADD_MODULE_COMMAND: {
            if (arguments.length != 2) {
                UserError.invalidAddFormat();
                return false;
            }
            try {
                Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                UserError.invalidSemester();
                return false;
            }
            break;
        }
        case UserCommandWord.DELETE_MODULE_COMMAND: {
            if (arguments.length != 1) {
                UserError.invalidDeleteFormat();
                return false;
            }
            break;
        }
        case UserCommandWord.SHIFT_MODULE_COMMAND: {
            if (arguments.length != 2) {
                UserError.invalidShiftFormat();
                return false;
            }
            try {
                Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                UserError.invalidSemester();
                return false;
            }
            break;
        }
        case UserCommandWord.INFO_COMMAND: {
            if (arguments.length < 1) {
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
            if (arguments.length < 1) {
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

    public static int[] parseStudentYear(String yearAndSem) {
        char yearValue = yearAndSem.charAt(1);
        int year = Character.getNumericValue(yearValue);
        char semValue = yearAndSem.charAt(4);
        int sem = Character.getNumericValue(semValue);
        return new int[]{year, sem};
    }

    public static String parserDayForModify(String userInput) {
        int startIndexOfDay = userInput.indexOf("/day");
        String day = userInput.substring(startIndexOfDay + 4).trim();
        return day;
    }

    public static int parserTimeForModify(String userInput) {
        int startIndexOfTime = userInput.indexOf("/time");
        int startIndexOfDuration = userInput.indexOf("/duration");
        String time = userInput.substring(startIndexOfTime + 5, startIndexOfDuration).trim();
        return Integer.parseInt(time);
    }

    public static int parserDurationForModify(String userInput) {
        int startIndexOfDay = userInput.indexOf("/day");
        int startIndexOfDuration = userInput.indexOf("/duration");
        String time = userInput.substring(startIndexOfDuration + 9, startIndexOfDay).trim();
        return Integer.parseInt(time);
    }

}
