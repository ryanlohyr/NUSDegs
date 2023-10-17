package seedu.duke.models;

public class Student {

    private String name;
    private String major;

    public Student(String name, String major) {
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

    public String getMajor() {
        return major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
