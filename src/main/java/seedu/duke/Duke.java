package seedu.duke;
import org.json.simple.parser.ParseException;
import seedu.duke.controllers.ModulePlannerController;
import seedu.duke.views.UnknownCommandException;

import java.io.IOException;
import java.net.URISyntaxException;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    //main should have basically no code except start
    public static void main(String[] args) {
        ModulePlannerController controller = new ModulePlannerController();
        try {
            controller.start();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (UnknownCommandException e) {
            throw new RuntimeException(e);
        }
    }
}
