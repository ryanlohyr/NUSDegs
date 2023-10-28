package seedu.duke.models.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DataRepositoryTest {
    @Test
    void validRequirementsReturned() {
        ArrayList<String> cegRequirementArray = DataRepository.getRequirements("CEG");
        assert(!cegRequirementArray.isEmpty());
    }
}
