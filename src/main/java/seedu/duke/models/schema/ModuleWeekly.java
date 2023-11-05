package seedu.duke.models.schema;

public class ModuleWeekly extends Module {
    private String lectureTime;
    private String tutorialTime;
    private String labTime;
    private int lectureDuration;
    private int labDuration;
    private int tutorialDuration;

    public ModuleWeekly(String moduleCode) throws NullPointerException, RuntimeException {
        super(moduleCode); // Call the constructor of the superclass (Module)
    }

    public ModuleWeekly(String moduleCode, String lectureTime, String tutorialTime,
                        String labTime, int lectureDuration, int labDuration, int tutorialDuration) throws NullPointerException, RuntimeException {
        super(moduleCode);
        this.lectureTime = lectureTime;
        this.tutorialTime = tutorialTime;
        this.labTime = labTime;
        this.lectureDuration = lectureDuration;
        this.labDuration = labDuration;
        this.tutorialDuration = tutorialDuration;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public String getTutorialTime() {
        return tutorialTime;
    }

    public String getLabTime() {
        return labTime;
    }

    public int getLectureDuration() {
        return lectureDuration;
    }

    public int getLabDuration() {
        return labDuration;
    }

    public int getTutorialDuration() {
        return tutorialDuration;
    }

    // Setter methods for ModuleWeekly specific fields

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public void setTutorialTime(String tutorialTime) {
        this.tutorialTime = tutorialTime;
    }

    public void setLabTime(String labTime) {
        this.labTime = labTime;
    }

    public void setLectureDuration(int lectureDuration) {
        this.lectureDuration = lectureDuration;
    }

    public void setLabDuration(int labDuration) {
        this.labDuration = labDuration;
    }

    public void setTutorialDuration(int tutorialDuration) {
        this.tutorialDuration = tutorialDuration;
    }
}

