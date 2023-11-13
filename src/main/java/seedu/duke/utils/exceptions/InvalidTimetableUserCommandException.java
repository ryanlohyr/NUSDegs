package seedu.duke.utils.exceptions;

//@@author janelleenqi
/**
 * This exception is thrown to indicate that a timetable user command is invalid.
 * It may occur due to invalid user input for timetable, missing information, etc.
 */
public class InvalidTimetableUserCommandException extends Exception {
    /**
     * Constructs a new `InvalidTimetableUserCommandException` with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the `getMessage()` method)
     */
    public InvalidTimetableUserCommandException(String message) {
        super(message + "\nPlease enter in the format: [moduleCode] [lessonType] [startTime] [duration] [day]\n " +
                "If you wish to clear lessons for a module, enter: [moduleCode] clear\n If you with " +
                "to exit modify, enter: exit ");
    }
}
