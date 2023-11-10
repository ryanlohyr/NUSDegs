package seedu.duke;

import seedu.duke.controllers.ModulePlannerController;

import java.io.IOException;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) throws IOException {
        ModulePlannerController controller = new ModulePlannerController();
        controller.start();

    }
}
