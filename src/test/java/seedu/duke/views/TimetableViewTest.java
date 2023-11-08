package seedu.duke.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.ModuleWeekly;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableViewTest {

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

    @Test
    void printTimetableTest_cs1231Lessons_expectTimetable() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        ModuleWeekly testModule = new ModuleWeekly("CS1231");
        testModule.addLecture("Monday", 13, 2);
        testModule.addTutorial("Thursday", 11, 1);
        testModule.addLab("Monday", 9, 2);

        currentSemesterModules.add(testModule);

        TimetableView.printTimetable(currentSemesterModules);
        String printedOutput = outputStream.toString();

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput = "------------------------------------------------------------\n" +
                "| DAY       | TIMETABLE                                    |\n" +
                "------------------------------------------------------------\n" +
                "| Monday    | CS1231 Lecture (1pm-3pm)                     |\n" +
                "|           | CS1231 Lab (9am-11am)                        |\n" +
                "------------------------------------------------------------\n" +
                "| Thursday  | CS1231 Tutorial (11am-12pm)                  |\n" +
                "------------------------------------------------------------\n";

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }

    @Test
    void printTimetableTest_noModuleWeekly_expectNothing() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        TimetableView.printTimetable(currentSemesterModules);
        String printedOutput = outputStream.toString();

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput =
                "";

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }
}
