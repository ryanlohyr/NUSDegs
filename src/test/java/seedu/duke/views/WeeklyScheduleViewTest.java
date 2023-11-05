package seedu.duke.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testPrintWeeklySchedule_success() {
        String expected =
                "--------------------------------------------------------------------------------------------\n" +
                        "|            ";

        //create data lol
        List<List<ArrayList<String>>> weeklySchedule = new ArrayList<>();
        for (int i = 0; i < 12; i++) { //12 time periods

            //8-9am
            List<ArrayList<String>> a = new ArrayList<>();
            //use list for workaround for generic array creation of "ArrayList<String>[] a = new ArrayList<String>[7];"
            for (int j = 0; j < 7; j++) { //7 days
                ArrayList<String> task = new ArrayList<String>(Arrays.asList("cs1231", "ie2141"));
                a.set(j, task);
            }
            weeklySchedule.set(i, a);
        }

        WeeklyScheduleView.printWeeklySchedule(weeklySchedule);
        String printedOutput = outputStream.toString();

        assertEquals(expected, printedOutput);

    }
}