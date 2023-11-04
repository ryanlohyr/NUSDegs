package seedu.duke.controllers;

import seedu.duke.models.logic.MajorRequirements;
import seedu.duke.models.schema.Major;
import seedu.duke.models.schema.Student;

import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.views.CommandLineView.displayMessage;

public class ModuleServiceController {

    /**
     * Checks if the user's major input is valid. A major input is valid if it exists in the enumeration
     * of valid majors.
     *
     * @param userInput The user's major input.
     * @return True if the input is a valid major, false otherwise.
     */
    public static boolean checkMajorInput(String userInput) {
        try {
            Major.valueOf(userInput.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Please choose from the list: CS, or CEG");
            return false;
        }
    }


    /**
     * Prompts the user to choose whether to add a list of modules to their draft schedule.
     * Displays the list of modules and asks for user input. Handles user input validation.
     *
     * @param scheduleToAdd A list of modules to be added to the schedule.
     */
    public static void chooseToAddToSchedule(Student student, ArrayList<String> scheduleToAdd) {

        Scanner in = new Scanner(System.in);
        displayMessage(scheduleToAdd);
        displayMessage("Do you want to add this to your draft schedule?, please input 'Y' or 'N'");

        String userInput = in.nextLine();

        while (!userInput.equals("N") && !userInput.equals(("Y"))) {
            displayMessage("Invalid input, please choose Y/N");
            userInput = in.nextLine();
        }

        if (userInput.equals("Y")) {
            displayMessage("Hold on, this may take a while......");
            student.getSchedule().addRecommendedScheduleListToSchedule(scheduleToAdd);
            student.getSchedule().printMainModuleList();

        } else {
            displayMessage("No was chosen");
        }
    }
    /**
     * Retrieves and prints the required modules for a specified major.
     * <p>
     * This method initializes a `MajorRequirements` object based on the provided `major`.
     * It then attempts to print the required modules from a corresponding TXT file.
     * If the TXT file is not found, an error message is displayed.
     *
     * @param major The major for which to retrieve required modules.
     * @throws NullPointerException If `major` is null.
     */
    public static void getRequiredModules(String major) throws NullPointerException {
        MajorRequirements.printRequiredModules(major);
    }

}
