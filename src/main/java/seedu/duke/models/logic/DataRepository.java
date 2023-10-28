package seedu.duke.models.logic;


import seedu.duke.models.schema.Major;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class DataRepository {
    public static ArrayList<String> getRequirements(String major){
        try{
            //add validation if wrong major?
            String fileName = String.format("src/main/java/seedu/duke/models/data/%sRequirements",major);
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

                if (!words[0].isEmpty() && words[0].charAt(0) != '*') { // not empty line, not title
                    currentArray.add(words[0]);
                }
            }
            return currentArray;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFullRequirements(Major major){
        //try{
            //add validation if wrong major?
        return String.format("src/main/java/seedu/duke/models/data/%sRequirements", major.toString());
/*
            try {
                printTXTFile(fileName);
            } catch (FileNotFoundException e) {
                System.out.println("\t☹ An error occurred." + e.getMessage());
            }

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

                if (!words[0].isEmpty() && words[0].charAt(0) != '*') { // not empty line, not title
                    currentArray.add(words[0]);
                }
            }
            return currentArray;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        */
    }

}
