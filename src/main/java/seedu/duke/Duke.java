package seedu.duke;

import seedu.duke.controllers.MainController;

import java.io.IOException;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) throws IOException {
        MainController controller = new MainController();
        controller.start();

    }
}
