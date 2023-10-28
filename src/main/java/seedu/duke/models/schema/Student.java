package seedu.duke.models.schema;

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

    public Major getMajor() throws NullPointerException {
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
            return "currentMajor";
        }
        try {
            setMajor(Major.valueOf(words[1].toUpperCase()));
            return "newMajor";
        } catch (IllegalArgumentException e) {
            return "invalidMajor";
        }
    }
}
