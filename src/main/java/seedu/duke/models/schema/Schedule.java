package seedu.duke.models.schema;

import seedu.duke.models.logic.ModuleList;

import java.io.InvalidObjectException;
import java.util.List;

import static seedu.duke.models.logic.Api.satisfiesAllPrereq;

public class Schedule extends ModuleList {

    protected int[] modulesPerSem;

    public Schedule(String modules, int[] modulesPerSem) {
        super(modules);
        this.modulesPerSem = modulesPerSem;
    }

    public Schedule() {
        super();
        this.modulesPerSem = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public String addModule(String[] words) {

        if (words.length != 3) {
            return "INVALID_FORMAT";
        }

        int targetSem = 0;
        try {
            //For now using sem 1, 2, 3...
            //Change to Y1/S1 format
            targetSem = Integer.parseInt(words[2]);
            if (targetSem > modulesPerSem.length) {
                return "INVALID_ACADEMIC_YEAR";
            }
        } catch (NumberFormatException e) {
            return "INVALID_ACADEMIC_YEAR";
        }

        int indexToAdd = 0;
        for (int i = 1; i < targetSem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }
        String module = words[1];

        List<String> completedModulesArray = getMainModuleList().subList(0, (indexToAdd));
        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));

        try {
            if(satisfiesAllPrereq(module, completedModules)) {
                this.getMainModuleList().add(indexToAdd, module);
                modulesPerSem[targetSem - 1] += 1;
                printMainModuleList();
                return "ADD_MODULE";
            }
        } catch (InvalidObjectException e) {
            //Replce with better handling
            return "PREREQ_FUNCTION_ERROR";
        }
        /*ArrayList<String> prereqs = getModulePrereqBasedOnCourse(module, String.valueOf(major));

        if (prereqs == null) {
            this.getMainModuleList().add(indexToAdd, module);
            modulesPerSem[targetSem - 1] += 1;
            printMainModuleList();
            return "ADD_MODULE";
        }

        for (String prereq : prereqs) {
            for (int j = 0; j < indexToAdd; j++) {
                if (prereq.equals(this.getMainModuleList().get(j))) {
                    prereqSatisfiedCount++;
                    break;
                }
            }
        }

        if (prereqSatisfiedCount == prereqs.size()) {
            this.getMainModuleList().add(indexToAdd, module);
            modulesPerSem[targetSem - 1] += 1;
            printMainModuleList();
            return "ADD_MODULE";
        }*/
        return "FAIL_PREREQ";
    }

    @Override
    public void printMainModuleList() {
        int moduleCounter = 0;
        for (int i = 0; i < modulesPerSem.length; i++) {
            System.out.print("Sem " + (i + 1) + ": ");
            for (int j = 0; j < modulesPerSem[i]; j++) {
                System.out.print(getMainModuleList().get(moduleCounter) + " ");
                moduleCounter++;
            }
            System.out.println();
        }
    }
}
