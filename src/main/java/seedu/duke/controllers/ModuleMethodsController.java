package seedu.duke.controllers;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.models.logic.CompletePreqs;
import seedu.duke.models.logic.ModulesLeft;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Student;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Objects;

import static seedu.duke.models.logic.Api.doesModuleExist;
import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.MajorRequirements.printRequiredModules;
import static seedu.duke.views.CommandLineView.displayMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulAddMessage;
import static seedu.duke.views.CommandLineView.showPrereqCEG;
import static seedu.duke.views.CommandLineView.displaySuccessfulDeleteMessage;

/**
 * This class houses all the methods for the Module Planner controller.
 * It provides functionality for computing the recommended pace, showing modules left,
 * adding, deleting, completing modules,
 * and getting required modules for a student.
 *
 * @author ryanlohyr
 *
 */
public class ModuleMethodsController {
    /**
     * Computes and displays the recommended pace for completing remaining module credits until graduation.
     *
     * @author ryanlohyr
     * @param arguments              An array of strings containing academic year and semester information.
     * @param completedModuleCredits The number of module credits already completed by the user.
     *
     */
    static void computePace(String[] arguments, int completedModuleCredits) {
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
        if (modulesMajor == null || modulesTaken == null) {
            UserError.emptyMajor();
            return;
        }
        ModulesLeft modulesLeft = new ModulesLeft(modulesMajor, modulesTaken);
        ArrayList<String> modules = modulesLeft.listModulesLeft();
        displayMessage("Modules left:");
        for (String module : modules) {
            displayMessage(module);
        }
    }

    public static void addModule(String module, int targetSem, Student student) {
        try {
            student.addModule(module, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(module);
            displayMessage(f.getMessage());
        }
    }

    public static void deleteModule(String module, Student student) {
        try {
            student.deleteModule(module);
            displaySuccessfulDeleteMessage();
            student.printSchedule();
        } catch (IllegalArgumentException | FailPrereqException e) {
            displayMessage(e.getMessage());
        }
    }

    public static void completeModule(
            String[] arguments,
            ModuleList modulesMajor,
            ModuleList modulesTaken,
            CompletePreqs addModulePreqs) {
        if(modulesMajor == null){
            UserError.emptyMajor();
            return;
        }
        if (addModulePreqs.checkModInput(arguments, modulesMajor)) {
            String moduleCompleted = arguments[0].toUpperCase();
            addModulePreqs.getUnlockedMods(moduleCompleted);
            addModulePreqs.printUnlockedMods(moduleCompleted);
            modulesTaken.addModule(moduleCompleted);

        }
    }

    public static void getRequiredModulesForStudent(String major) {
        printRequiredModules(major);
    }

    public static void determinePrereq(String moduleCode, String major) {
        boolean exist = doesModuleExist(moduleCode);

        if (!exist) {
            return;
        }

        ArrayList<String> prereq = getModulePrereqBasedOnCourse(moduleCode, major);
        displayMessage(Objects.requireNonNullElseGet(prereq, () -> "Module " + moduleCode +
                " has no prerequisites."));
    }



}
