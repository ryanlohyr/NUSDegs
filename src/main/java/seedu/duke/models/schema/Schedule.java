package seedu.duke.models.schema;

import java.util.ArrayList;
import java.util.List;

import static seedu.duke.models.logic.Api.satisfiesAllPrereq;

/**
 * The `Schedule` class represents a student's course schedule and extends the `ModuleList` class.
 * It allows a student to manage and manipulate their enrolled modules across multiple semesters.
 */
public class Schedule extends ModuleList {

    private static final int MAXIMUM_SEMESTERS = 8;
    private static final String MISSING_MODULE = "Module is not in schedule.";
    private static final String DEPENDENT_MODULE = "Unable to delete module. This module is a prerequisite for ";
    private static final String INVALID_MODULE = "Please select a valid module";
    private static final String DELETE_MODULE = "Module Successfully Deleted";
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

    /**
     * Adds a module to the student's course schedule at the specified target semester.
     *
     * @param module     The module code to be added.
     * @param targetSem  The target semester for adding the module.
     * @return `true` if the module is successfully added, `false` if the addition is not possible.
     */
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
                changeNumberOfModules(1);
                printMainModuleList();
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return false;
    }

    /**
     * Deletes a module from the student's course schedule.
     *
     * @param module The module code to be deleted from the schedule.
     * @return One of the following strings:
     *     - If the module is successfully deleted: {@value #DELETE_MODULE}
     *     - If the specified module does not exist in the schedule: {@value #MISSING_MODULE}
     *     - If another module is dependent on the specified module:
     *       {@value #DEPENDENT_MODULE} followed by the dependent module's code.
     *     - If the specified module is invalid: {@value #INVALID_MODULE}
     */
    public String deleteModule(String module) {

        int targetIndex = getMainModuleList().indexOf(module);

        if (targetIndex == -1) {
            return MISSING_MODULE;
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
                    return DEPENDENT_MODULE + moduleAhead;
                }
            }
        } catch (IllegalArgumentException e) {
            return INVALID_MODULE;
        }

        getMainModuleList().remove(module);
        modulesPerSem[targetSem - 1] -= 1;
        changeNumberOfModules(-1);

        return DELETE_MODULE;
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
