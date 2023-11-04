package seedu.duke.exceptions;

/**
 * This class represents a custom exception that is thrown when an invalid module is encountered.
 * An invalid module that has illegal characters.
 * @author ryanlohyr
 */
public class InvalidModuleException extends Exception{
    public InvalidModuleException() {
        super("Only alphabets and digits are allowed in module codes!");
    }
}
