package seedu.duke.models.logic;


import seedu.duke.models.schema.Major;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataRepository {

    /**
     * Retrieves a list of modules requirements for a specified major.
     *
     * @author ryanlohyr
     * @param major The major for which to retrieve requirements.
     * @return An ArrayList of module codes.
     * @throws RuntimeException If the specified major requirements file is not found.
     */
    public static ArrayList<String> getRequirements(String major) {
        String[] courseArray = determineRequirements(major);
        return new ArrayList<>(Arrays.asList(courseArray));
    }

    /**
     * Processes a course file, extracts relevant information, and returns a list of course codes.
     *
     * @param f The file to be processed.
     * @return An ArrayList of course codes extracted from the file.
     * @throws FileNotFoundException If the specified file is not found.
     */
    private static ArrayList<String> processCourseFile(File f) throws FileNotFoundException {
        ArrayList<String> currentArray = new ArrayList<>();
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String currentLine = s.nextLine();

            String[] words = currentLine.split(" ");

            if (!currentLine.isEmpty() && !currentLine.startsWith("*")) { // not empty line, not title
                currentArray.add(words[0]);
            }
        }
        return currentArray;
    }

    /**
     * Determines the course requirements based on the specified major. Function is used if file is not found
     *
     * @param major A string representing the major (e.g., "CEG" for Computer Engineering, "CS" for Computer Science).
     * @return An array of strings containing the course requirements for the specified major.
     */
    private static String[] determineRequirements(String major) {
        String[] courseArray;

        String[] csCourseArray = {
            "CS1101S", "ES2660", "GEC1000", "GEA1000", "GESS1000",
            "GEN2000", "IS1108", "CS1231S", "CS2030", "CS2040S",
            "CS2100", "CS2101", "CS2103T", "CS2106", "CS2109S",
            "CS3230", "MA1521", "MA1522", "ST2334", "CP3880"
        };
        String[] cegCourseArray = {
            "CG1111A", "MA1511", "MA1512", "CS1010", "GESS1000",
            "GEC1000", "GEN2000", "ES2631", "GEA1000", "DTK1234",
            "EG1311", "IE2141", "EE2211", "EG2501", "CDE2000",
            "PF1101", "CG4002", "MA1508E", "EG2401A", "CP3880",
            "CG2111A", "CS1231", "CG2023", "CG2027", "CG2028",
            "CG2271", "ST2334", "CS2040C", "CS2113", "EE2026", "EE4204"
        };

        if(major.equals("CEG")){
            courseArray = cegCourseArray;
        }else{
            courseArray = csCourseArray;
        }
        return courseArray;
    }

    /**
     * Returns the file path for the requirements of a specified major.
     *
     * @param major The major for which to retrieve the requirements file path.
     * @return The file path to the major's requirements file.
     */
    public static String getFullRequirements(Major major) {
        return String.format("src/main/java/seedu/duke/models/data/%sRequirements", major.toString());
    }

}
