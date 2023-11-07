package seedu.duke.models.schema;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.duke.exceptions.InvalidModuleCodeException;
import seedu.duke.models.logic.Api;

import java.util.ArrayList;

public class ModuleWeekly extends Module {
    private int lectureTime;
    private int tutorialTime;
    private int labTime;
    private int lectureDuration;
    private int labDuration;
    private int tutorialDuration;
    private ArrayList<Event> lessons = new ArrayList<Event>();




    public void getDuration(String moduleCode) {
        JSONArray workloadCurrModule = null;
        try {
            workloadCurrModule = Api.getWorkload(moduleCode);
            if (workloadCurrModule == null) {
                throw new InvalidModuleCodeException();
            }
            this.lectureDuration = (int) workloadCurrModule.get(0);
            this.tutorialTime = (int) workloadCurrModule.get(1);
            this.labDuration = (int) workloadCurrModule.get(2);
        } catch (InvalidModuleCodeException e) {
            throw new RuntimeException(e);
        }
    }

    public ModuleWeekly(String moduleCode, int lectureTime, int tutorialTime,
                        int labTime)
            throws NullPointerException, RuntimeException {
        super(moduleCode);
        this.lectureTime = lectureTime;
        this.tutorialTime = tutorialTime;
        this.labTime = labTime;
        getDuration(moduleCode);
    }

    public ModuleWeekly(String moduleCode) {
        super(moduleCode);
        getDuration(moduleCode);
        this.lectureTime = 0;
        this.tutorialTime = 0;
        this.labTime = 0;
    }

    public void printModuleWeekly(ModuleWeekly moduleWeekly) {
        System.out.println("lect time: " + moduleWeekly.getLectureTime());
        System.out.println("tut time: " + moduleWeekly.getTutorialTime());
        System.out.println("lab time: "+ moduleWeekly.getLabTime());
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

