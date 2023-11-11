package seedu.duke.views;

import seedu.duke.models.schema.Module;
import seedu.duke.models.schema.ModuleList;


public class SemesterPlannerView {
    private static void print(String output) {
        System.out.print(output);
    }

    private static void println() {
        System.out.println();
    }

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

    private static String getCompletionStatusForPrinting(Module module) {
        if (module.getCompletionStatus()) {
            return "✓";
        }
        return "X";
    }
}
