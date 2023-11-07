package seedu.duke.models.schema;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.exceptions.MissingModuleException;
import seedu.duke.utils.exceptions.InvalidPrereqException;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.Api.getModuleFulfilledRequirements;
import static seedu.duke.models.logic.Api.doesModuleExist;
import static seedu.duke.models.logic.Api.satisfiesAllPrereq;
import static seedu.duke.models.logic.DataRepository.getRequirements;
import static seedu.duke.views.SemesterPlannerView.printSemesterPlanner;

/**
 * The `Schedule` class represents a student's course schedule.
 * It allows a student to manage and manipulate their enrolled modules across multiple semesters.
 */
public class Schedule {

    private static final int MAXIMUM_SEMESTERS = 8;
    protected int[] modulesPerSem;
    private ModuleList modulesPlanned;
    private HashMap<String, Module> completedModules;

    private HashMap<String, ArrayList<String>> prereqMap;


    /*
    public ArrayList<Module> getCurrentSemesterModules() {
        int[] yearAndSem = Parser.parseStudentYear(year);
        int currSem = ((yearAndSem[0] - 1) * 2) + yearAndSem[1];
        int numberOfModulesInCurrSem = modulesPerSem[currSem - 1];
        int numberOfModulesCleared = 0;
        for (int i = 0; i < currSem - 1; i++) {
            numberOfModulesCleared += modulesPerSem[i];
        }
        int startIndex = currSem - 1;
        int endIndex = startIndex + numberOfModulesCleared;
        ArrayList<Module> modulesInSchedule = modulesPlanned.getMainModuleList();
        ArrayList<Module> currentSemesterModules = null;
        for (int i = startIndex; i < endIndex; i++) {
            currentSemesterModules.add(modulesInSchedule.get(i));
        }
        return currentSemesterModules;
    }
    */
    /**
     * Constructs a new `Schedule` with the provided modules and distribution across semesters.
     *
     * @param modules      A string containing module codes representing the student's schedule.
     * @param modulesPerSem An array indicating the distribution of modules across semesters.
     */
    public Schedule(String modules, int[] modulesPerSem) {
        this.modulesPerSem = modulesPerSem;
        modulesPlanned = new ModuleList(modules);
        completedModules = new HashMap<String, Module>();

    }

    /**
     * Constructs a new, empty `Schedule` with no modules and a default semester distribution.
     */
    public Schedule() {
        modulesPerSem = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        modulesPlanned = new ModuleList();
        completedModules = new HashMap<String, Module>();
    }

    /**
     * Retrieves the maximum number of semesters allowed in a student's course schedule.
     *
     * @return The maximum number of semesters allowed.
     */
    public static int getMaximumSemesters() {
        return MAXIMUM_SEMESTERS;
    }

    public ModuleList getModulesPlanned() {
        return modulesPlanned;
    }

    public int[] getModulesPerSem() {
        return modulesPerSem;
    };

    /**
     * Adds a recommended schedule list to the current schedule, updating completion statuses if needed.
     *
     * This method adds a list of recommended schedule modules to the current schedule. You can choose to
     * either keep or clear the completion statuses of modules. The recommended schedule modules are added
     * to the schedule, taking into account prerequisites and distributing them across semesters based on
     * fulfillment of prerequisites.
     *
     * @author ryanlohyr
     * @param scheduleToAdd The list of recommended schedule modules to add.
     * @param keep          A boolean indicating whether to keep or clear completion statuses.
     *                     If true, completion statuses are kept; if false, completion statuses are cleared.
     */
    public void addRecommendedScheduleListToSchedule(ArrayList<String> scheduleToAdd, boolean keep) {
        //update to store completion statuses
        if (keep) {
            completedModules = modulesPlanned.newHashMapOfCompleted();
        } else {
            completedModules.clear();
        }

        final int modsToAddPerSem = 5;
        int currentIndexOfMod = 0;
        int currentSem = 1;

        //overwrite
        modulesPlanned = new ModuleList();
        modulesPerSem = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

        for (String module : scheduleToAdd) {
            // Check if the module fulfill pre req, else we move it to next sem
            // ModuleList completedModules = new ModuleList(String.join(" ", getMainModuleList()));
            int indexToAdd = 0;
            for (int i = 1; i < currentSem; i++) {
                indexToAdd += this.modulesPerSem[i - 1];
            }

            //Sub list as we only want modules before the current target semester
            List<String> currentSemestersModules = scheduleToAdd.subList(indexToAdd, indexToAdd + currentIndexOfMod);
            ArrayList<String> currModulesPrereq = prereqMap.get(module);

            //now we check if the modules prereq is contained on current line
            for(String currModule:currentSemestersModules){
                if(currModulesPrereq.contains(currModule)){
                    currentSem += 1;
                    currentIndexOfMod = 0;
                }
            }

            try {
                addModuleWithoutCheckingPrereq(module, currentSem);
            } catch (InvalidObjectException | IllegalArgumentException e){
                throw new RuntimeException(e);
            }

            currentIndexOfMod += 1;
            if (currentIndexOfMod >= modsToAddPerSem){
                currentIndexOfMod = 0;
                currentSem += 1;
            }
            if (currentSem > 8) {
                return;
            }
        }
    }

