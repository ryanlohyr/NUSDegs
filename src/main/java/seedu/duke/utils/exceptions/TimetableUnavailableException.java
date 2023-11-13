package seedu.duke.utils.exceptions;

//@@author janelleenqi
/**
 * This exception is thrown to indicate that the timetable view is unavailable.
 */
public class TimetableUnavailableException extends Exception {

    public TimetableUnavailableException(String message) {
        super(message);
    }
}
