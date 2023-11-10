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
    void printTimetableTest_cs1231Lessons_expectSortedTimetable() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();

        ModuleWeekly firstTestModule = new ModuleWeekly("CS1231");
        firstTestModule.addLecture("Wednesday", 12, 2);
        firstTestModule.addTutorial("Thursday", 14, 2);

        ModuleWeekly secondTestModule = new ModuleWeekly("ES2631");
        secondTestModule.addLecture("sunday", 11, 1);
        secondTestModule.addTutorial("friday", 14, 2);

        ModuleWeekly thirdTestModule = new ModuleWeekly("EE2026");
        thirdTestModule.addLecture("thurSDay", 11, 2);
        thirdTestModule.addTutorial("WEDNESday", 17, 1);
        thirdTestModule.addLab("wednESDAY", 9, 3);

        ModuleWeekly fourthTestModule = new ModuleWeekly("CS2113");
        fourthTestModule.addLecture("FRIDAY", 16, 2);
        fourthTestModule.addTutorial("THURSDAY", 17, 1);

        ModuleWeekly overlappingModule = new ModuleWeekly("CFG1002");
        overlappingModule.addTutorial("wednesday", 12, 2);

        currentSemesterModules.add(firstTestModule);
        currentSemesterModules.add(secondTestModule);
        currentSemesterModules.add(thirdTestModule);
        currentSemesterModules.add(fourthTestModule);
        currentSemesterModules.add(overlappingModule);

        TimetableView.printTimetable(currentSemesterModules);

        String printedOutput = outputStream.toString();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput = "------------------------------------------------------------\n" +
                "| DAY       | TIMETABLE                                    |\n" +
                "------------------------------------------------------------\n" +
                "| Wednesday | EE2026 Lab (9am-12pm)                        |\n" +
                "|           | CFG1002 Tutorial (12pm-2pm)                  |\n" +
                "|           | CS1231 Lecture (12pm-2pm)                    |\n" +
                "|           | EE2026 Tutorial (5pm-6pm)                    |\n" +
                "------------------------------------------------------------\n" +
                "| Thursday  | EE2026 Lecture (11am-1pm)                    |\n" +
                "|           | CS1231 Tutorial (2pm-4pm)                    |\n" +
                "|           | CS2113 Tutorial (5pm-6pm)                    |\n" +
                "------------------------------------------------------------\n" +
                "| Friday    | ES2631 Tutorial (2pm-4pm)                    |\n" +
                "|           | CS2113 Lecture (4pm-6pm)                     |\n" +
                "------------------------------------------------------------\n" +
                "| Sunday    | ES2631 Lecture (11am-12pm)                   |\n" +
                "------------------------------------------------------------\n";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }
    /*
    @Test
    void printTimetableTest_noModuleWeekly_expectNothing() {
        ArrayList<ModuleWeekly> currentSemesterModules = new ArrayList<ModuleWeekly>();
        TimetableView.printTimetable(currentSemesterModules);

        String printedOutput = outputStream.toString();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        String expectedOutput = "";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);

        //assertTrue(false);
    }

    */

}
