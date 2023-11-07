package seedu.duke.exceptions;

public class FailPrereqException extends Exception{
    public FailPrereqException(String moduleCode) {
        super("Prerequisite not met for " + moduleCode);
    }
}
