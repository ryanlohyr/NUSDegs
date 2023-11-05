package seedu.duke.views;

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.ModuleWeekly;

//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeeklyScheduleViewTest {
/*
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

 */

    @Test
    void testPrintWeeklySchedule_success() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        ModuleWeekly testModule = new ModuleWeekly("CS1231");
        testModule.addLecture("Monday", 13, 2);
        testModule.addTutorial("Monday", 11, 1);
        testModule.addLab("Monday", 9, 2);

        currentSemesterModules.add(testModule);


        String expected = "-----------------------------------------------------------------------------------------" +
                "----------\n" +
                "|            |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    " +
                "|Sunday      |\n" +
                "-------------------------------------------------------------------------------------------" +
                "--------\n" +
                "|8-9am       |            |            |            |            |            |            " +
                "|            |\n" +
                "-------------------------------------------------------------------------------------------" +
                "--------\n" +
                "|9-10am      |CS1231 Lab  |            |            |            |            |            " +
                "|            |\n" +
                "-------------------------------------------------------------------------------------------" +
                "--------";


        WeeklyScheduleView.printWeeklySchedule(currentSemesterModules);
        //String printedOutput = outputStream.toString();

        //assertTrue(printedOutput.startsWith(expected));
        assertTrue(false);

    }
}
