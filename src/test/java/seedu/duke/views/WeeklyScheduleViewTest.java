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

class WeeklyScheduleViewTest {

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
    void testPrintWeeklySchedule_cs1231Lessons_expectWeeklySchedule() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        ModuleWeekly testModule = new ModuleWeekly("CS1231");
        testModule.addLecture("Monday", 13, 2);
        testModule.addTutorial("Monday", 11, 1);
        testModule.addLab("Monday", 9, 2);

        currentSemesterModules.add(testModule);

        WeeklyScheduleView.printWeeklySchedule(currentSemesterModules);
        String printedOutput = outputStream.toString();

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput = "------------------------------------------------------------\n" +
                "| Monday    | CS1231 Lecture (1pm-3pm)                     |\n" +
                "|           | CS1231 Tutorial (11am-12pm)                  |\n" +
                "|           | CS1231 Lab (9am-11am)                        |\n" +
                "------------------------------------------------------------\n";

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }

    @Test
    void testPrintWeeklySchedule_noLessons_expectAddGuidePrompt() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        WeeklyScheduleView.printWeeklySchedule(currentSemesterModules);
        String printedOutput = outputStream.toString();

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput =
                "Weekly Schedule is unavailable because you have not added any lectures/tutorials/labs yet.\n" +
                "To add classes your Timetable, please enter 'timetable modify'\n";

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }
}
