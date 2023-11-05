package seedu.duke.models.schema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.FailPrereqException;

import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;

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

    @Test
    void addRecommendedScheduleListToScheduleTest_expectRecommendedInSchedule() throws InvalidObjectException, FailPrereqException {
        student.addModuleSchedule("MA1511", 2);

        ArrayList<String> recommendedSchedule = generateRecommendedSchedule(student.getMajor());
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule);
        student.printSchedule();

        String printedOutput = outputStream.toString();
        String expectedOutput = "Sem 1: GESS1000 DTK1234 MA1512 MA1511 GEA1000 \n" +
                "Sem 2: GEC1000 EG1311 EG2501 GEN2000 CS1010 \n" +
                "Sem 3: EG2401A CG1111A IE2141 CDE2000 PF1101 \n" +
                "Sem 4: CG2023 CS1231 MA1508E ST2334 ES2631 \n" +
                "Sem 5: EE4204 EE2026 CG2027 CS2040C CG2111A \n" +
                "Sem 6: CG2028 CS2113 CG2271 EE2211 \n" +
                "Sem 7: CG4002 CP3880 \n" +
                "Sem 8: \n";
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

    }
}
