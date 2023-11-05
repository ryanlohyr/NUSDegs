package seedu.duke.models.schema;

import java.util.ArrayList;

public class Tutorial extends Event{

    public Tutorial(int startTime, int duration) {
        super(startTime, duration);
    }

    @Override
    public ArrayList<String> getEventByHour (Event event) {
        ArrayList<String> tutorialByHour = new ArrayList<>();

        int eventDurationLeft = getDuration();
        while (eventDurationLeft > 0) {
            String tutorialTimeData = "T " + getStartTime() + " " + (getStartTime() + 1);
            tutorialByHour.add(tutorialTimeData);
            eventDurationLeft -= 1;
        }

        return tutorialByHour;
    }
}
