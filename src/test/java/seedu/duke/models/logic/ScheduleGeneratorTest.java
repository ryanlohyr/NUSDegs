package seedu.duke.models.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;

class ScheduleGeneratorTest {
    @Test
    void validRecommendedSchedule() {
        ArrayList<String> cegRequirementArray = generateRecommendedSchedule("CEG");
        assert(!cegRequirementArray.isEmpty());
    }
}