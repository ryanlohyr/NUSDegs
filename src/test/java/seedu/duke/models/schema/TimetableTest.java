package seedu.duke.models.schema;

// need to change this when planner class is created

import seedu.duke.exceptions.InvalidModifyArgumentException;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimetableTest {

    @Test
    void testProcessModifyArgumentsValidInputExpectedTrue() {
        String userInput = "lecture /time 10 /duration 1 /day Monday";
        Student student = new Student();
        int indexOfModule = 0;
        student.currentSemesterModulesWeekly = new ArrayList<ModuleWeekly>();
        student.currentSemesterModulesWeekly.add(new ModuleWeekly("CS1010"));
        if (student.currentSemesterModulesWeekly.isEmpty()) {
            System.out.println("why empty");
        }
        ModuleWeekly test = new ModuleWeekly("CS1010");
        test.addLecture("Monday", 10, 1);
        try {
            student.processModifyArguments(userInput, indexOfModule, student);
            assertTrue(student.currentSemesterModulesWeekly.get(0).equals(test));
        } catch (InvalidModifyArgumentException e) {
            fail("Unexpected InvalidModifyArgumentException");
        }
    }

    @Test
    void testProcessModifyArgumentsInvalidCommand() {
        Student student = new Student();
        int indexOfModule = 0;
        String userInput = "invalidCommand /time 12 /duration 3 /day Tuesday";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            student.processModifyArguments(userInput, indexOfModule, student);
            assertEquals("Not a valid command. Please try again!", outputStream.toString().trim());
        } catch (InvalidModifyArgumentException e) {
            fail("Unexpected InvalidModifyArgumentException");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testProcessModifyArgumentsInvalidArguments() {
        Student student = new Student();
        int indexOfModule = 0;
        String userInput = "lecture /time 12  /day Tuesday";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            assertThrows(InvalidModifyArgumentException.class, () -> {
                student.processModifyArguments(userInput, indexOfModule, student);
            });
        } finally {
            System.setOut(originalOut);
        }
    }

    //more unit tests to be added

}
