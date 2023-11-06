package seedu.duke.models.schema;


import java.util.ArrayList;

public class ModuleWeekly extends Module {
    private int lectureTime;
    private int tutorialTime;
    private int labTime;
    private int lectureDuration;
    private int labDuration;
    private int tutorialDuration;
    private ArrayList<Event> lessons = new ArrayList<Event>();

    public ModuleWeekly(String moduleCode) throws NullPointerException, RuntimeException {
        super(moduleCode); // Call the constructor of the superclass (Module)
    }

    public ModuleWeekly(String moduleCode, int lectureTime, int tutorialTime,
                        int labTime, int lectureDuration, int labDuration, int tutorialDuration)
            throws NullPointerException, RuntimeException {
        super(moduleCode);
        this.lectureTime = lectureTime;
        this.tutorialTime = tutorialTime;
        this.labTime = labTime;
        this.lectureDuration = lectureDuration;
        this.labDuration = labDuration;
        this.tutorialDuration = tutorialDuration;
    }

    public int getLectureTime() {
        return lectureTime;
    }

    public int getTutorialTime() {
        return tutorialTime;
    }

    public int getLabTime() {
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

    public void setLectureTime(int lectureTime) {
        this.lectureTime = lectureTime;
    }

    public void setTutorialTime(int tutorialTime) {
        this.tutorialTime = tutorialTime;
    }

    public void setLabTime(int labTime) {
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

    public void addLecture(String day, int time, int duration) {
        lessons.add(new Lecture(day, time, duration));
    }

    public void addTutorial(String day, int time, int duration) {
        lessons.add(new Tutorial(day, time, duration));
    }

    public void addLab(String day, int time, int duration) {
        lessons.add(new Lab(day, time, duration));
    }

    //functions to alter lessons

    public ArrayList<Event> getWeeklySchedule() {
        return lessons;
    }
}

