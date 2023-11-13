package seedu.duke.models.logic;

import org.junit.jupiter.api.Test;
import seedu.duke.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DataRepositoryTest {
    @Test
    void validRequirementsReturned() {
        ArrayList<String> cegRequirementArray = StorageManager.getRequirements("CEG");
        int numberOfRequiredCegMods = 31;
        assertEquals(numberOfRequiredCegMods,cegRequirementArray.size());
    }
}
