package seedu.duke;

import seedu.duke.controllers.ModulePlannerController;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        ModulePlannerController controller = new ModulePlannerController();
        controller.start();
    }
}
