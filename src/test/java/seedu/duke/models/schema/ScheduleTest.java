package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {


    //success scenario 1: String containing valid module, int containing valid targetSem
    //module satisfies prereqs --> true
    @Test
    void addModuleTest_correctInputsSatisfyPrereqs_expectTrue() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertTrue(schedule.addModule("CS1010", 1));

    }

    //success scenario 2: String containing valid module, int containing valid targetSem
    //module does not satisfy prereqs --> false
    @Test
    void addModuleTest_correctInputsDoesNotSatisfyPrereqs_expectFalse() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertFalse(schedule.addModule("CS2040C", 2));

    }

    //failure scenario 1: String containing invalid module, int containing valid targetSem
    //--> false
    @Test
    void addModuleTest_invalidModule_expectFalse() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertFalse(schedule.addModule("wrong", 1));
    }

    //failure scenario 2: String containing valid module, int containing invalid targetSem
    //--> false
    @Test
    void addModuleTest_invalidTargetSem_expectFalse() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertFalse(schedule.addModule("CS1010", 100));
    }

}