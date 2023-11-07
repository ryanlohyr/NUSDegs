package seedu.duke.utils.exceptions;

public class InvalidPrereqException extends Exception{
    public InvalidPrereqException(String moduleCode) {
        super(String.format("Sorry but we could not get the prerequisite for %s as " +
                "NUSMods API provided it in a invalid format :<",moduleCode));
    }

}
