package seedu.duke.models.schema;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private static final List<String> days = List.of("monday", "tuesday", "wednesday", "thursday", "friday",
            "saturday", "sunday");
    private String day;
    private int startTime;
    private int duration;
    private String moduleCode;

    public Event(String day, int startTime, int duration, String moduleCode) {
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
        this.moduleCode = moduleCode;
    }

    public ArrayList<String> getByHour() {
        ArrayList<String> eventByHour = new ArrayList<>();
        int eventDurationLeft = getDuration();
        while (eventDurationLeft > 0) {
            String eventTimeData = getStartTime() + " " + (getStartTime() + 1);
            eventByHour.add(eventTimeData);
            eventDurationLeft -= 1;
        }
        return eventByHour;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public int getDayInt() {
        String lowercaseDay = day.toLowerCase();
        if (!days.contains(lowercaseDay)) {
            return -1;
        }

        return days.indexOf(lowercaseDay);
    }

    public String getEventType() {
        return "";
    }

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
        String endTime = getTime(timePeriod + duration);

        // time is outside 8am-8pm
        if (startTime.isEmpty() || endTime.isEmpty()) {
            return "";
        }

        return "(" + startTime + "-" + endTime + ")";
    }

    /**
     * Gets the time string for the given time period.
     *
     * @param timePeriod Index of the time period.
     * @return A string representing the time.
     */
    public static String getTime(int timePeriod) {
        if (8 <= timePeriod && timePeriod <= 11) {
            return (timePeriod) + "am";
        } else if (timePeriod == 12) {
            return (timePeriod) + "pm";
        } else if (13 <= timePeriod && timePeriod <= 19) {
            return (timePeriod - 12) + "pm";
        } else {
            // time is outside 8am-8pm
            return "";
        }
    }

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

    @Override
    public String toString() {
        return moduleCode;
    }
}
