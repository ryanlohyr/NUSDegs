package seedu.duke.models.schema;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private String name;
    private Major major;

    private Schedule schedule;

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
     */
    public Major getMajor() {
        return major;
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

    /**
     * Sets the major of the student based on user input.
     *
     * @param userInput The user input containing the new major.
     * @return A status message indicating the result of the operation:
     *         - "NEW_MAJOR" if the major was successfully updated.
     *         - "INVALID_MAJOR" if the user input contained an invalid major.
     *         - "CURRENT_MAJOR" if the user input did not specify a major.
     */
    public String updateMajor(String userInput) {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            return "CURRENT_MAJOR";
        }
        try {
            setMajor(Major.valueOf(words[1].toUpperCase()));
            return "NEW_MAJOR";
        } catch (IllegalArgumentException e) {
            return "INVALID_MAJOR";
        }
    }
}
