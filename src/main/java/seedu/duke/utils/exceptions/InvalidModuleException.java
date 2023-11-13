package seedu.duke.utils.exceptions;

//@@author ryanlohyr
/**
 * This class represents a custom exception that is thrown when an invalid module is encountered.
 * An invalid module that has illegal characters.
 *
 */
public class InvalidModuleException extends Exception{
    public InvalidModuleException() {
        super("Only alphabets and digits are allowed in module codes!");
    }

}
