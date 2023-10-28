package seedu.duke.models.schema;

import seedu.duke.models.logic.ModuleList;

import java.util.ArrayList;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;

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

    public void addModule(String module, int sem, Major major) {
        int indexToAdd = 0;
        for (int i = 1; i < sem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }
        int prereqSatisfiedCount = 0;
        ArrayList<String> prereqs = getModulePrereqBasedOnCourse(module, String.valueOf(major));

        if (prereqs == null) {
            this.getMainModuleList().add(indexToAdd, module);
            modulesPerSem[sem - 1] += 1;
            return;
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
            modulesPerSem[sem - 1] += 1;
            return;
        }
        System.out.print("prereq not satisfied\n");
    }
}
