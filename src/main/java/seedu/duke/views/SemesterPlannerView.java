package seedu.duke.views;

import seedu.duke.models.schema.Module;

import java.util.ArrayList;

public class SemesterPlannerView {
    public static void print(String output) {
        System.out.print(output);
    }

    public static void println() {
        System.out.println();
    }

    public static void printSemesterPlanner(int[] modulesPerSem, ArrayList<Module> modulesPlanned) {
        int moduleCounter = 0;
        for (int i = 0; i < modulesPerSem.length; i++) {
            print("Sem " + (i + 1) + ": ");
            for (int j = 0; j < modulesPerSem[i]; j++) {
                print(modulesPlanned.get(moduleCounter).getModuleCode() + " ");
                moduleCounter++;
            }
            println();
        }
    }
}
