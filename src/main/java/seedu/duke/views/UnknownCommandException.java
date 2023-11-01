package seedu.duke.views;

public class UnknownCommandException extends Exception{
    public UnknownCommandException(String command) {
        super("Unknown command: " + command);
    }
}
