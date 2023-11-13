package seedu.duke.utils.exceptions;

/**
 * Custom exception to indicate that a required module is missing.
 * This exception should be thrown when an operation expects a certain module to be present,
 * but the module cannot be found or is missing.
 *
 * @author SebasFok
 */
public class MissingModuleException extends Exception{
    public MissingModuleException(String message) {
        super(message);
    }
}
