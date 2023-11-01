package seedu.duke;
import org.json.simple.parser.ParseException;
import seedu.duke.controllers.ModulePlannerController;

import java.io.IOException;
import java.net.URISyntaxException;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    //main should have basically no code except start
    public static void main(String[] args) {
        ModulePlannerController controller = new ModulePlannerController();
        controller.start();
    }
}
