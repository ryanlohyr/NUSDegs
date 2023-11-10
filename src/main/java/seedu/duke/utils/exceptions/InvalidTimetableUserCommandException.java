package seedu.duke.utils.exceptions;

public class InvalidTimetableUserCommandException extends Exception {
    public InvalidTimetableUserCommandException(String message) {
        super(message);
        System.out.println("Please enter in the format: [moduleCode] [lessonType] [startTime] [duration] [day]\n " +
                "If you wish to clear lessons for a module, enter: [moduleCode] clear\n If you with " +
                "to exit modify, enter: EXIT ");
    }
}
