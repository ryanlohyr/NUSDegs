package seedu.duke.models.schema;

//@@author janelleenqi
/**
 * Represents a Lab that extends the Event class.
 */
public class Lab extends Event{

    private static final String EVENT_TYPE = "Lab";

    /**
     * Constructs a Lab object with the specified day, start time, duration, and module code.
     *
     * @param day        The day of the lab session. Must not be null.
     * @param startTime  The start time of the lab session.
     * @param duration   The duration of the lab session.
     * @param moduleCode The module code associated with the lab session. Must not be null.
     */
    public Lab(String day, int startTime, int duration, String moduleCode) {
        super(day, startTime, duration, moduleCode);
    }

    /**
     * Gets the type of the event, which is "Lab".
     *
     * @return The event type.
     */
    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    /**
     * Checks if this Lab object is equal to another Event object by comparing their common attributes.
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
     * Generates a string representation of this Lab, including module code, event type, and time range.
     *
     * @return The string representation of this Lab.
     */
    @Override
    public String toString() {
        return super.toString() + " " + getEventType() + " " + getTime(getStartTime(), getDuration());
    }

    /**
     * Generates a string representation of this Lab for saving purposes, including module code,
     * event type, start time, duration, and day.
     *
     * @return The string representation of this Lab for saving.
     */
    @Override
    public String toSave() {
        return super.toSave() + " " + getEventType() + " " + getStartTime() + " " + getDuration() + " " + getDay();
    }
}
