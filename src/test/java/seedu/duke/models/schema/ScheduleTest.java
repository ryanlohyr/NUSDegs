package seedu.duke.models.schema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.exceptions.MandatoryPrereqException;
import seedu.duke.exceptions.MissingModuleException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScheduleTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Student student = new Student();

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y2/S1");
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    //success scenario: String containing valid module, int containing valid targetSem
    //module satisfies prereqs --> module added
    @Test
    void addModuleTest_correctInputsSatisfyPrereqs_expectModuleAdded() throws Exception {
        Schedule schedule = new Schedule();
        schedule.addModule("CS1010", 1);
        ArrayList<String> testArray= new ArrayList<>();
        testArray.add("CS1010");
        assertEquals(testArray, schedule.getModulesPlanned().getModuleCodes());

    }

    //failure scenario 1: String containing valid module, int containing valid targetSem
    //module does not satisfy prereqs --> throw FailPrereqException
    @Test
    void addModuleTest_correctInputsDoesNotSatisfyPrereqs_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(FailPrereqException.class, () -> schedule.addModule("CS2040C", 1));

    }

    //failure scenario 2: String containing invalid module, int containing valid targetSem
    //--> throws IllegalArgumentException
    @Test
    void addModuleTest_invalidModule_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(IllegalArgumentException.class, () -> schedule.addModule("wrong", 1));
    }

    //failure scenario 3: String containing valid module, int containing invalid targetSem
    //--> throws IllegalArgumentException
    @Test
    void addModuleTest_invalidTargetSem_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(IllegalArgumentException.class, () -> schedule.addModule("CS2040C", 1000));
    }

    //success scenario: String containing valid module, module is in schedule
    //module is not a prerequisite of modules ahead--> module deleted
    @Test
    void deleteModuleTest_correctInputsNotPrereq_expectModuleDeleted() throws Exception {
        Schedule schedule = new Schedule();
        schedule.addModule("CS1010", 1);
        schedule.addModule("CS2040C", 2);
        schedule.deleteModule("CS2040C");
        ArrayList<String> testArray= new ArrayList<>();
        testArray.add("CS1010");
        assertEquals(testArray, schedule.getModulesPlanned().getModuleCodes());

    }

    //failure scenario 1: String containing invalid module
    // --> throw MissingModuleException
    @Test
    void deleteModuleTest_invalidModule_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(MissingModuleException.class, () -> schedule.deleteModule("wrong"));

    }

    //failure scenario 2: String containing valid module, module not in schedule
    //--> throws MissingModuleException
    @Test
    void deleteModuleTest_missingModule_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(MissingModuleException.class, () -> schedule.deleteModule("CS2040C"));
    }

    //failure scenario 3: String containing valid module, module in schedule
    //module is prerequisite of modules ahead--> throws MandatoryPrereqException
    @Test
    void deleteModuleTest_MandatoryPrereq_expectException() {
        Schedule schedule = new Schedule("CS1010 CS2040C", new int[]{1, 1, 0, 0, 0, 0, 0, 0});
        assertThrows(MandatoryPrereqException.class, () -> schedule.deleteModule("CS1010"));
    }

    //success scenario 1: String containing valid module, module is in schedule, int containing valid targetSem
    //shifting earlier does not affect other modules--> module shifted earlier
    @Test
    void shiftModuleTest_correctInputsShiftEarlier_expectModuleShifted() throws Exception {
        Schedule schedule = new Schedule();
        schedule.addModule("CS1010", 3);
        schedule.addModule("MA1511", 2);
        schedule.shiftModule("CS1010", 1);
        ArrayList<String> testArray= new ArrayList<>();
        testArray.add("CS1010");
        testArray.add("MA1511");
        assertEquals(testArray, schedule.getModulesPlanned().getModuleCodes());

    }

    //success scenario 2: String containing valid module, module is in schedule, int containing valid targetSem
    //shifting later does not affect other modules--> module shifted later
    @Test
    void shiftModuleTest_correctInputsShiftLater_expectModuleShifted() throws Exception {
        Schedule schedule = new Schedule();
        schedule.addModule("CS1010", 1);
        schedule.addModule("MA1511", 2);
        schedule.shiftModule("CS1010", 3);
        ArrayList<String> testArray= new ArrayList<>();
        testArray.add("MA1511");
        testArray.add("CS1010");
        assertEquals(testArray, schedule.getModulesPlanned().getModuleCodes());

    }

    //failure scenario 1: String containing valid module, module is in schedule, int containing valid targetSem
    //shifting earlier affects other modules--> throws FailPrereqException
    @Test
    void shiftModuleTest_shiftEarlierAffectsModules_expectException() {
        Schedule schedule = new Schedule("CS1010 CS2040C", new int[]{1, 1, 0, 0, 0, 0, 0, 0});
        assertThrows(FailPrereqException.class, () -> schedule.shiftModule("CS2040C", 1));

    }

    //failure scenario 2: String containing valid module, module is in schedule, int containing valid targetSem
    //shifting later affects other modules--> throws MandatoryPrereqException
    @Test
    void shiftModuleTest_shiftLaterAffectsModules_expectException() {
        Schedule schedule = new Schedule("CS1010 CS2040C", new int[]{1, 1, 0, 0, 0, 0, 0, 0});
        assertThrows(MandatoryPrereqException.class, () -> schedule.shiftModule("CS1010", 2));

    }

    //failure scenario 3: String containing valid module, int containing invalid targetSem
    //--> throws IllegalArgumentException
    @Test
    void shiftModuleTest_invalidTargetSem_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(IllegalArgumentException.class, () -> schedule.shiftModule("CS1231S", 1000));
    }

    //failure scenario 4: String containing invalid module
    // --> throw MissingModuleException
    @Test
    void shiftModuleTest_invalidModule_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(MissingModuleException.class, () -> schedule.shiftModule("wrong", 1));

    }

    //failure scenario 5: String containing valid module, module not in schedule
    //--> throws MissingModuleException
    @Test
    void shiftModuleTest_missingModule_expectException() {
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThrows(MissingModuleException.class, () -> schedule.shiftModule("CS2040C", 1));
    }
}
