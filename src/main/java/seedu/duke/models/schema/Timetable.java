package seedu.duke.models.schema;

import seedu.duke.views.TimetableView;

import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.utils.Parser.parserDayForModify;
import static seedu.duke.utils.Parser.parserTimeForModify;
import static seedu.duke.utils.Parser.parserDurationForModify;
public class Timetable {

    public static Timetable timetable = new Timetable();

    private ArrayList <ModuleWeekly> currentSemesterModulesWeekly;


    private Timetable() {
        currentSemesterModulesWeekly = new ArrayList<>();
    }

    public void addToCurrentSemesterModulesWeekly(ModuleWeekly module) {
        currentSemesterModulesWeekly.add(module);
    }

    public void removeFromCurrentSemesterModulesWeekly(ModuleWeekly module) {
        currentSemesterModulesWeekly.remove(module);
    }

    public ArrayList<ModuleWeekly> getCurrentSemesterModulesWeekly() {
        return currentSemesterModulesWeekly;
    }

    public void printCurrentSemesterModulesWeekly(Student student) {
        for (ModuleWeekly moduleweekly : currentSemesterModulesWeekly) {
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
     * Modifies the timetable for the specified student based on user input.
     * @author @rohitcube
     * @param student The student object.
     * @throws seedu.duke.exceptions.InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void modifyTimetable(Student student) throws seedu.duke.exceptions.InvalidModifyArgumentException {
        try {
            System.out.println("List of modules in current semester: ");
            if (currentSemesterModulesWeekly.isEmpty()) {
                System.out.println("There are no modules in your current semester. " +
                        "Please add in modules, or generate using the 'recommend' command.");
                return;
            }
            for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
                System.out.println(currentSemesterModulesWeekly.get(i).getModuleCode());
            }
            Scanner in = new Scanner(System.in);
            System.out.println("Which current module do you want to modify? (ENTER MODULE CODE)");
            String moduleCode = in.nextLine().trim().toUpperCase();
            if (!isExistInCurrentSemesterModules(moduleCode, timetable.currentSemesterModulesWeekly)) {
                System.out.println("Sorry that module doesn't exist in current semesters");
                return;
            }
            System.out.println("Ok that module exists. Enter what you would like to change in this way " +
                    "(lecture, tutorial, lab):\n " +
                    "[lecture /time 12 /duration 3 /day Tuesday], OR to clear all lessons, enter [clear]");
            String userInput = in.nextLine().trim();
            // pass in the ModuleWeekly element from currentSemester
            int indexOfModuleWeeklyToModify = getIndexOfModuleWeekly(moduleCode, currentSemesterModulesWeekly);
            processModifyArguments(userInput, indexOfModuleWeeklyToModify, student);
        } catch (seedu.duke.exceptions.InvalidModifyArgumentException e) {
            throw new seedu.duke.exceptions.InvalidModifyArgumentException();
        }
    }

    /**
     * Processes the modify arguments provided by the user and updates the module schedule accordingly.
     * @author @rohitcube
     * @param userInput     The user input specifying the modification.
     * @param indexOfModule The index of the ModuleWeekly object to be modified.
     * @param student       The student object.
     * @throws seedu.duke.exceptions.InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void processModifyArguments(String userInput, int indexOfModule, Student student)
            throws seedu.duke.exceptions.InvalidModifyArgumentException {
        try {
            if (!userInput.contains("/time") && userInput.trim().equalsIgnoreCase("CLEAR")) {
                timetable.currentSemesterModulesWeekly.get(indexOfModule).clearLessons();
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                System.out.println("All lessons for selected module are cleared.");
                return;
            }
            int startIndexOfStart = userInput.indexOf("/time");
            String command = userInput.substring(0, startIndexOfStart).trim().toUpperCase();
            if (!command.equals("LECTURE") &&
                    !command.equals("TUTORIAL") &&
                    !command.equals("LAB") && !command.equals("CLEAR")) {
                System.out.println("Not a valid command. Please try again!");
                return;
            }
            /*
            if (command.equals("CLEAR")) {
                timetable.currentSemesterModulesWeekly.get(indexOfModule).clearLessons();
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                System.out.println("All lessons for selected module are cleared.");
                return;

             */
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
                timetable.currentSemesterModulesWeekly.get(indexOfModule).addLecture(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            case "TUTORIAL": {
                timetable.currentSemesterModulesWeekly.get(indexOfModule).addTutorial(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            case "LAB": {
                timetable.currentSemesterModulesWeekly.get(indexOfModule).addLab(parserDayForModify(userInput),
                        parserTimeForModify(userInput), parserDurationForModify(userInput));
                TimetableView.printTimetable(currentSemesterModulesWeekly);
                break;
            }
            default: {
                System.out.println("Invalid Command. Please try again!");
            }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new seedu.duke.exceptions.InvalidModifyArgumentException();
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



}
