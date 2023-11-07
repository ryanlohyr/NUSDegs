package seedu.duke.models.schema;

import java.util.ArrayList;

public class Lecture extends Event{

    public Lecture(String day, int startTime, int duration, String moduleCode) {
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
        return "Lecture";
    }
}
