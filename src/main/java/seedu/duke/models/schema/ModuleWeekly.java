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

    //@@author janelleenqi
    /**
     * Checks if a specific event exists in this ModuleWeekly.
     *
     * @param newEvent The event to check for existence.
     * @return true if the event exists, false otherwise.
     */
    public boolean exists(Event newEvent) {
        for (Event existingEvent : lessons) {
            if (newEvent.equals(existingEvent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are any lessons in the weekly schedule.
     *
     * @return true if there are lessons, false otherwise.
     */
    public boolean haveLessons() {
        return !lessons.isEmpty();
    }

    /**
     * Checks if an event can be added to the timetable and adds it if possible.
     *
     * @param event The event to add.
     * @return true if the event can be added, false otherwise.
     */
    public boolean canAddToTimetable(Event event) {
        if (this.exists(event)) {
            UserError.displayLessonAlreadyAdded(event);
            return false;
        }
        return true;
    }

    /**
     * Adds a lecture to the weekly timetable.
     *
     * @param day      The day of the lecture.
     * @param time     The time of the lecture.
     * @param duration The duration of the lecture.
     */
    public void addLecture(String day, int time, int duration) {
        Event newLecture = new Lecture(day, time, duration, moduleCode);
        if (canAddToTimetable(newLecture)) {
            lessons.add(newLecture);
        }
    }

    /**
     * Adds a tutorial to the weekly timetable.
     *
     * @param day      The day of the tutorial.
     * @param time     The time of the tutorial.
     * @param duration The duration of the tutorial.
     */
    public void addTutorial(String day, int time, int duration) {
        Event newTutorial = new Tutorial(day, time, duration, moduleCode);
        if (canAddToTimetable(newTutorial)) {
            lessons.add(newTutorial);
        }
    }

    /**
     * Adds a lab to the weekly timetable.
     *
     * @param day      The day of the tutorial.
     * @param time     The time of the tutorial.
     * @param duration The duration of the tutorial.
     */
    public void addLab(String day, int time, int duration) {
        Event newLab = new Lab(day, time, duration, moduleCode);
        if (canAddToTimetable(newLab)) {
            lessons.add(newLab);
        }
    }


    /**
     * Clears all lessons from the weekly timetable.
     */
    public void clearLessons() {
        lessons.clear();
    }


    /**
     * Gets the ArrayList of events representing the weekly timetable.
     *
     * @return The ArrayList of events.
     */
    public ArrayList<Event> getWeeklyTimetable() {
        return lessons;
    }
}

