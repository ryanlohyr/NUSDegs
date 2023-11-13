package seedu.duke.controllers;

import seedu.duke.utils.exceptions.MandatoryPrereqException;
import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.utils.exceptions.MissingModuleException;
import seedu.duke.utils.exceptions.InvalidPrereqException;

import seedu.duke.models.schema.Module;
import seedu.duke.models.schema.Student;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.controllers.ModuleServiceController.chooseToAddToSchedule;
import static seedu.duke.controllers.ModuleServiceController.isConfirmedToClearSchedule;
import static seedu.duke.models.logic.Api.isValidModule;
import static seedu.duke.models.logic.Prerequisite.getModulePrereqBasedOnCourse;
import static seedu.duke.models.schema.Storage.saveSchedule;
import static seedu.duke.models.schema.Storage.saveTimetable;
import static seedu.duke.utils.errors.HttpError.displaySocketError;
import static seedu.duke.views.Ui.displayMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulAddMessage;
import static seedu.duke.views.CommandLineView.showPrereq;
import static seedu.duke.views.CommandLineView.displaySuccessfulDeleteMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulShiftMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulClearMessage;
import static seedu.duke.views.CommandLineView.displayUnsuccessfulClearMessage;
import static seedu.duke.views.MajorRequirementsView.printRequiredModules;
import static seedu.duke.views.ModuleInfoView.printModuleStringArray;
import static seedu.duke.views.Ui.showLoadingAnimation;
import static seedu.duke.views.Ui.stopLoadingAnimation;

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
    private static final String LEFT_HEADER = "Modules Left: ";


    /**
     * Computes and displays the recommended pace for completing remaining module credits until graduation.
     *
     * @author ryanlohyr
     * @param arguments              An array of strings containing academic year and semester information.
     * @param completedModuleCredits The number of module credits already completed by the user.
     */
    public static void computePace(String[] arguments, int completedModuleCredits, String currentAcademicYear) {
        int totalCreditsToGraduate = 160;
        int creditsLeft = totalCreditsToGraduate - completedModuleCredits;
        boolean argumentProvided = arguments.length != 0;

        String[] parts = currentAcademicYear.split("/");;

        //if the user provided a argument and it was invalid
        if (argumentProvided && !Parser.isValidAcademicYear(arguments[0])) {
            return;
        }

        //if user provided argument, we will use this to calculate pace instead
        if(argumentProvided) {
            parts = arguments[0].split("/");
        }

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

    //@@author janelleenqi
    /**
     * Displays the list of remaining module codes.
     *
     * This method takes an ArrayList of module codes and prints them to the console
     * after displaying a header message.
     *
     * @param moduleCodesLeft An ArrayList of Strings representing the remaining module codes.
     *                        It should not be null.
     */
    public static void showModulesLeft(ArrayList<String> moduleCodesLeft) {
        displayMessage(LEFT_HEADER);
        printModuleStringArray(moduleCodesLeft);
    }


    /**
     * Executes the command to add a module to the target semester of the student's schedule and saves the updated
     * schedule. This method adds the specified module to the target semester of the student's schedule and prints
     * the updated schedule. Additionally, it attempts to save the updated schedule to storage.
     * Exceptions related to module deletion, missing modules, mandatory prerequisites, and
     * storage I/O errors are caught and appropriate error messages are displayed.
     *
     * @author SebasFok
     * @param module     The module code of the module to be added.
     * @param targetSem  The target semester for adding the module.
     * @param student    The student object to which the module will be added.
     */
    public static void executeAddModuleCommand(String module, int targetSem, Student student) {
        try {
            student.addModuleToSchedule(module, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            try{
                saveSchedule(student);
            }catch (IOException ignored){
                //we ignore first as GitHub actions cant save schedule on the direcotry
            }
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereq(module, student.getMajor());
            displayMessage(f.getMessage());
        }
    }

    /**
     * Recommends a schedule to the given student based on their major.
     * The method generates a recommended schedule, displays a loading animation,
     * and allows the student to choose whether to add the recommended courses to their existing schedule.
     *
     * @author ryanlohyr
     * @param student The student for whom the schedule recommendation is generated.
     */
    public static void recommendScheduleToStudent(Student student) {
        try{
            displayMessage("Hold on a sec! Generating your recommended schedule <3....");

            showLoadingAnimation();

            ArrayList<String> recommendedSchedule = student
                    .getSchedule()
                    .generateRecommendedSchedule(student.getMajor());

            stopLoadingAnimation();

            chooseToAddToSchedule(student, recommendedSchedule);

        }catch (IOException e) {
            displayMessage("Oh no, we could not generate your schedule");
            displaySocketError();
        }
    }

    /**
     * Deletes a module from the student's schedule and saves the updated schedule.
     * This method removes the specified module from the student's schedule and prints
     * the updated schedule. Additionally, it attempts to save the updated schedule to storage.
     * Exceptions related to module deletion, missing modules, mandatory prerequisites, and
     * storage I/O errors are caught and appropriate error messages are displayed.
     *
     * @author ryanlohyr
     * @param module  The code or identifier of the module to be deleted.
     * @param student The student object whose schedule is being updated.
     */
    public static void executeDeleteModuleCommand(String module, Student student) {
        try {
            student.deleteModuleFromSchedule(module);
            displaySuccessfulDeleteMessage();
            student.printSchedule();
            try{
                saveSchedule(student);
                saveTimetable(student);
            }catch (IOException ignored){
                //we ignore first as GitHub actions cant save schedule on the direcotry
            }

        } catch (MissingModuleException | MandatoryPrereqException e) {
            displayMessage(e.getMessage());
        } catch (IOException e) {
            displaySocketError();
        }
    }

    /**
     * Executes the command to shift a module within a student's schedule to a different semester.
     * This method shifts the specified module to the target semester of the student's schedule and prints
     * the updated schedule. Additionally, it attempts to save the updated schedule to storage.
     * Exceptions related to module deletion, missing modules, mandatory prerequisites, and
     * storage I/O errors are caught and appropriate error messages are displayed.
     *
     * @author SebasFok
     * @param module     The module code of the module to be shifted.
     * @param targetSem  The target semester for shifting the module.
     * @param student    The student object whose schedule will be updated.
     */
    public static void executeShiftModuleCommand(String module, int targetSem, Student student) {
        try {
            student.shiftModuleInSchedule(module, targetSem);
            displaySuccessfulShiftMessage();
            student.printSchedule();
            try{
                saveSchedule(student);
                saveTimetable(student);
            }catch (IOException ignored){
                //we ignore first as GitHub actions cant save schedule on the direcotry
            }

        } catch (InvalidObjectException | IllegalArgumentException | MissingModuleException |
                 MandatoryPrereqException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereq(module, student.getMajor());
            displayMessage(f.getMessage());
        } catch (IOException e) {
            displaySocketError();
        }
    }

    /**
     * Executes the command to clear the student's schedule. This method clears the entire schedule of the student as
     * well as the completion status of all modules. Additionally, it attempts to save the updated schedule to storage.
     * Before clearing, the user will be prompted again to check if they truly want to clear their schedule.
     * Exceptions related to module deletion, missing modules, mandatory prerequisites, and
     * storage I/O errors are caught and appropriate error messages are displayed.
     *
     * @author SebasFok
     * @param student    The student object whose schedule will be cleared.
     */
    public static void executeClearScheduleCommand(Student student){
        if(!isConfirmedToClearSchedule()){
            displayUnsuccessfulClearMessage();
            return;
        }

        student.clearAllModulesFromSchedule();
        displaySuccessfulClearMessage();
        try{
            saveSchedule(student);
            saveTimetable(student);
        }catch (IOException e){
            throw new RuntimeException();
        }

    }

    /**
     * Completes the specified module for the given student.
     *
     * This method attempts to mark the specified module as completed for the given student.
     * It checks if the module is already completed, and if so, displays an error message
     * and exits the method. If the module is not found in the student's schedule planner,
     * a message is displayed. If the prerequisites for the module are not met, an error
     * message is displayed, and the prerequisites are shown.
     *
     * @param student The student for whom the module is to be completed. Must not be null.
     * @param moduleCode The code of the module to be completed. Must not be null.
     * @throws MissingModuleException If the module does not exist in the student's schedule planner.
     * @throws FailPrereqException If the prerequisites for the module are not met.
     *                            Displays an error message and shows the prerequisites.
     * @throws RuntimeException If an unexpected InvalidPrereqException occurs (should not happen).
     */
    public static void completeModule(Student student, String moduleCode) {
        try {
            Module module = student.getModuleFromSchedule(moduleCode);

            // if module is already completed, exit
            if (module.getCompletionStatus()) {
                UserError.displayModuleAlreadyCompleted(module.getModuleCode());
                return;
            }

            student.completeModuleSchedule(moduleCode);
            // update save file
            try {
                saveSchedule(student);
            } catch (IOException ignored){
                // GitHub actions cannot save schedule on the directory
            }

        } catch (MissingModuleException e) {
            // module not does exist in schedule planner
            displayMessage(e.getMessage());

        } catch (InvalidObjectException e) {
            assert false;
        } catch (FailPrereqException e) {
            // prerequisites are not met
            displayMessage("Prerequisites not completed for " + moduleCode);
            showPrereq(moduleCode, student.getMajor());
        } catch (InvalidPrereqException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prints the required modules for a student based on their major.
     *
     * This method calls the printRequiredModules function in MajorRequirementsView to print required modules for
     * the specified major.
     *
     * @param major The major of the student that will be used to print the required modules.
     */
    public static void getRequiredModulesForStudent(String major) {
        printRequiredModules(major);
    }

    /**
     * Determines and displays the prerequisites of a module for a given major.
     * This method determines the prerequisites of a module based on the provided module code and major.
     * It checks if the module exists, retrieves its prerequisites, and displays them if they are available.
     * If the module does not exist, or if there are any issues with retrieving prerequisites, appropriate
     * messages are displayed.
     * @author ryanlohyr
     * @param moduleCode The module code for which prerequisites need to be determined.
     * @param major      The major for which the prerequisites are determined.
     */
    public static void determinePrereq(String moduleCode, String major) {
        boolean isValid = isValidModule(moduleCode);
        ArrayList<String> prereq;

        // Checks if the module is a valid module in NUS
        if (!isValid) {
            return;
        }

        try{
            prereq = getModulePrereqBasedOnCourse(moduleCode, major);

            if (prereq == null) {
                displayMessage("Module " + moduleCode + " has no prerequisites.");
                return;
            }

            printModuleStringArray(prereq);

        } catch (InvalidPrereqException e) {
            displayMessage(e.getMessage());
        }catch (IOException e){
            //if there is an issue connecting to NUSMods/connecting to the internet
            displaySocketError();
        }
    }
}
