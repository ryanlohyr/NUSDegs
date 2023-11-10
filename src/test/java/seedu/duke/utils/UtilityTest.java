package seedu.duke.utils;

import org.junit.jupiter.api.Test;
import seedu.duke.controllers.ModuleMethodsController;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.duke.controllers.ModuleMethodsController.completeModule;
import static seedu.duke.utils.Utility.isInternetReachable;

class UtilityTest {
    @Test
    void internetFeature_whenInternetPresent() {
        boolean isConnectedToInternet = isInternetReachable();

        assertTrue(isConnectedToInternet);
    }
}