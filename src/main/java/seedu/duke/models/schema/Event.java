package seedu.duke.models.schema;

import java.util.List;

//@@author janelleenqi
/**
 * Represents a scheduled event, with details like the day, start time,
 * duration, module code, and utility methods for time calculations and comparisons.
 */
public class Event {
    private static final List<String> days = List.of("monday", "tuesday", "wednesday", "thursday", "friday",
            "saturday", "sunday");
    private String day;
    private int startTime;
    private int duration;
    private String moduleCode;

    /**
     * Constructs an Event object with the specified day, start time, duration, and module code.
     *
     * @param day        The day of the event. Must not be null.
     * @param startTime  The start time of the event.
     * @param duration   The duration of the event.
     * @param moduleCode The module code associated with the event. Must not be null.
     */
    public Event(String day, int startTime, int duration, String moduleCode) {
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
        this.moduleCode = moduleCode;
    }

    /**
     * Gets the start time of the event.
     *
     * @return The start time.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Gets the duration of the event.
     *
     * @return The duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the day of the event as an integer index (0 for Monday, 1 for Tuesday, etc.).
     *
     * @return The index of the day.
     */
    public int getDayInt() {
        String lowercaseDay = day.toLowerCase();
        if (!days.contains(lowercaseDay)) {
            return -1;
        }

        return days.indexOf(lowercaseDay);
    }

    /**
     * Gets the day of the event.
     *
     * @return The day.
     */
    public String getDay() {
        return day;
    }

    /**
     * Gets the event type (empty string for general events).
     *
     * @return The event type.
     */
    public String getEventType() {
        return "";
    }

    /**
     * Gets the module code associated with the event.
     *
     * @return The module code.
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Calculates the time range for a given time period and duration.
     *
     * @param timePeriod The starting time period.
     * @param duration   The duration of the event.
     * @return A string representing the time range.
     */
    public static String getTime(int timePeriod, int duration) {
        String startTime = getTime(timePeriod);

        // time is outside 5am-11pm
        if (startTime.isEmpty()) {
            return "";
        }

        // event has no duration, just return start time
        if (duration == 0) {
            return "(" + startTime + ")";
        }

        String endTime = getTime(timePeriod + duration);

        // time is outside 5am-11pm, just return start time
        if (endTime.isEmpty()) {
            return "(" + startTime + ")";
        }

        return "(" + startTime + "-" + endTime + ")";
    }

    /**
     * Gets the time string for the given time period.
     *
     * @param timePeriod Index of the time period.
     * @return A string representing the time.
     */
    private static String getTime(int timePeriod) {
        if (5 <= timePeriod && timePeriod <= 11) {
            return (timePeriod) + "am";
        } else if (timePeriod == 12) {
            return (timePeriod) + "pm";
        } else if (13 <= timePeriod && timePeriod <= 23) {
            return (timePeriod - 12) + "pm";
        } else {
            // time is outside 5am-11pm
            return "";
        }
    }

    /**
     * Checks if two events are equal by comparing their day, start time, duration, and module code.
     *
     * @param event The event to compare with.
     * @return true if the events are equal, false otherwise.
     */
    public boolean equals(Event event) {
        if (this.getDayInt() != event.getDayInt()) {
            return false;
        }

        if (this.startTime != event.getStartTime()) {
            return false;
        }

        if (this.duration != event.getDuration()) {
            return false;
        }

        if (!this.moduleCode.equals(event.getModuleCode())) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the current event is earlier than another event based on start time, duration,
     * and module code.
     *
     * @param event The event to compare with.
     * @return true if the current event is earlier, false otherwise.
     */
    public boolean isEarlierThan(Event event) {
        // compare startTime
        if (this.startTime < event.getStartTime()) {
            return true;
        }
        if (this.startTime > event.getStartTime()) {
            return false;
        }

        // same startTime, compare duration
        if (this.duration < event.getDuration()) {
            return true;
        }
        if (this.duration > event.getDuration()) {
            return false;
        }

        int currentCharIndex = 0;
        while (currentCharIndex < this.moduleCode.length() && currentCharIndex < event.getModuleCode().length()) {
            // same startTime & duration, compare moduleCode characters
            if (this.moduleCode.charAt(currentCharIndex) < event.getModuleCode().charAt(currentCharIndex)) {
                return true;
            }
            if (this.moduleCode.charAt(currentCharIndex) > event.getModuleCode().charAt(currentCharIndex)) {
                return false;
            }
            currentCharIndex++;
        }

        // same startTime & duration, compare moduleCode length (shorter is earlier)
        if (this.moduleCode.length() < event.getModuleCode().length()) {
            return true;
        }
        if (this.moduleCode.length() > event.getModuleCode().length()) {
            return true;
        }

        // difference in Lecture, Tutorial, Lab
        return false; // no swap in bubble sort
    }

    /**
     * Generates a string representation of the event, which is the module code.
     *
     * @return The string representation of the event.
     */
    @Override
    public String toString() {
        return moduleCode;
    }


    /**
     * Generates a string representation of the event for saving purposes, which is the module code.
     *
     * @return The string representation of the event for saving.
     */
    public String toSave() {
        return moduleCode;
    }
}