    /**
     * Adds a module to the schedule for a specified semester.
     *
     * @param module The module code to be added.
     * @param targetSem The target semester (an integer from 1 to 8) in which to add the module.
     * @throws IllegalArgumentException If the provided semester is out of the valid range (1 to 8),
     *     or if the module already exists in the schedule, or if the module is not valid.
     * @throws InvalidObjectException If the module is null.
     * @throws FailPrereqException If the prerequisites for the module are not satisfied
     */
    public void addModule(String module, int targetSem) throws IllegalArgumentException, InvalidObjectException,
            FailPrereqException {

        if (targetSem < 1 || targetSem > MAXIMUM_SEMESTERS) {
            throw new IllegalArgumentException("Please select an integer from 1 to 8 for semester selection");
        }

        try {
            if (modulesPlanned.existsByCode(module)) {
                throw new IllegalArgumentException("Module already exists in the schedule");
            }
        } catch (InvalidObjectException e) {
            throw new InvalidObjectException("Module cannot be null");
        }

        int indexToAdd = 0;
        for (int i = 1; i < targetSem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }

        //Sub list as we only want modules before the current target semester
        List<String> plannedModulesArray = modulesPlanned.getModuleCodes().subList(0, (indexToAdd));
        ModuleList plannedModules = new ModuleList(String.join(" ", plannedModulesArray));

        try {
            if (satisfiesAllPrereq(module, plannedModules)) {
                //module initialization will be here

                modulesPlanned.addModule(indexToAdd, new Module(module));
                modulesPerSem[targetSem - 1] += 1;
                return;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Please select a valid module");
        }
        throw new FailPrereqException("Unable to add module as prerequisites not satisfied for: " + module);
    }

    /**
     * Deletes a module from the schedule by its module code.
     *
     * @param module The module code to be deleted from the schedule.
     * @throws FailPrereqException If the module to be deleted is a prerequisite for other modules in the schedule.
     * @throws IllegalArgumentException If the provided module code is not valid, the module is not in the schedule
     */
    public void deleteModule(String module) throws FailPrereqException, MissingModuleException {

        if (!doesModuleExist(module)) {
            throw new MissingModuleException("Module does not exist in schedule");
        }

        int targetIndex = modulesPlanned.getIndexByString(module);

        int targetSem = 1;
        int moduleCount = modulesPerSem[0];

        while ((moduleCount - 1) < targetIndex) {
            moduleCount += modulesPerSem[targetSem];
            targetSem += 1;
        }

        ArrayList<String> requirementsFulfilledFromModule = getModuleFulfilledRequirements(module);

        List<String> modulesAheadArray;
        int lastModuleIndex = modulesPlanned.getMainModuleList().size() - 1;
        int nextSemStartingIndex = moduleCount;

        try {
            modulesAheadArray = modulesPlanned.getModuleCodes().subList(nextSemStartingIndex, lastModuleIndex + 1);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            modulesAheadArray = new ArrayList<>();
        }

        for(String fulfilledModule: requirementsFulfilledFromModule ){
            //over here we check if the semesters in front of us contain a module in fulfilled module
            if(modulesAheadArray.contains(fulfilledModule)){
                throw new FailPrereqException("Unable to delete module. This module is a prerequisite for "
                        + fulfilledModule);
            }
        }

        modulesPlanned.deleteModulebyCode(module);

        modulesPerSem[targetSem - 1] -= 1;

        //        completedModules.deleteModulebyCode(module);
        //        int nextSemStartingIndex = moduleCount;
        //
        //        int lastModuleIndex = modulesPlanned.getMainModuleList().size() - 1;
        //        List<String> completedModulesArray = modulesPlanned.getModuleCodes().subList(0, nextSemStartingIndex);
        //        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));
        //        completedModules.deleteModulebyCode(module);
        //
        //        List<String> modulesAheadArray;
        //        try {
        //       modulesAheadArray = modulesPlanned.getModuleCodes().subList(nextSemStartingIndex, lastModuleIndex + 1);
        //        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        //            modulesAheadArray = new ArrayList<>();
        //        }
        //
        //        try {
        //            for (String moduleAhead : modulesAheadArray){
        //                if (!satisfiesAllPrereq(moduleAhead, completedModules)) {
        //                   throw new FailPrereqException("Unable to delete module. This module is a prerequisite for "
        //                            + moduleAhead);
        //                }
        //            }
        //        } catch (IllegalArgumentException e) {
        //            // This catch should never occur as it should not be possible to add an invalid module
        //            assert false;
        //            throw new IllegalArgumentException("Invalid Module in Schedule");
        //        }

        //        modulesPerSem[targetSem - 1] -= 1;
        //
    }

    public Module getModule(String moduleCode) throws InvalidObjectException {
        return modulesPlanned.getModule(moduleCode);
    }

    /**
     * Adds a module to the schedule for a specified semester.
     *
     * @param moduleCode The module code to be added.
     * @param targetSem The target semester (an integer from 1 to 8) in which to add the module.
     * @throws IllegalArgumentException If the provided semester is out of the valid range (1 to 8),
     *     or if the module already exists in the schedule, or if the module is not valid.
     * @throws InvalidObjectException If the module is null.
     * @throws FailPrereqException If the prerequisites for the module are not satisfied
     */
    public void addModuleWithoutCheckingPrereq(String moduleCode, int targetSem)
            throws InvalidObjectException, IllegalArgumentException {

        if (targetSem < 1 || targetSem > MAXIMUM_SEMESTERS) {
            throw new IllegalArgumentException("Please select an integer from 1 to 8 for semester selection");
        }

        try {
            if (modulesPlanned.existsByCode(moduleCode)) {
                throw new IllegalArgumentException("Module already exists in the schedule");
            }
        } catch (InvalidObjectException e) {
            throw new InvalidObjectException("Module cannot be null");
        }

        int indexToAdd = 0;
        for (int i = 1; i < targetSem; i++) {
            indexToAdd += this.modulesPerSem[i - 1];
        }

        //reuse module data if existed
        Module module = completedModules.get(moduleCode);
        if (module != null) {
            modulesPlanned.addModule(indexToAdd, module);
            modulesPerSem[targetSem - 1] += 1;
            return;
        }

        modulesPlanned.addModule(indexToAdd, new Module(moduleCode));
        modulesPerSem[targetSem - 1] += 1;
    }

    /**
     * Prints the student's course schedule, displaying modules organized by semesters.
     */

    public void printMainModuleList() {
        printSemesterPlanner(modulesPerSem, modulesPlanned);
    }

    /**
     * Generates a recommended schedule for a given course based on its requirements and prerequisites.
     *
     * @author ryanlohyr
     * @param course The course for which to generate a recommended schedule.
     * @return An ArrayList of strings representing the recommended schedule in order of completion.
     */
    public ArrayList<String> generateRecommendedSchedule(String course){
        ArrayList<String> requirements = getRequirements(course);
        HashMap<String, Integer> degreeMap = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        ArrayList<String> schedule = new ArrayList<>();
        HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();
        this.prereqMap = new HashMap<>();
        //initialisation
        for(String requirement: requirements) {
            adjacencyList.put(requirement, new ArrayList<>());
            degreeMap.put(requirement, 0);
        }

        for (String requirement : requirements) {
            ArrayList<String> prereqArray;
            try{
                prereqArray = getModulePrereqBasedOnCourse(requirement, course);
            } catch (InvalidPrereqException e){
                prereqArray = new ArrayList<>();
            }
            if (prereqArray == null) {
                prereqArray = new ArrayList<>();
            }

            prereqMap.put(requirement, prereqArray);

            //we need to create an adjacency list to add all the connections
            // from pre req -> item
            for (String s : prereqArray) {
                adjacencyList.get(s).add(requirement);
                Integer value = degreeMap.get(requirement) + 1;
                degreeMap.put(requirement, value);
            }
        }

        for (String key : degreeMap.keySet()) {
            Integer value = degreeMap.get(key);
            if(value == 0){
                q.offer(key);
            }
        }

        while(!q.isEmpty()){
            String curr = q.poll();
            schedule.add(curr);
            ArrayList<String> currReq = adjacencyList.get(curr);
            for (String s : currReq) {
                int num = degreeMap.get(s) - 1;
                degreeMap.put(s, num);
                if (num == 0) {
                    q.offer(s);
                }
            }
        }

        return schedule;
    }
}
