package seedu.duke.models.schema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.FailPrereqException;

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

}
