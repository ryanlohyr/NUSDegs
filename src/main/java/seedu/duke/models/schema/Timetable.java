package seedu.duke.models.schema;

import seedu.duke.views.TimetableView;

import java.util.ArrayList;

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

    //@@author janelleenqi
    /**
     * Removes all ModuleWeekly from the current semester's weekly schedule.
     */
    public void removeAll() {
        currentSemesterModulesWeekly = new ArrayList<>();
    }

    //@@author

    public ArrayList<ModuleWeekly> getCurrentSemesterModulesWeekly() {
        return currentSemesterModulesWeekly;
    }

    public void modify(Student student) {

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

    //@@author janelleenqi
    /**
     * Checks if there are any lessons in the current semester's weekly schedule, indicating the availability
     * of a timetable view.
     *
     * @return true if there are lessons present, indicating timetable view can be printed, else otherwise.
     */
    public boolean timetableViewIsAvailable() {
        for (ModuleWeekly moduleWeekly : currentSemesterModulesWeekly) {
            if (moduleWeekly.haveLessons()) {
                return true;
            }
        }
        return false;
    }

    //@@author


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





    public void lessonsController(String lessonType, int indexOfModule, int time, int duration, String day) {
        //TO BE REFACTORED
        // parsing of day should be validated in the same as the above ^ if statements. Did not change this
        //for you yet, but parserDayForModify should be moved up, and only if the day is valid as well,
        // then you enter the switch statement. done
        switch (lessonType) {
        case "LECTURE": {
            timetable.currentSemesterModulesWeekly.get(indexOfModule).addLecture(day,
                    time, duration);
            TimetableView.printTimetable(currentSemesterModulesWeekly);
            return;
        }
        case "TUTORIAL": {
            timetable.currentSemesterModulesWeekly.get(indexOfModule).addTutorial(day,
                    time, duration);
            TimetableView.printTimetable(currentSemesterModulesWeekly);
            return;
        }
        case "LAB": {
            timetable.currentSemesterModulesWeekly.get(indexOfModule).addLab(day,
                    time, duration);
            TimetableView.printTimetable(currentSemesterModulesWeekly);
            return;
        }
        default: {
            System.out.println("Invalid Command. Please try again!");
        }
        }


    }

    /**
     * Retrieves the timetable associated with the current user or context.
     *
     * @return The Timetable object representing the timetable.
     */
    public Timetable getTimetable() {
        return timetable;
    }

}
