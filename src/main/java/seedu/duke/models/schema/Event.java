package seedu.duke.models.schema;

import java.util.ArrayList;

public abstract class Event {
    private int startTime;
    private int duration;

    public Event(int startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public ArrayList<String> getEventByHour (Event event) {
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


}
