package seedu.duke.models.schema;

import seedu.duke.exceptions.FailPrereqException;

import java.io.InvalidObjectException;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private String name;
    private Major major;
    private Schedule schedule;
    private String year;
    private int completedModuleCredits;
    // private ArrayList<Module> ModulesTaken;

    /**
     * Constructs a student with a name, major, and module schedule.
     *
     * @param name     The name of the student.
     * @param major    The major of the student.
     * @param schedule The module schedule of the student.
     */
    public Student(String name, Major major, Schedule schedule) {
        this.name = name;
        this.major = major;
        this.schedule = schedule;
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

    public Major getMajor() throws NullPointerException {
        if (major == null) {
            throw new NullPointerException();
        }
        return major;
    }

    /**
     * Sets the first major without the major command
     * @author Isaiah Cerven
     * @param userInput must be validated in parser as CS or CEG
     */
    public void setFirstMajor(String userInput){
        try {
            setMajor(Major.valueOf(userInput.toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void addModule(String module, int targetSem) throws InvalidObjectException, FailPrereqException {
        this.schedule.addModule(module,targetSem);
    }

    public void printSchedule(){
        this.schedule.printMainModuleList();
    }

    public void deleteModule(String module) throws FailPrereqException {
        this.schedule.deleteModule(module);
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
    public void setMajor(Major major) {
        this.major = major;
    }
}
