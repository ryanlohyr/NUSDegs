package seedu.duke.utils;

import seedu.duke.views.ErrorHandler;

import java.util.Objects;

public class Parser {

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
    public boolean isValidAcademicYear( String userInput ) {
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

    public static boolean isValidInput(String command, String[] words) {
        switch (command) {
        case "prereq": {
            if (words.length < 2) {
                ErrorHandler.invalidInput();
                return false;
            }
            break;
        }
        case "recommend": {
            if (words.length < 2) {
                ErrorHandler.invalidInput();
                return false;
            }
            if (!Objects.equals(words[1], "CEG")){
                ErrorHandler.invalidInput();
                return false;
            }
            break;
        }
        case "test2": {
            if (words.length < 21) {
                return false;
            }
            break;
        }
        default: {
            return true;
        }

        }
        return true;
    }



}
