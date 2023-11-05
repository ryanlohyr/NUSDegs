package seedu.duke.models.schema;

import java.util.ArrayList;

public class Lab extends Event{

    public Lab(String day, int startTime, int duration) {
        super(day, startTime, duration);
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
        return "Lab";
    }

}
