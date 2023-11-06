package seedu.duke.controllers;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.exceptions.MissingModuleException;
import seedu.duke.models.logic.CompletePreqs;
import seedu.duke.models.schema.Module;
import seedu.duke.models.schema.Student;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;
import seedu.duke.views.CommandLineView;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.controllers.ModuleServiceController.chooseToAddToSchedule;
import static seedu.duke.models.logic.Api.doesModuleExist;
import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.MajorRequirements.printRequiredModules;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;
import static seedu.duke.views.CommandLineView.*;
import static seedu.duke.views.ModuleInfoView.printModuleStringArray;

/**
 * This class houses all the methods for the Module Planner controller.
 * It provides functionality for computing the recommended pace, showing modules left,
 * adding, deleting, completing modules,
 * It provides functionality for computing the recommended pace,
 * showing modules left, adding, deleting, completing modules,
 * and getting required modules for a student.
 *
 * @author ryanlohyr
 */
public class ModuleMethodsController {
    /**
     * Computes and displays the recommended pace for completing remaining module credits until graduation.
     *
     * @param arguments              An array of strings containing academic year and semester information.
     * @param completedModuleCredits The number of module credits already completed by the user.
     * @author ryanlohyr
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

    public static void showModulesLeft(ArrayList<String> moduleCodes) {
        printModuleStringArray(moduleCodes);
    }


    public static void addModule(String module, int targetSem, Student student) {
        try {
            student.addModuleSchedule(module, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(module);
            displayMessage(f.getMessage());
        }
    }

    public static void recommendScheduleToStudent(Student student) {
        CommandLineView.displayMessage("Hold on a sec! Generating your recommended schedule <3....");
        ArrayList<String> recommendedSchedule = generateRecommendedSchedule(student.getMajor());
        chooseToAddToSchedule(student, recommendedSchedule);
    }

    public static void deleteModule(String module, Student student) {
        try {
            student.deleteModuleSchedule(module);
            displaySuccessfulDeleteMessage();
            student.printSchedule();
        } catch (MissingModuleException | FailPrereqException e) {
            displayMessage(e.getMessage());
        }
    }

    //public static boolean canCompleteModule(String[] arguments, ArrayList<String> majorModuleCodes,
    //ModuleList modulesPlanned, CompletePreqs addModulePreqs) {
    public static void completeModule(Student student, String moduleCode) {
        try {
            Module module = student.existModuleSchedule(moduleCode);
            if (module.getCompletionStatus()) {
                UserError.displayModuleAlreadyCompleted(module.getModuleCode());
            } else {
                student.completeModuleSchedule(moduleCode);
                displaySuccessfulCompleteMessage();
            }

        } catch (MissingModuleException e) {
            displayMessage(e.getMessage());
            UserError.invalidAddFormat();

        } catch (InvalidObjectException e) {
            assert false;
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
        if (prereq == null || prereq.isEmpty()) {
            displayMessage("Module " + moduleCode + " has no prerequisites.");
        } else {
            displayMessage(prereq);
        }
    }
}
