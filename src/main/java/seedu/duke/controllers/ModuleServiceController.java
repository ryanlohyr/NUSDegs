package seedu.duke.controllers;

import seedu.duke.models.schema.Major;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.Timetable;
import seedu.duke.models.schema.ModuleWeekly;
import seedu.duke.models.schema.TimetableUserCommand;
import seedu.duke.utils.exceptions.InvalidModifyArgumentException;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.utils.exceptions.TimetableUnavailableException;
import seedu.duke.views.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.models.schema.Storage.saveSchedule;
import static seedu.duke.models.schema.Storage.saveTimetable;
import static seedu.duke.utils.TimetableParser.isExitModify;
import static seedu.duke.views.MajorRequirementsView.printRequiredModules;
import static seedu.duke.views.SemesterPlannerView.displaySchedule;
import static seedu.duke.views.TimetableUserGuideView.printCurrentSemModules;
import static seedu.duke.views.TimetableUserGuideView.println;
import static seedu.duke.views.TimetableUserGuideView.printTTModifyDetailedLessonGuide;
import static seedu.duke.views.TimetableUserGuideView.printTTModifySimpleLessonGuide;
import static seedu.duke.views.TimetableView.printTimetable;
import static seedu.duke.views.Ui.displayMessage;

public class ModuleServiceController {

    /**
     * Checks if the user's major input is valid. A major input is valid if it exists in the enumeration
     * of valid majors.
     *
     * @param userInput The user's major input.
     * @return True if the input is a valid major, false otherwise.
     */
    public static boolean validateMajorInput(String userInput) {
        try {
            Major.valueOf(userInput.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Please choose from the list: CS, or CEG");
            return false;
        }
    }


    //@@author ryanlohyr
    /**
     * Prompts the user to choose whether to add a list of modules to their draft schedule.
     * Displays the list of modules and asks for user input. Handles user input validation.
     *
     * @param scheduleToAdd A list of modules to be added to the schedule.
     */
    public static void chooseToAddToSchedule(Student student, ArrayList<String> scheduleToAdd) throws IOException {

        Scanner in = new Scanner(System.in);

        displayMessage("Here you go!");
        displayMessage("Taking the modules in this order will ensure a prerequisite worry free uni life!");
        displayMessage("Do you want to add this to your schedule planner? " +
                "(This will overwrite your current schedule!)");
        displayMessage("Please input 'Y' or 'N'");



        String userInput = in.nextLine().replace("\r", "").toUpperCase();

        while (!userInput.equals("N") && !userInput.equals(("Y"))) {
            displayMessage("Invalid input, please choose Y/N");
            userInput = in.nextLine().replace("\r", "");
        }

        if(userInput.equals("N")){
            displayMessage("Okay, we will not put it in your schedule");
            return;
        }

        student.addRecommendedSchedule(scheduleToAdd);

        displayMessage("Here is your schedule planner!");

        Schedule currentSchedule = student.getSchedule();

        displaySchedule(currentSchedule);

        displayMessage("Happy degree planning!");

        saveSchedule(student);

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
        printRequiredModules(major);
    }

    //@@author SebasFok
    /**
     * Asks the user for confirmation to clear their schedule and returns the user's choice.
     * Displays a message warning that clearing your schedule cannot be undone.
     *
     * @return true if the user confirms by entering 'Y', false if 'N'.
     */
    public static boolean isConfirmedToClearSchedule() {

        Scanner in = new Scanner(System.in);
        displayMessage("Are you sure you want to clear your schedule? " +
                "This action cannot be undone!");
        displayMessage("Please input 'Y' or 'N'");

        String userInput = in.nextLine().toUpperCase();

        while (!userInput.equals("N") && !userInput.equals(("Y"))) {
            displayMessage("Invalid input, please choose Y/N");
            userInput = in.nextLine();
        }

        return userInput.equals("Y");
    }

    //@@author janelleenqi
    /**
     * Modifies the timetable for the given student based on user commands.
     *
     * This method allows the user to modify the timetable for the current semester
     * by processing user commands. It enters a loop to continuously accept commands
     * until the user decides to exit. The modified timetable is saved after each
     * successful modification. If the timetable view is available, it is printed.
     * If the timetable view is unavailable, a simple guide is printed to inform the user.
     *
     * @param student The student for whom the timetable is to be modified.
     *                Must not be null.
     * @throws InvalidModifyArgumentException If an invalid argument is provided during modification.
     */
    public void modifyTimetable(Student student) throws InvalidModifyArgumentException {
        Timetable timetable = student.getTimetable();
        ArrayList <ModuleWeekly> currentSemModulesWeekly = timetable.getCurrentSemesterModulesWeekly();
        //verify accepted timetableuser command

        try {
            printCurrentSemModules(currentSemModulesWeekly);
        } catch (TimetableUnavailableException e) {
            println(e.getMessage());
        }

        printTTModifyDetailedLessonGuide("Entered Timetable Modify Mode");

        Ui ui = new Ui();

        boolean inTimetableModifyMode = true;
        while (inTimetableModifyMode) {
            try {
                String userInput = ui.getUserCommand("Input timetable modify command here: ");

                TimetableUserCommand currentTimetableCommand = new TimetableUserCommand(student,
                        currentSemModulesWeekly, userInput);


                String[] arguments = currentTimetableCommand.getArguments();

                //if exit
                if (isExitModify(arguments)) {
                    inTimetableModifyMode = false;
                    println("Exited Timetable Modify Mode");
                    continue;
                }

                currentTimetableCommand.processTimetableCommand(currentSemModulesWeekly);
                try {
                    saveTimetable(student);
                } catch (IOException ignored){
                    // GitHub actions cannott save timetable on the directory
                }
                if (timetable.timetableViewIsAvailable()) {
                    printTimetable(currentSemModulesWeekly);
                } else {
                    printTTModifySimpleLessonGuide("Timetable view is unavailable as modules in your " +
                            "current semester have no lessons yet.");
                }

            } catch (InvalidTimetableUserCommandException e) {
                println(e.getMessage());
            }
        }
    }

    //@@author janelleenqi
    /**
     * Displays the timetable for the current semester based on the provided module weekly data.
     *
     * This method displays the timetable for the current semester using the ArrayList of ModuleWeekly objects to the
     * user.
     *
     * @param currentSemesterModuleWeekly The list of ModuleWeekly objects with information about
     *                                     the timetable for the current semester
     */
    public void showTimetable(ArrayList<ModuleWeekly> currentSemesterModuleWeekly) {
        printTimetable(currentSemesterModuleWeekly);
    }
}
