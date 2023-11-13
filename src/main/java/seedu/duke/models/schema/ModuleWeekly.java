package seedu.duke.models.schema;

import seedu.duke.utils.errors.UserError;

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
    }

    public ModuleWeekly(String moduleCode) {
        super(moduleCode);
        this.moduleCode = moduleCode;
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


    public void setDay(String day) {
        this.day = day;
    }


    public boolean exists(Event newEvent) {
        for (Event existingEvent : lessons) {
            if (newEvent.equals(existingEvent)) {
                return true;
            }
        }
        return false;
    }

    public boolean haveLessons() {
        return !lessons.isEmpty();
    }

    public boolean canAddToTimetable(Event event) {
        if (this.exists(event)) {
            UserError.displayLessonAlreadyAdded(event);
            return false;
        }
        return true;
    }

    public void addLecture(String day, int time, int duration) {
        Event newLecture = new Lecture(day, time, duration, moduleCode);
        if (canAddToTimetable(newLecture)) {
            lessons.add(newLecture);
        }
    }

    public void addTutorial(String day, int time, int duration) {
        Event newTutorial = new Tutorial(day, time, duration, moduleCode);
        if (canAddToTimetable(newTutorial)) {
            lessons.add(newTutorial);
        }
    }

    public void addLab(String day, int time, int duration) {
        Event newLab = new Lab(day, time, duration, moduleCode);
        if (canAddToTimetable(newLab)) {
            lessons.add(newLab);
        }
    }


    public void clearLessons() {
        lessons.clear();
    }


    public ArrayList<Event> getWeeklyTimetable() {
        return lessons;
    }



}

