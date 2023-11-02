package seedu.duke.controllers;

import seedu.duke.models.logic.ModulesLeft;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.utils.Parser;
import seedu.duke.views.ErrorHandler;

import java.util.ArrayList;

import static seedu.duke.views.CommandLineView.displayMessage;

public class ModuleMethodsController {
    /**
     * Computes and displays the recommended pace for completing remaining module credits until graduation.
     * @author ryanlohyr
     * @param arguments              An array of strings containing academic year and semester information.
     * @param completedModuleCredits The number of module credits already completed by the user.
     *
     */
    public static void computePace(String[] arguments, int completedModuleCredits) {
        int totalCreditsToGraduate = 160;
        int creditsLeft = totalCreditsToGraduate - completedModuleCredits;
        boolean argumentProvided = arguments.length != 0;
        if (!argumentProvided) {
            displayMessage("You currently have " + creditsLeft + " MCs till graduation");
            return;
        }
        if (!Parser.isValidAcademicYear(arguments[0])) {
            return;
        }

        String[] parts = arguments[0].split("/");
        String year = parts[0].toUpperCase();
        String semester = parts[1].toUpperCase();

        int lastSemesterOfYear = 2;
        int lastYearOfDegree = 4;


        int yearIntValue = Character.getNumericValue(year.charAt(1));
        int semesterIntValue = Character.getNumericValue(semester.charAt(1));
        //if we are at y2s1, we have 5 semesters left
        int semestersLeft = (lastYearOfDegree - yearIntValue) * 2 + (lastSemesterOfYear - semesterIntValue);
        int creditsPerSem = Math.round((float) creditsLeft / semestersLeft);
        displayMessage("You have " + creditsLeft + "MCs for " + semestersLeft + " semesters. "
                + "Recommended Pace: " + creditsPerSem + "MCs per sem until graduation");
    }
    public static void showModulesLeft(ModuleList modulesMajor, ModuleList modulesTaken) {
        if (modulesMajor != null && modulesTaken != null) {
            ModulesLeft modulesLeft = new ModulesLeft(modulesMajor, modulesTaken);
            ArrayList<String> modules = modulesLeft.listModulesLeft();
            displayMessage("Modules left:");
            for (String module : modules) {
                displayMessage(module);
            }
        } else {
            ErrorHandler.emptyMajor();
        }
    }


}
