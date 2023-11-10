package seedu.duke.models.schema;

import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.views.TimetableView;

import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.utils.Parser.parserDayForModify;
import static seedu.duke.utils.Parser.parserTimeForModify;
import static seedu.duke.utils.Parser.parserDurationForModify;
import static seedu.duke.views.CommandLineView.displayMessage;
import static seedu.duke.views.TimetableUserGuideView.printTTModifyDetailedLessonGuide;

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
        //verify accepted timetableuser command
        try {
            System.out.println("List of modules in current semester: ");
            if (currentSemesterModulesWeekly.isEmpty()) {
                System.out.println("There are no modules in your current semester. " +
                        "Please add in modules, or generate using the 'recommend' command.");
                return;
            }
            for (ModuleWeekly moduleWeekly : currentSemesterModulesWeekly) {
                System.out.println(moduleWeekly.getModuleCode());
            }
            Scanner in = new Scanner(System.in);

            boolean inTimetableModifyMode = true;
            while (inTimetableModifyMode) {
                try {
                    printTTModifyDetailedLessonGuide("timetable modify:");
                    //messy possibly invalid user inputs
                    TimetableUserCommand currentTimetableCommand = new TimetableUserCommand(student, in.nextLine());
                    //clean very nice user inputs

                    //use clean arguments
                    String[] arguments = currentTimetableCommand.getArguments();

                    //if exit
                    inTimetableModifyMode = false;

                } catch (InvalidTimetableUserCommandException e) {
                    displayMessage(e.getMessage());
                }

                //ROHIT remove below && the larger try catch block


                System.out.println("Which current module do you want to modify? (ENTER MODULE CODE)");
                String moduleCode = in.nextLine().trim().toUpperCase();
                while (!isExistInCurrentSemesterModules(moduleCode, timetable.currentSemesterModulesWeekly)) {
                    displayMessage("Invalid Module, please choose a module from this semester");
                    moduleCode = in.nextLine().trim().toUpperCase().replace("\r", "");
                }

                // pass in the ModuleWeekly element from currentSemester
                int indexOfModuleWeeklyToModify = getIndexOfModuleWeekly(moduleCode, currentSemesterModulesWeekly);
                processModifyArguments(indexOfModuleWeeklyToModify, student);


            }


        } catch (seedu.duke.exceptions.InvalidModifyArgumentException e) {
            throw new seedu.duke.exceptions.InvalidModifyArgumentException();
        }
    }

    /**
     * Processes the modify arguments provided by the user and updates the module schedule accordingly.
     * @author @rohitcube
     * @param indexOfModule The index of the ModuleWeekly object to be modified.
     * @param student       The student object.
     * @throws seedu.duke.exceptions.InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void processModifyArguments(int indexOfModule, Student student)
            throws seedu.duke.exceptions.InvalidModifyArgumentException {

        Scanner in = new Scanner(System.in);

        String userInput;
        System.out.println("Example: lecture /time 12 /duration 3 /day Tuesday \n" +
                "Time range of values are 8-20 (where 8 refers to 0800 and 20 refers to 2000)");
        try {
            while(true){
                System.out.println("Enter what you would like to modify with the following arguments " +
                        "(lecture, tutorial, lab):");
                userInput = in.nextLine().trim().replace("\r", "");
                String[] wordArray = userInput.split(" ");
                String command = wordArray[0].toUpperCase();
                if (!command.equals("LECTURE") &&
                        !command.equals("TUTORIAL") &&
                        !command.equals("LAB")) {
                    System.out.println("Not a valid command. Please try again!");
                    continue;
                }
                try{
                    if (parserTimeForModify(userInput) < 8 || parserTimeForModify(userInput) > 20) {
                        //this is triggered when its a number but not a valid one.
                        System.out.println("Not a valid time. Please try again!");
                        continue;
                    }
                    if (parserDurationForModify(userInput) < 1 ||
                            parserDurationForModify(userInput) > 20 - parserTimeForModify(userInput)) {
                        //this is triggered when its a number but not a valid one.
                        System.out.println("Not a valid duration. Please try again!");
                        continue;
                    }
                }catch(IndexOutOfBoundsException e){
                    //This is triggered when empty/not a number (mentioned issues of this in PR 166)
                    System.out.println("Invalid duration or time");
                    continue;
                }

                switch (command) {
                case "LECTURE": {

                    //TO BE REFACTORED
                    // parsing of day should be validated in the same as the above ^ if statements. Did not change this
                    //for you yet, but parserDayForModify should be moved up, and only if the day is valid as well,
                    // then you enter the switch statement.
                    timetable.currentSemesterModulesWeekly.get(indexOfModule).addLecture(parserDayForModify(userInput),
                            parserTimeForModify(userInput), parserDurationForModify(userInput));
                    TimetableView.printTimetable(currentSemesterModulesWeekly);
                    return;
                }
                case "TUTORIAL": {
                    timetable.currentSemesterModulesWeekly.get(indexOfModule).addTutorial(parserDayForModify(userInput),
                            parserTimeForModify(userInput), parserDurationForModify(userInput));
                    TimetableView.printTimetable(currentSemesterModulesWeekly);
                    return;
                }
                case "LAB": {
                    timetable.currentSemesterModulesWeekly.get(indexOfModule).addLab(parserDayForModify(userInput),
                            parserTimeForModify(userInput), parserDurationForModify(userInput));
                    TimetableView.printTimetable(currentSemesterModulesWeekly);
                    return;
                }
                default: {
                    System.out.println("Invalid Command. Please try again!");
                }
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
