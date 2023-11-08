package seedu.duke.models.schema;

import seedu.duke.exceptions.InvalidModifyArgumentException;
import seedu.duke.utils.exceptions.MandatoryPrereqException;
import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.utils.exceptions.MissingModuleException;

import seedu.duke.utils.Parser;
import seedu.duke.utils.exceptions.InvalidPrereqException;
import seedu.duke.views.TimetableView;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.DataRepository.getRequirements;
import static seedu.duke.utils.Parser.parserDayForModify;
import static seedu.duke.utils.Parser.parserTimeForModify;
import static seedu.duke.utils.Parser.parserDurationForModify;
import static seedu.duke.views.CommandLineView.displaySuccessfulCompleteMessage;
import static seedu.duke.views.UserGuideView.addOrRecommendGuide;
import static seedu.duke.views.UserGuideView.timetableModifySuccessful;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private static boolean intitialise;
    private static int counter;
    public ArrayList<ModuleWeekly> currentSemesterModulesWeekly;
    private String name;
    private String major;
    private Schedule schedule;
    private String year;
    private int completedModuleCredits;
    private ArrayList<String> majorModuleCodes;
    private ModuleList currentSemesterModules;






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
        counter++;
    }

    /**
     * Constructs a student with a null name, null major, and an empty module schedule.
     */
    public Student() {
        this.name = null;
        this.major = null;
        this.schedule = new Schedule();
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

    public void addModuleSchedule(String moduleCode, int targetSem) throws IllegalArgumentException,
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

        Module module = schedule.getModule(moduleCode);

        ArrayList<String> modulePrereq = getModulePrereqBasedOnCourse(moduleCode,this.getMajor());

        schedule.completeModule(module,modulePrereq);
        this.completedModuleCredits += module.getModuleCredits();

        displaySuccessfulCompleteMessage();

    }
    /**
     * Deletes a module with the specified module code. This method also updates the completed
     * module credits and removes the module from the planned modules list.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module to be deleted.
     * @throws FailPrereqException If deleting the module fails due to prerequisite dependencies.
     */
    public void deleteModuleSchedule(String moduleCode) throws MandatoryPrereqException, MissingModuleException {
        try{
            Module module = schedule.getModule(moduleCode);
            schedule.deleteModule(moduleCode);
            if(module.getCompletionStatus()){
                this.completedModuleCredits -= module.getModuleCredits();
            }
        }catch (InvalidObjectException e) {
            throw new MissingModuleException(e.getMessage());
        }
    }

    public void shiftModuleSchedule(String moduleCode, int targetSem) throws IllegalArgumentException,
            FailPrereqException, MissingModuleException, InvalidObjectException, MandatoryPrereqException {
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


    // ------------------ everything below this should be in seperate planner class ---------------------------
    // will update in the next PR

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
            ModuleList modulesPlanned = schedule.getModulesPlanned();
            int numberOfModulesInCurrSem = modulesPerSem[currSem - 1];
            int numberOfModulesCleared = 0;
            for (int i = 0; i < currSem - 1; i++) {
                numberOfModulesCleared += modulesPerSem[i];
            }
            int startIndex = numberOfModulesCleared;
            int endIndex = startIndex + numberOfModulesInCurrSem;
            ArrayList<Module> modulesInSchedule = modulesPlanned.getMainModuleList();
            currentSemesterModules = new ModuleList();
            for (int i = startIndex; i < endIndex; i++) {
                currentSemesterModules.addModule(modulesInSchedule.get(i));
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
    public void setCurrentSemesterModulesWeekly() {
        if (currentSemesterModules.getMainModuleList().isEmpty()) {
            int currentSem = getCurrentSem();
            addOrRecommendGuide("Your current sem has no modules yet.", currentSem);
        }

        ArrayList<Module> currentSemModuleList = currentSemesterModules.getMainModuleList();
        currentSemesterModulesWeekly = new ArrayList<>();
        for (int i = 0; i < currentSemModuleList.size(); i++) {
            String currModuleCode = currentSemModuleList.get(i).getModuleCode();
            ModuleWeekly currModule = new ModuleWeekly(currModuleCode);
            currentSemesterModulesWeekly.add(currModule);
        }
    }

    public void printCurrentSemesterModulesWeekly(Student student) {
        for (ModuleWeekly moduleweekly : student.currentSemesterModulesWeekly) {
            System.out.println(moduleweekly.getModuleCode());
            ArrayList<Event> weeklyschedule = moduleweekly.getWeeklyTimetable();
            if (weeklyschedule.isEmpty()) {
                System.out.println("aint nothin here");
            }
            for (int i = 0; i < weeklyschedule.size(); i++) {
                System.out.println(weeklyschedule.get(i).getEventType());
                System.out.println(weeklyschedule.get(i).getStartTime());
            }
        }
    }

    /**
     * Executes 'show' or 'modify' subcommands under the timetable command.
     * @author @rohitcube
     * @param student   The student object.
     * @param userInput The user input specifying whether to show or modify the timetable.
     */
    public void timetableShowOrModify(Student student, String userInput) {
        try {
            //while (!intitialise) {
            student.setCurrentSemesterModules();
            student.setCurrentSemesterModulesWeekly();
            //intitialise = true;
            //}
            String argument = userInput.substring(userInput.indexOf("timetable") + 9).trim().toUpperCase();
            switch (argument) {
            case "SHOW": {
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            case "MODIFY": {
                student.modifyTimetable(student);
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
        }
    }

    /**
     * Modifies the timetable for the specified student based on user input.
     * @author @rohitcube
     * @param student The student object.
     * @throws InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void modifyTimetable(Student student) throws InvalidModifyArgumentException {
        try {
            System.out.println("List of modules in current semester: ");
            for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
                System.out.println(currentSemesterModulesWeekly.get(i).getModuleCode());
            }
            Scanner in = new Scanner(System.in);
            System.out.println("Which current module do you want to modify?");
            String moduleCode = in.nextLine().trim().toUpperCase();
            if (!isExistInCurrentSemesterModules(moduleCode, student.currentSemesterModulesWeekly)) {
                System.out.println("Sorry that module doesn't exist in current semesters");
                return;
            }
            System.out.println("Ok that module exists. Enter what you would like to change in this way " +
                    "(lecture, tutorial, lab):\n " +
                    "[lecture /time 12 /duration 3 /day Tuesday], time range of values: 8-20");
            String userInput = in.nextLine().trim();
            // pass in the ModuleWeekly element from currentSemester
            int indexOfModuleWeeklyToModify = getIndexOfModuleWeekly(moduleCode, student.currentSemesterModulesWeekly);
            processModifyArguments(userInput, indexOfModuleWeeklyToModify, student);
            timetableModifySuccessful();
        } catch (InvalidModifyArgumentException e) {
            throw new InvalidModifyArgumentException();
        }
    }

    /**
     * Processes the modify arguments provided by the user and updates the module schedule accordingly.
     * @author @rohitcube
     * @param userInput     The user input specifying the modification.
     * @param indexOfModule The index of the ModuleWeekly object to be modified.
     * @param student       The student object.
     * @throws InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void processModifyArguments(String userInput, int indexOfModule, Student student)
            throws InvalidModifyArgumentException {
        try {
            int startIndexOfStart = userInput.indexOf("/time");
            String command = userInput.substring(0, startIndexOfStart).trim().toUpperCase();
            if (!command.equals("LECTURE") &&
                    !command.equals("TUTORIAL") &&
                    !command.equals("LAB")) {
                System.out.println("Not a valid command. Please try again!");
                return;
            }
            if (parserTimeForModify(userInput) < 8 || parserTimeForModify(userInput) > 20) {
                System.out.println("Not a valid time. Please try again!");
                return;
            }
            if (parserDurationForModify(userInput) < 1 ||
                    parserDurationForModify(userInput) > 20 - parserTimeForModify(userInput)) {
                System.out.println("Not a valid duration. Please try again!");
                return;
            }
            switch (command) {
            case "LECTURE": {
                student.currentSemesterModulesWeekly.get(indexOfModule).addLecture(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            case "TUTORIAL": {
                student.currentSemesterModulesWeekly.get(indexOfModule).addTutorial(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            case "LAB": {
                student.currentSemesterModulesWeekly.get(indexOfModule).addLab(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            default: {
                System.out.println("Invalid Command. Please try again!");
            }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidModifyArgumentException();
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
    public static boolean isExistInCurrentSemesterModules(String moduleCode,
                                                          ArrayList<ModuleWeekly> currentSemesterModulesWeekly) {
        for (ModuleWeekly module : currentSemesterModulesWeekly) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ModuleWeekly> getCurrentSemesterModulesWeekly() {
        return currentSemesterModulesWeekly;
    }

    public ModuleList getCurrentSemesterModules() {
        return currentSemesterModules;
    }


}
