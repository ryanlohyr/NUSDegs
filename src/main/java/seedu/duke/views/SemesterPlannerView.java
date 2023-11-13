package seedu.duke.views;

import seedu.duke.models.schema.Module;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Schedule;


/**
 * The SemesterPlannerView class provides methods to display and print the semester planner.
 */
public class SemesterPlannerView {
    private static void print(String output) {
        System.out.print(output);
    }

    private static void println() {
        System.out.println();
    }

    /**
     * Displays the schedule based on the provided schedule object.
     *
     * @param schedule The Schedule object containing the semester planner information.
     */
    public  static void displaySchedule (Schedule schedule){
        int[] modulesPerSem = schedule.getModulesPerSem();
        ModuleList modulesPlanned = schedule.getModulesPlanned();
        int moduleCounter = 0;
        int maxModulesPerColumn = 5;
        for (int i = 0; i < modulesPerSem.length; i++) {
            print("Sem " + (i + 1) + ":   ");
            for (int j = 0; j < modulesPerSem[i]; j++) {
                if(j != 0 && j % maxModulesPerColumn == 0){
                    System.out.println();
                    print("    " + " "+ "    ");
                }
                Module currentModule = modulesPlanned.getModuleByIndex(moduleCounter);
                String stringToPrint = getCompletionStatusForPrinting(currentModule) + " " + currentModule + "   ";
                System.out.printf("%-15s", stringToPrint);
                moduleCounter++;
            }
            println();
        }
    }

    /**
     * Prints the semester planner based on the provided modulesPerSem and modulesPlanned.
     *
     * @param modulesPerSem The array representing the number of modules per semester.
     * @param modulesPlanned The ModuleList containing the planned modules.
     */
    public static void printSemesterPlanner(int[] modulesPerSem, ModuleList modulesPlanned) {
        int moduleCounter = 0;
        int maxModulesPerColumn = 5;
        for (int i = 0; i < modulesPerSem.length; i++) {
            print("Sem " + (i + 1) + ":   ");
            for (int j = 0; j < modulesPerSem[i]; j++) {
                if(j != 0 && j % maxModulesPerColumn == 0){
                    System.out.println();
                    print("    " + " "+ "    ");
                }
                Module currentModule = modulesPlanned.getModuleByIndex(moduleCounter);
                String stringToPrint = getCompletionStatusForPrinting(currentModule) + " " + currentModule + "   ";
                System.out.printf("%-15s", stringToPrint);
                moduleCounter++;
            }
            println();
        }
    }

    //@@author janelleenqi
    /**
     * Retrieves the completion status indicator for a module, 'O' for completed, 'X' for not completed.
     *
     * @param module The Module object for which to determine the completion status.
     * @return The completion status indicator ('O' or 'X').
     */
    private static String getCompletionStatusForPrinting(Module module) {
        if (module.getCompletionStatus()) {
            return "O";
        }
        return "X";
    }
}
