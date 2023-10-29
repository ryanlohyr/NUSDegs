package seedu.duke.models.logic;


import seedu.duke.models.schema.Major;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataRepository {

    /**
     * Retrieves a list of modules requirements for a specified major.
     *
     * @param major The major for which to retrieve requirements.
     * @return An ArrayList of module codes.
     * @throws RuntimeException If the specified major requirements file is not found.
     */
    public static ArrayList<String> getRequirements(String major){
        try {
            //add validation if wrong major?
            String fileName = String.format("src/main/java/seedu/duke/models/data/%sRequirementsModuleCodes",major);
            File f = new File(fileName);
            if (!f.exists()) {
                System.out.println("File does not exist");
                return new ArrayList<>();
            }
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the file path for the requirements of a specified major.
     *
     * @param major The major for which to retrieve the requirements file path.
     * @return The file path to the major's requirements file.
     */
    public static String getFullRequirements(Major major){
        return String.format("src/main/java/seedu/duke/models/data/%sRequirements", major.toString());
    }

}
