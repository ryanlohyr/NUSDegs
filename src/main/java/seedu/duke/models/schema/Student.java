package seedu.duke.models.schema;

import seedu.duke.controllers.ModuleServiceController;
import seedu.duke.utils.exceptions.InvalidModifyArgumentException;
import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.utils.exceptions.InvalidPrereqException;
import seedu.duke.utils.exceptions.MandatoryPrereqException;
import seedu.duke.utils.exceptions.MissingModuleException;
import seedu.duke.utils.exceptions.TimetableUnavailableException;

import seedu.duke.utils.Parser;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.models.logic.Prerequisite.getModulePrereqBasedOnCourse;
import static seedu.duke.models.schema.Storage.getRequirements;
import static seedu.duke.utils.errors.HttpError.displaySocketError;
import static seedu.duke.views.CommandLineView.displaySuccessfulCompleteMessage;
import static seedu.duke.views.TimetableUserGuideView.addOrRecommendGuide;
//import static seedu.duke.views.TimetableUserGuideView.addOrRecommendGuide;
//import static seedu.duke.views.UserGuideView.timetableModifySuccessful;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private static boolean intitialise;
    private static int counter;
    private String name;
    private String major;
    private Schedule schedule;
    private String year;
    private int completedModuleCredits;
    private ArrayList<String> majorModuleCodes;
    private ModuleList currentSemesterModules;
    private Timetable timetable;
    //private ArrayList<ModuleWeekly> currentSemesterModulesWeekly;

    /**
     * Constructs a student with a name, major, and module schedule.
     *
     * @param name     The name of the student.
     * @param major    The major of the student.
     * @param schedule The module schedule of the student.
     */
    public Student(String name, String major, Schedule schedule) {
        this.name = name;
        this.major = major;
        this.schedule = schedule;
        this.year = null;
        this.timetable = Timetable.timetable;
        counter++;
    }

    /**
     * Constructs a student with a null name, null major, and an empty module schedule.
     */
    public Student() {
        this.name = null;
        this.major = null;
        this.schedule = new Schedule();
        this.timetable = Timetable.timetable;
        this.year = null;
    }

    public static int getNumOfInstances() {
        return counter;
    }



    /**
     * Retrieves the module schedule of the student.
     *
     * @return The module schedule of the student.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Sets the class schedule of the student.
     *
     * @param schedule The new module schedule.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getCurrentModuleCredits() {
        return completedModuleCredits;
    }

    /**
     * Retrieves the name of the student.
     *
     * @return The name of the student.
     */
    public String getName() {
        return name;
    }

    //@@author ryanlohyr

    /**
     * Sets the name of the student.
     *
     * @param name The new name of the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the major of the student.
     *
     * @return The major of the student.
     * @throws NullPointerException If the major has not been set (i.e., it is `null`).
     */

    public String getMajor() {
        return major;
    }

    /**
     * Sets the major of the student.
     *
     * @param major The new major to set.
     */
    public void setMajor(String major) {
        this.major = major;
        majorModuleCodes = getRequirements(major);
    }

    /**
     * Sets the first major without the major command
     * @author Isaiah Cerven
     * @param userInput must be validated in parser as CS or CEG
     */
    public void setFirstMajor(String userInput) {
        try {
            setMajor(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void addModuleToSchedule(String moduleCode, int targetSem) throws IllegalArgumentException,
            InvalidObjectException, FailPrereqException {
        this.schedule.addModule(moduleCode, targetSem);
    }

    /**
     * Completes a module with the specified module code.
     *
     * @param moduleCode The code of the module to be completed.
     */
    public void completeModuleSchedule(String moduleCode) throws InvalidObjectException,
            FailPrereqException, InvalidPrereqException {
        try{
            Module module = schedule.getModule(moduleCode);

            ArrayList<String> modulePrereq = getModulePrereqBasedOnCourse(moduleCode,this.getMajor());

            schedule.completeModule(module,modulePrereq);
            this.completedModuleCredits += module.getModuleCredits();

            displaySuccessfulCompleteMessage();
        } catch (IOException e) {
            displaySocketError();
        }


    }
    /**
     * Deletes a module with the specified module code. This method also updates the completed
     * module credits and removes the module from the planned modules list.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module to be deleted.
     * @throws MandatoryPrereqException If deleting the module fails due to prerequisite dependencies.
     */
    public void deleteModuleFromSchedule(String moduleCode) throws
            MandatoryPrereqException,
            MissingModuleException,
            IOException {
        try{
            Module module = schedule.getModule(moduleCode);
            schedule.deleteModule(moduleCode);
            if(module.getCompletionStatus()){
                this.completedModuleCredits -= module.getModuleCredits();
            }

        }catch (InvalidObjectException e) {
            throw new MissingModuleException("Module does not exist in schedule");
        }
    }

    public void shiftModuleInSchedule(String moduleCode, int targetSem) throws IllegalArgumentException,
            FailPrereqException, MissingModuleException, IOException, MandatoryPrereqException {
        this.schedule.shiftModule(moduleCode, targetSem);
    }

    public void clearAllModulesFromSchedule() {
        //Replaces current schedule with new schedule
        this.schedule = new Schedule();
        this.completedModuleCredits = 0;
    }

    //@@author janelleenqi
    public Module getModuleFromSchedule(String moduleCode) throws MissingModuleException {
        try {
            return schedule.getModule(moduleCode);
        } catch (InvalidObjectException e) {
            throw new MissingModuleException(moduleCode + " is not in Modules Planner. " +
                    "Please add the module to your schedule first!");
        }
    }

    public boolean completionStatusModuleSchedule(Module module) {
        return module.getCompletionStatus();
    }

    //@@author
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    /**
     * Retrieves the module codes that are left to be completed in the major's curriculum.
     * <p>
     * This method compares the list of major module codes with the list of completed module codes
     * in the current schedule. It returns a list of module codes that are still left to be completed
     * as per the major's curriculum.
     *
     * @return An ArrayList of Strings representing module codes that are left to be completed.
     */
    public ArrayList<String> getModuleCodesLeft() {
        ArrayList<String> moduleCodesLeft = new ArrayList<String>();
        ArrayList<String> completedModuleCodes = schedule.getModulesPlanned().getModulesCompleted();

        for (String moduleCode : majorModuleCodes) {
            if (!completedModuleCodes.contains(moduleCode)) {
                moduleCodesLeft.add(moduleCode);
            }
        }
        return moduleCodesLeft;
    }

    public ArrayList<String> getMajorModuleCodes() {
        return majorModuleCodes;
    }

    public ModuleList getModulesPlanned() {
        return schedule.getModulesPlanned();
    }

    public void printSchedule() {
        this.schedule.printMainModuleList();
    }

    /**
     * Sets the current semester modules for the student based on their year and semester.
     *
     * @author @rohitcube
     */
    public void setCurrentSemesterModules() {
        try {
            int currSem = getCurrentSem();

            int[] modulesPerSem = schedule.getModulesPerSem();
            // modules planned for all sems
            ModuleList modulesPlanned = schedule.getModulesPlanned();
            int numberOfModulesInCurrSem = modulesPerSem[currSem - 1];
            int numberOfModulesCleared = 0;
            for (int i = 0; i < currSem - 1; i++) {
                numberOfModulesCleared += modulesPerSem[i];
            }
            int startIndex = numberOfModulesCleared;
            int endIndex = startIndex + numberOfModulesInCurrSem;
            currentSemesterModules = new ModuleList();
            for (int i = startIndex; i < endIndex; i++) {
                currentSemesterModules.addModule(modulesPlanned.getModuleByIndex(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("why array out of bounds bruh");
        } catch (NullPointerException e) {
            System.out.print("null ptr exception");
        }
    }

    public int getCurrentSem() {
        int[] yearAndSem = Parser.parseStudentYear(year);
        return ((yearAndSem[0] - 1) * 2) + yearAndSem[1];
    }

    /**
     * Sets the current semester modules with each module as a ModuleWeekly class.
     *
     * @author @rohitcube
     */
    public void setCurrentSemesterModulesWeekly() throws TimetableUnavailableException {
        if (currentSemesterModules == null || currentSemesterModules.getMainModuleList().isEmpty()) {
            timetable.removeAll();
            int currentSem = getCurrentSem();
            throw new TimetableUnavailableException(
                    addOrRecommendGuide("Timetable view is unavailable as your current semester has " +
                            "no modules yet.", currentSem));
        }
        ArrayList<Module> newCurrentSemModuleList = currentSemesterModules.getMainModuleList();
        ArrayList<ModuleWeekly> currentSemesterModulesWeekly = timetable.getCurrentSemesterModulesWeekly();
        for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
            ModuleWeekly currModule = currentSemesterModulesWeekly.get(i);
            String currModuleCode = currModule.getModuleCode();
            if (isExistInCurrentSemesterModule(currModuleCode, newCurrentSemModuleList)) {
                continue;
            }
            timetable.removeFromCurrentSemesterModulesWeekly(currModule);
        }

        for (int i = 0; i < newCurrentSemModuleList.size(); i++) {
            String currModuleCode = newCurrentSemModuleList.get(i).getModuleCode();
            if (isExistInCurrentSemesterModuleWeekly(currModuleCode, currentSemesterModulesWeekly)) {
                continue;
            }
            ModuleWeekly currModule = new ModuleWeekly(currModuleCode);
            timetable.addToCurrentSemesterModulesWeekly(currModule);
        }
    }

    public void updateTimetable() throws TimetableUnavailableException {
        this.setCurrentSemesterModules();
        this.setCurrentSemesterModulesWeekly();
    }


    /**
     * Executes 'show' or 'modify' subcommands under the timetable command.
     * @author @rohitcube
     * @param argument The user input specifying whether to show or modify the timetable.
     */
    public void timetableShowOrModify(String argument) {
        try {
            this.updateTimetable();
            ModuleServiceController moduleServiceController = new ModuleServiceController();
            argument = argument.trim().toUpperCase().replace("\r", "");
            switch (argument) {
            case "SHOW": {
                moduleServiceController.showTimetable(timetable.getCurrentSemesterModulesWeekly());
                break;
            }
            case "MODIFY": {

                moduleServiceController.modifyTimetable(this);
                break;
            }
            default: {
                System.out.println("Invalid command (not show or modify). Please try again");
            }
            }
        } catch (InvalidModifyArgumentException e) {
            System.out.println("Invalid argument. Please try again.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds exception.");
        } catch (TimetableUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * Retrieves the ModuleWeekly object for a given module code.
     * @author @rohitcube
     * @param moduleCode                   The module code to search for.
     * @param currentSemesterModulesWeekly The list of ModuleWeekly objects for the current semester.
     * @return The ModuleWeekly object corresponding to the provided module code, or null if not found.
     */
    public static ModuleWeekly getModuleWeekly(String moduleCode,
                                               ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
            ModuleWeekly module = currentSemesterModulesWeekly.get(i);
            if (module.getModuleCode().equals(moduleCode)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Retrieves the index of the ModuleWeekly object for a given module code.
     * @author @rohitcube
     * @param moduleCode                   The module code to search for.
     * @param currentSemesterModulesWeekly The list of ModuleWeekly objects for the current semester.
     * @return The index of the ModuleWeekly object corresponding to the provided module code, or -1 if not found.
     */
    public static int getIndexOfModuleWeekly(String moduleCode,
                                             ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
            ModuleWeekly module = currentSemesterModulesWeekly.get(i);
            if (module.getModuleCode().equals(moduleCode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if a module with a given module code exists in the current semester modules.
     * @author @rohitcube
     * @param moduleCode                   The module code to search for.
     * @param currentSemesterModulesWeekly The list of ModuleWeekly objects for the current semester.
     * @return true if the module exists, false otherwise.
     */
    public static boolean isExistInCurrentSemesterModuleWeekly(String moduleCode,
                                                          ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        for (ModuleWeekly module : currentSemesterModulesWeekly) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistInCurrentSemesterModule(String moduleCode,
                                                               ArrayList<Module> currentSemesterModulesWeekly) {
        for (Module module : currentSemesterModulesWeekly) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public ModuleList getCurrentSemesterModules() {
        return currentSemesterModules;
    }

    public Timetable getTimetable() {
        return this.timetable;
    }

}
