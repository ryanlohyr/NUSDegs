package seedu.duke.models.schema;

public class Student {

    private String name;
    private Major major;

    private Schedule schedule;

    public Student(String name, Major major, Schedule schedule) {
        this.name = name;
        this.major = major;
        this.schedule = schedule;
    }

    public Student() {
        this.name = null;
        this.major = null;
        this.schedule = new Schedule();
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public Major getMajor() {
        return major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

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
