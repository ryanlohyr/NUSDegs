package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteFeatureTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Sebastian");
        student.setFirstMajor("CEG");
        student.setYear("Y2/S1");
        Schedule schedule = new Schedule("CS1010 CS2040C", new int[]{1, 1, 0, 0, 0, 0, 0, 0});
        student.setSchedule(schedule);
        System.setOut(new PrintStream(outputStream));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testDeleteFeature_deleteValidModule_expectModuleDeleted() {
        String userInput = "delete CS2040C";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        // Assert the printed output matches the expected value
        String expectedOutput = "Module Successfully Deleted\n" +
                "Sem 1:   X CS1010       \n" +
                "Sem 2:   \n" +
                "Sem 3:   \n" +
                "Sem 4:   \n" +
                "Sem 5:   \n" +
                "Sem 6:   \n" +
                "Sem 7:   \n" +
                "Sem 8:";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testDeleteFeature_deleteModuleWhichIsPrerequisite_expectErrorMessage() {
        String userInput = "delete CS1010";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        // Assert the printed output matches the expected value
        String expectedOutput = "Unable to delete module. This module is a mandatory prerequisite for CS2040C";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testDeleteFeature_deleteModuleNotInSchedule_expectErrorMessage() {
        String userInput = "delete CS1231";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        // Assert the printed output matches the expected value
        String expectedOutput = "Module does not exist in schedule";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }
}