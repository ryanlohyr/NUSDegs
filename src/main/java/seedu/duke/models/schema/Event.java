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

    public int getDay() {
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
}
