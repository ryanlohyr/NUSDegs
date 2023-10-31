package seedu.duke.models.schema;

import java.util.ArrayList;
import java.util.List;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
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

    public void addRecommendedScheduleListToSchedule(ArrayList<String> scheduleToAdd){
        final int modsToAddPerSem = 5;
        int currentIndexOfMod = 0;
        int currentSem = 1;

        for (String module : scheduleToAdd) {
            //check if the module fulfill pre req, if not we move it to next sem
            ModuleList completedModules = new ModuleList(String.join(" ", getMainModuleList()));
            if(!satisfiesAllPrereq(module,completedModules)){
                System.out.println("completed modules");
                System.out.println(completedModules.getMainModuleList());
                System.out.println("this modules prereqs are "
                        + getModulePrereqBasedOnCourse(module.toUpperCase(),"CEG"));
                System.out.println(module + " module prereq was not satisfied current sem is " + currentSem);
                currentSem += 1;
                currentIndexOfMod = 0;
            }
            addModule(module, currentSem);
            currentIndexOfMod += 1;
            if(currentIndexOfMod >= modsToAddPerSem){
                currentIndexOfMod = 0;
                currentSem += 1;
            }
        }

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
                return true;
            }
            System.out.println("pre req not satisfied for: " + module);
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
