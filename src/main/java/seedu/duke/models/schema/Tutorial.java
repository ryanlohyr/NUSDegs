package seedu.duke.models.schema;

import java.util.ArrayList;

public class Tutorial extends Event {

    public Tutorial(String day, int startTime, int duration, String moduleCode) {
        super(day, startTime, duration, moduleCode);
    }

    @Override
    public ArrayList<String> getByHour () {
        ArrayList<String> tutorialByHour = new ArrayList<>();

        int eventDurationLeft = getDuration();
        while (eventDurationLeft > 0) {
            String tutorialTimeData = "T " + getStartTime() + " " + (getStartTime() + 1);
            tutorialByHour.add(tutorialTimeData);
            eventDurationLeft -= 1;
        }

        return tutorialByHour;
    }


    @Override
    public String getEventType() {
        return "Tutorial";
    }

    @Override
    public boolean equals(Event event) {
        boolean isSameEvent = super.equals(event);

        if (!isSameEvent) {
            return false;
        }

        if (!this.getEventType().equals(event.getEventType())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " " + getEventType() + " " + getTime(getStartTime(), getDuration());
    }
}
