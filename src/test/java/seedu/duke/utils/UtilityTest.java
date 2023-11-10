package seedu.duke.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.utils.Utility.isInternetReachable;

class UtilityTest {
    @Test
    void internetFeature_whenInternetPresent() {
        boolean isConnectedToInternet = isInternetReachable();

        assertTrue(isConnectedToInternet);
    }
}
