package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleFeatureTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();
    private String commandToTest = "schedule";

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y3/S2");
        System.setOut(new PrintStream(outputStream));


    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testViewScheduleInvalidInput() {
        String argument = " extra argument";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Invalid argument for command schedule";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testViewScheduleValidInput() {
        currentUserCommand = new UserCommand(commandToTest);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1:   \n" +
                "Sem 2:   \n" +
                "Sem 3:   \n" +
                "Sem 4:   \n" +
                "Sem 5:   \n" +
                "Sem 6:   \n" +
                "Sem 7:   \n" +
                "Sem 8:";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testViewScheduleInValidCharacter() {
        String argument = " ```";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Invalid argument for command schedule";
        assertEquals(expectedOutput, printedOutput);
    }




}
