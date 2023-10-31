package seedu.duke.models.schema;

import java.util.List;

import static seedu.duke.models.logic.Api.satisfiesAllPrereq;

public class Schedule extends ModuleList {

    private static final int MAXIMUM_SEMESTERS = 8;
    protected int[] modulesPerSem;

    public Schedule(String modules, int[] modulesPerSem) {
        super(modules);
        this.modulesPerSem = modulesPerSem;
    }

    public Schedule() {
        super();
        this.modulesPerSem = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public static int getMaximumSemesters() {
        return MAXIMUM_SEMESTERS;
    }

    public boolean addModule(String module, int targetSem)  {

        if (targetSem < 1 || targetSem > MAXIMUM_SEMESTERS) {
            return false;
        }

        int indexToAdd = 0;
        for (int i = 1; i < targetSem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }

        List<String> completedModulesArray = getMainModuleList().subList(0, (indexToAdd));
        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));

        try {
            if (satisfiesAllPrereq(module, completedModules)) {
                this.getMainModuleList().add(indexToAdd, module);
                modulesPerSem[targetSem - 1] += 1;
                printMainModuleList();
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return false;
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
