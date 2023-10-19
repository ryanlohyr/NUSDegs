package seedu.duke.controllers;

import seedu.duke.CompletePreqs;
import seedu.duke.ModuleList;
import seedu.duke.models.Major;
import seedu.duke.models.Student;
import seedu.duke.views.CommandLineView;
import seedu.duke.utils.Parser;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;


import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
public class ModulePlannerController {
    private CommandLineView view;
    private Parser parser;
    private Student student;
    private ModuleList modulesMajor;
    private ModuleList modulesTaken;
    private ModuleList modulesLeft;
    private HashMap<String, List<String>> modsWithPreqs;
    private CompletePreqs addModulePreqs;

    public ModulePlannerController() {
        this.view = new CommandLineView();
        this.parser = new Parser();
        this.student = new Student();

        //This modules list of taken and classes left can be in a storage class later on.
        this.modulesMajor = new ModuleList("CS1231S CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");
        this.modulesTaken = new ModuleList("CS1231S MA1511");
        this.modulesLeft = new ModuleList();

        modsWithPreqs = new HashMap<>();

        //Pass in Hashmap of mods with Preqs
        this.addModulePreqs = new CompletePreqs(addModsWithPreqs(modsWithPreqs));
        //Pass in the list of mods completed.
        addModulePreqs.initializeCompletedMods(modulesTaken);


    }



    public void start() {
        view.displayWelcome();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while (!userInput.equals("bye")) {

            String[] words = userInput.split(" ");

            String initialWord = words[0].toLowerCase();

            switch (initialWord) {
            case "hi": {
                view.displayMessage("can put the commands here");
                break;
            }
            case "hello": {
                view.displayMessage("yup");
                break;
            }
            case "left": {
                ArrayList<String> modules = listModulesLeft();

                view.displayMessage("Modules left:");
                for (String module : modules) {
                    view.displayMessage(module);
                }

                break;
            }
            case "pace": {
                //assumed that everyone graduates at y4s2
                //waiting for retrieving logic
                int modulesCreditsCompleted = 100;
                int totalCreditsToGraduate = 160;
                int creditsLeft = totalCreditsToGraduate - modulesCreditsCompleted;
                computePace(words, creditsLeft);
                break;
            }
            case "major": {
                updateMajor(words[1]);
                break;
            }
            case "complete": {
                String moduleCompleted = words[1];
                //Get mods that are unlocked after a mod is marked complete
                addModulePreqs.getUnlockedMods(moduleCompleted);
                break;
            }
            default: {
                view.displayMessage("Hello " + userInput);
                break;
            }

            }
            userInput = in.nextLine();
        }
    }



    /**
     * Computes and returns the list of modules that are left in the ModuleList modulesMajor
     * after subtracting the modules in the ModuleList modulesTaken.
     *
     * @return An ArrayList of module codes representing the modules left after the subtraction.
     * @throws InvalidObjectException If either modulesMajor or modulesTaken is null.
     */
    public ArrayList<String> listModulesLeft() {
        //modulesMajor.txt - modulesTaken.txt
        try {
            modulesLeft.getDifference(modulesMajor, modulesTaken);
            return modulesLeft.getMainModuleList();
        } catch (InvalidObjectException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Computes the recommended pace for completing a degree based on the provided academic year
     * and credits left until graduation.
     *
     * @param userInput  An array of user input where userInput[0] is the command and userInput[1] is the academic year.
     * @param creditsLeft The number of credits left until graduation.
     * @throws IllegalArgumentException if the provided academic year is invalid.
     */
    public void computePace(String[] userInput, int creditsLeft) {
        boolean argumentProvided = userInput.length != 1;
        //wait for text file logic
        if (!argumentProvided) {
            view.displayMessage("You currently have " + creditsLeft + " MCs till graduation");
            return;
        }
        if (!parser.isValidAcademicYear(userInput[1])) {
            return;
        }

        String[] parts = userInput[1].split("/");
        String year = parts[0].toUpperCase();
        String semester = parts[1].toUpperCase();

        int lastSemesterOfYear = 2;
        int lastYearOfDegree = 4;


        int yearIntValue = Character.getNumericValue(year.charAt(1));
        int semesterIntValue = Character.getNumericValue(semester.charAt(1));
        //if we are at y2s1, we have 5 semesters left
        int semestersLeft = (lastYearOfDegree - yearIntValue) * 2 + (lastSemesterOfYear - semesterIntValue);
        int creditsPerSem = Math.round((float) creditsLeft / semestersLeft);
        view.displayMessage("You have " + creditsLeft + "MCs for " + semestersLeft + " semesters. "
                + "Recommended Pace: "+ creditsPerSem + "MCs per sem until graduation");
    }


    /**
     * Add all mods that require prerequisites to a map storing the mod and a set of preqs
     * @param list
     * @return HashMap of Mods with their corresponding preqs
     */

    private HashMap<String, List<String>> addModsWithPreqs(HashMap<String, List<String>> list) {
        //Only two mods don't have preqs MA1511 and CS1231S.
        // In the future this will be dealt
        addValue(list, "CS3230", "CS2030S");
        addValue(list, "CS3230", "CS1231S");

        addValue(list, "CS2030S", "CS1231S");

        addValue(list, "CS2040S", "CS1231S");

        addValue(list, "CS2106", "CS1231S");

        addValue(list, "CS2109S", "CS1231S");

        return list;
    }


    /**
     * Helper function to addModsWithPreqs to add Strings and sets together
     * @param map
     * @param key
     * @param value
     */
    public static void addValue(HashMap<String, List<String>> map, String key, String value) {
        // If the map does not contain the key, put an empty list for that key
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        // Add the value to the list associated with the key
        map.get(key).add(value);
    }



    public void updateMajor(String major) {
        try {
            student.setMajor(Major.valueOf(major.toUpperCase()));
            view.displayMessage("Major " + student.getMajor() + " selected!");
        } catch (IllegalArgumentException e) {
            view.displayMessage("Please select a major from this list: " + Arrays.toString(Major.values()));
        }
    }
}
