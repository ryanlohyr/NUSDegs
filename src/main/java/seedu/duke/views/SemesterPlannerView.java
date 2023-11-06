package seedu.duke.views;

import seedu.duke.models.schema.Module;
import seedu.duke.models.schema.ModuleList;

import java.util.ArrayList;

public class SemesterPlannerView {
    public static void print(String output) {
        System.out.print(output);
    }

    public static void println() {
        System.out.println();
    }

    public static void printSemesterPlanner(int[] modulesPerSem, ModuleList modulesPlanned) {
        int moduleCounter = 0;
        for (int i = 0; i < modulesPerSem.length; i++) {
            print("Sem " + (i + 1) + ":   ");
            for (int j = 0; j < modulesPerSem[i]; j++) {
                Module currentModule = modulesPlanned.getModuleByIndex(moduleCounter);
                print(getCompletionStatusForPrinting(currentModule) + " " + currentModule + "   ");
                moduleCounter++;
            }
            println();
        }
    }

    public static String getCompletionStatusForPrinting(Module module) {
        if (module.getCompletionStatus()) {
            return "✓";
        }
        return "X";
    }
}
