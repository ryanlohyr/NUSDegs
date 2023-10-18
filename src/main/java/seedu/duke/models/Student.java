package seedu.duke.models;

public class Student {

    private String name;
    private Major major;

    public Student(String name, Major major) {
        this.name = name;
        this.major = major;
    }

    public Student() {
        this.name = null;
        this.major = null;
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
}
