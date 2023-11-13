package seedu.duke.models.schema;

//@@author janelleenqi
/**
 * Represents a Tutorial that extends the Event class.
 */
public class Tutorial extends Event {
    private static final String EVENT_TYPE = "Tutorial";

    /**
     * Constructs a Tutorial object with the specified day, start time, duration, and module code.
     *
     * @param day        The day of the lab session. Must not be null.
     * @param startTime  The start time of the lab session.
     * @param duration   The duration of the lab session.
     * @param moduleCode The module code associated with the lab session. Must not be null.
     */
    public Tutorial(String day, int startTime, int duration, String moduleCode) {
        super(day, startTime, duration, moduleCode);
    }

    /**
     * Gets the type of the event, which is "Tutorial".
     *
     * @return The event type.
     */
    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    /**
     * Checks if this Tutorial object is equal to another Event object by comparing their common attributes.
     *
     * @param event The event to compare with.
     * @return true if the events are equal, false otherwise.
     */
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

    /**
     * Generates a string representation of this Tutorial, including module code, event type, and time range.
     *
     * @return The string representation of this Tutorial.
     */
    @Override
    public String toString() {
        return super.toString() + " " + getEventType() + " " + getTime(getStartTime(), getDuration());
    }

    /**
     * Generates a string representation of this Tutorial for saving purposes, including module code,
     * event type, start time, duration, and day.
     *
     * @return The string representation of this Tutorial for saving.
     */
    @Override
    public String toSave() {
        return super.toSave() + " " + getEventType() + " " + getStartTime() + " " + getDuration() + " " + getDay();
    }
}
