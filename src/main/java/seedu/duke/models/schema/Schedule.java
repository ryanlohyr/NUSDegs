package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import static seedu.duke.models.logic.Api.doesModuleExist;
import static seedu.duke.models.logic.Api.satisfiesAllPrereq;
import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;

/**
 * The `Schedule` class represents a student's course schedule and extends the `ModuleList` class.
 * It allows a student to manage and manipulate their enrolled modules across multiple semesters.
 */
public class Schedule extends ModuleList {

    private static final int MAXIMUM_SEMESTERS = 8;
    protected int[] modulesPerSem;

    /**
     * Constructs a new `Schedule` with the provided modules and distribution across semesters.
     *
     * @param modules      A string containing module codes representing the student's schedule.
     * @param modulesPerSem An array indicating the distribution of modules across semesters.
     */
    public Schedule(String modules, int[] modulesPerSem) {
        super(modules);
        this.modulesPerSem = modulesPerSem;
    }

    /**
     * Constructs a new, empty `Schedule` with no modules and a default semester distribution.
     */
    public Schedule() {
        super();
        this.modulesPerSem = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    /**
     * Retrieves the maximum number of semesters allowed in a student's course schedule.
     *
     * @return The maximum number of semesters allowed.
     */
    public static int getMaximumSemesters() {
        return MAXIMUM_SEMESTERS;
    }

    public void addRecommendedScheduleListToSchedule(ArrayList<String> scheduleToAdd){
        final int modsToAddPerSem = 5;
        int currentIndexOfMod = 0;
        int currentSem = 1;

        for (String module : scheduleToAdd) {
            //check if the module fulfill pre req, if not we move it to next sem
//            ModuleList completedModules = new ModuleList(String.join(" ", getMainModuleList()));
            int indexToAdd = 0;
            for (int i = 1; i < currentSem; i++) {
                indexToAdd += this.modulesPerSem[i - 1];
            }

            //Sub list as we only want modules before the current target semester
            List<String> completedModulesArray = getMainModuleList().subList(0, (indexToAdd));
            ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));
            if(!satisfiesAllPrereq(module,completedModules)){
                System.out.println("completed modules");
                System.out.println(completedModules);
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

    /**
     * Adds a module to the student's course schedule at the specified target semester.
     *
     * @param module     The module code to be added.
     * @param targetSem  The target semester for adding the module.
     * @return `true` if the module is successfully added, `false` if the addition is not possible.
     */
    public boolean addModule(String module, int targetSem)  {

        if (targetSem < 1 || targetSem > MAXIMUM_SEMESTERS) {
            System.out.println("Please select an integer from 1 to 8 for semester selection");
            return false;
        }

        try {
            if (exists(module)) {
                System.out.println("Module already exists in the schedule");
                return false;
            }
        } catch (InvalidObjectException e) {
            System.out.println("Module cannot be null");
            return false;
        }

        int indexToAdd = 0;
        for (int i = 1; i < targetSem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }

        //Sub list as we only want modules before the current target semester
        List<String> completedModulesArray = getMainModuleList().subList(0, (indexToAdd));
        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));

        try {
            if (satisfiesAllPrereq(module, completedModules)) {
                this.getMainModuleList().add(indexToAdd, module);
                modulesPerSem[targetSem - 1] += 1;
                changeNumberOfModules(1);
                return true;
            }
            System.out.println("completed modules");
            System.out.println(completedModules.getMainModuleList());
            System.out.println("this modules prereqs are "
                    + getModulePrereqBasedOnCourse(module.toUpperCase(),"CEG"));
            System.out.println("pre req not satisfied for: " + module);
        } catch (IllegalArgumentException e) {
            System.out.println("Please select a valid module");
            return false;
        }
        System.out.println("Unable to add module as prerequisites are not satisfied");
        return false;
    }

    /**
     * Deletes a module from the student's course schedule.
     *
     * @param module The module code to be deleted from the schedule.
     * @return `true` if the module is successfully deleted, `false` if deletion is not possible.
     */
    public boolean deleteModule(String module) {

        int targetIndex = getMainModuleList().indexOf(module);

        if (!doesModuleExist(module)) {
            System.out.println("Please select a valid module");
        }

        if (targetIndex == -1) {
            System.out.println("Module is not in schedule");
            return false;
        }

        int targetSem = 1;
        int moduleCount = modulesPerSem[0];

        while ((moduleCount - 1) < targetIndex) {
            moduleCount += modulesPerSem[targetSem];
            targetSem += 1;
        }

        int nextSemStartingIndex = moduleCount;

        int lastModuleIndex = getNumberOfModules() - 1;

        List<String> completedModulesArray = getMainModuleList().subList(0, nextSemStartingIndex);
        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));
        completedModules.getMainModuleList().remove(module);

        List<String> modulesAheadArray;
        try {
            modulesAheadArray = getMainModuleList().subList(nextSemStartingIndex, lastModuleIndex + 1);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            modulesAheadArray = new ArrayList<>();
        }

        try {
            for (String moduleAhead : modulesAheadArray){
                if (!satisfiesAllPrereq(moduleAhead, completedModules)) {
                    System.out.println("Unable to delete module. This module is a prerequisite for " + moduleAhead);
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Module in Schedule");
            return false;
        }

        getMainModuleList().remove(module);
        modulesPerSem[targetSem - 1] -= 1;
        changeNumberOfModules(-1);

        return true;
    }

    /**
     * Prints the student's course schedule, displaying modules organized by semesters.
     */

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
