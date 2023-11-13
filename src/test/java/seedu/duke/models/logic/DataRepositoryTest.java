package seedu.duke.models.logic;

import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DataRepositoryTest {
    @Test
    void validRequirementsReturned() {
        ArrayList<String> cegRequirementArray = Storage.getRequirements("CEG");
        int numberOfRequiredCegMods = 31;
        assertEquals(numberOfRequiredCegMods,cegRequirementArray.size());
    }
}
