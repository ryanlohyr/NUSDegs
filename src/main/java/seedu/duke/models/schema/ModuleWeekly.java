package seedu.duke.models.schema;


import org.json.simple.JSONArray;
import seedu.duke.utils.exceptions.InvalidModuleCodeException;
import seedu.duke.models.logic.Api;

import java.util.ArrayList;

public class ModuleWeekly extends Module {

    private String moduleCode;
    private int lectureTime;
    private int tutorialTime;
    private int labTime;
    private int lectureDuration;
    private int labDuration;
    private int tutorialDuration;

    private String day;
    private ArrayList<Event> lessons = new ArrayList<Event>();

    public ModuleWeekly(String moduleCode, int lectureTime, int tutorialTime,
                        int labTime) throws NullPointerException, RuntimeException {
        super(moduleCode);
        this.lectureTime = lectureTime;
        this.tutorialTime = tutorialTime;
        this.labTime = labTime;
        //getDuration(moduleCode);
    }

    public ModuleWeekly(String moduleCode) {
        super(moduleCode);
        this.moduleCode = moduleCode;
        //getDuration(moduleCode);
        this.lectureTime = 8;
        this.labTime = 7;
        this.lectureDuration = 1;
        this.tutorialTime = 1;
        this.labDuration = 1;
    }

    public void printModuleWeekly(ModuleWeekly moduleWeekly) {
        System.out.println("lect time: " + moduleWeekly.getLectureTime());
        System.out.println("tut time: " + moduleWeekly.getTutorialTime());
        System.out.println("lab time: "+ moduleWeekly.getLabTime());
    }

    public String getModuleCode() {
        return moduleCode;
    }
    public int getLectureTime() {
        return lectureTime;
    }

    public String getDay() {
        return day;
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

    public void setDay(String day) {
        this.day = day;
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
        lessons.add(new Lecture(day, time, duration, moduleCode));
    }

    public void addTutorial(String day, int time, int duration) {
        lessons.add(new Tutorial(day, time, duration, moduleCode));
    }

    public void addLab(String day, int time, int duration) {
        lessons.add(new Lab(day, time, duration, moduleCode));
    }

    public void getDuration(String moduleCode) {
        JSONArray workloadCurrModule = null;
        try {
            workloadCurrModule = Api.getWorkload(moduleCode);
            if (workloadCurrModule == null) {
                throw new InvalidModuleCodeException();
            }
            int[] intArray = new int[workloadCurrModule.size()];
            System.out.println(workloadCurrModule.get(0));
            long longLectureDuration = (long) workloadCurrModule.get(0);
            long longTutorialDuration = (long) workloadCurrModule.get(1);
            long longLabDuration = (long) workloadCurrModule.get(2);
            this.lectureDuration = (int) longLectureDuration;
            this.tutorialTime = (int) longTutorialDuration;
            this.labDuration = (int) longLabDuration;
        } catch (InvalidModuleCodeException e) {
            System.out.println(" module weekly exception in get duration");
            throw new RuntimeException(e);
        }
    }

    //functions to alter lessons


    public ArrayList<Event> getWeeklySchedule() {
        return lessons;
    }



}

