package seedu.duke.models.schema;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.exceptions.MissingModuleException;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.models.logic.DataRepository.getRequirements;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private String name;
    private String major;
    private Schedule schedule;
    private String year;
    private int completedModuleCredits;
    private ArrayList<String> majorModuleCodes;

    /**
     * Constructs a student with a name, major, and module schedule.
     *
     * @param name     The name of the student.
     * @param major    The major of the student.
     * @param schedule The module schedule of the student.
     */
    public Student(String name, String major, Schedule schedule) {
        this.name = name;
        this.major = major;
        this.schedule = schedule;
        this.year = null;
    }

    /**
     * Constructs a student with a null name, null major, and an empty module schedule.
     */
    public Student() {
        this.name = null;
        this.major = null;
        this.schedule = new Schedule();
        this.year = null;
    }

    /**
     * Sets the class schedule of the student.
     *
     * @param schedule The new module schedule.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Retrieves the module schedule of the student.
     *
     * @return The module schedule of the student.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    public int getCurrentModuleCredits(){
        return completedModuleCredits;
    }

    /**
     * Retrieves the name of the student.
     *
     * @return The name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the major of the student.
     *
     * @return The major of the student.
     * @throws NullPointerException If the major has not been set (i.e., it is `null`).
     */

    public String getMajor(){
        return major;
    }

    /**
     * Sets the first major without the major command
     * @author Isaiah Cerven
     * @param userInput must be validated in parser as CS or CEG
     */
    public void setFirstMajor(String userInput){
        try {
            setMajor(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void addModuleSchedule(String moduleCode, int targetSem) throws InvalidObjectException, FailPrereqException {
        this.schedule.addModule(moduleCode, targetSem);
    }

    /**
     * Completes a module with the specified module code.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module to be completed.
     */
    public void completeModuleSchedule(String moduleCode) {
        for (Module module : schedule.getModulesPlanned().getMainModuleList()) {
            if (module.getModuleCode().equals(moduleCode)) {
                this.completedModuleCredits += module.getModuleCredits();
                module.markModuleAsCompleted();
                return;
            }
        }
    }


    /**
     * Deletes a module with the specified module code. This method also updates the completed
     * module credits and removes the module from the planned modules list.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module to be deleted.
     * @throws FailPrereqException If deleting the module fails due to prerequisite dependencies.
     */
    public void deleteModuleSchedule(String moduleCode) throws FailPrereqException, MissingModuleException {
        schedule.deleteModule(moduleCode);
        Module module;
        try {
            module = schedule.getModule(moduleCode);
        } catch (InvalidObjectException e) {
            throw new MissingModuleException(moduleCode + " is not in Modules Planner.");
        }
        completedModuleCredits -= module.getModuleCredits();
        schedule.getModulesPlanned().deleteModule(module);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Sets the name of the student.
     *
     * @param name The new name of the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the major of the student.
     *
     * @param major The new major to set.
     */
    public void setMajor(String major) {
        this.major = major;
        majorModuleCodes = getRequirements(major);
    }

    public ArrayList<String> getModuleCodesLeft () {
        ArrayList<String> moduleCodesLeft = new ArrayList<String>();
        ArrayList<String> completedModuleCodes = schedule.getModulesPlanned().getModulesCompleted();

        for (String moduleCode: majorModuleCodes) {
            if (!completedModuleCodes.contains(moduleCode)) {
                moduleCodesLeft.add(moduleCode);
            }
        }
        return moduleCodesLeft;
    }

    public ArrayList<String> getMajorModuleCodes() {
        return majorModuleCodes;
    }

    public ModuleList getModulesPlanned() {
        return schedule.getModulesPlanned();
    }

    public void printSchedule(){
        this.schedule.printMainModuleList();
    }
}
