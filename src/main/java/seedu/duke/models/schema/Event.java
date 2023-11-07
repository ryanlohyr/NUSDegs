package seedu.duke.models.schema;

import java.util.ArrayList;

public class Event {
    private String day;
    private int startTime;
    private int duration;

    public Event(String day, int startTime, int duration) {
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
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
        switch (day) {
        case "Monday":
            return 0;
        case "Tuesday":
            return 1;
        case "Wednesday":
            return 2;
        case "Thursday":
            return 3;
        case "Friday":
            return 4;
        case "Saturday":
            return 5;
        case "Sunday":
            return 6;
        default:
            return -1;
        }
    }

    public String getEventType() {
        return "";
    }


}
