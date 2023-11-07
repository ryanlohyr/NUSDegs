package seedu.duke.utils.exceptions;

/**
 * This class represents a custom exception that is thrown when an invalid module is encountered.
 * An invalid module that has illegal characters.
 * @author ryanlohyr
 */
public class InvalidModuleCodeException extends Exception {
    public InvalidModuleCodeException() {
        super("Invalid Module Name");
    }

}
