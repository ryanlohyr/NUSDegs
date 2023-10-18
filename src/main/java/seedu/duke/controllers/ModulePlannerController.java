package seedu.duke.controllers;

import seedu.duke.views.CommandLineView;
import seedu.duke.utils.Parser;
import seedu.duke.models.*;

import java.net.URISyntaxException;
import java.util.Scanner;

public class ModulePlannerController {
    private CommandLineView view;
    private Parser parser;

    public ModulePlannerController() {
        this.view = new CommandLineView();
        this.parser = new Parser();
    }

    public void start() {
        view.displayWelcome();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while (!userInput.equals("Bye")) {

            String[] words = userInput.split(" ");

            String initialWord = words[0];

            switch (initialWord) {
            case "hi": {
                view.displayMessage("can put the commands here");
                break;
            }
            case "hello": {
                view.displayMessage("yup");
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
            case "info": {
                String moduleCode = words[1];
                try {
                    Api.getModuleInfo(moduleCode);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
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
}
