package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchFeatureTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Janelle");
        student.setFirstMajor("CEG");
        student.setYear("Y2/S1");
        System.setOut(new PrintStream(outputStream));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testSearchFeature_validKeyword_expectAssertEquals() {
        String userInput = "search Darwin";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "_________________________________________\n" +
                "These are the modules that contain your keyword in the title:\n" +
                "\n" +
                "Title: Junior Seminar: The Darwinian Revolution\n" +
                "Module Code: UTC1102B\n" +
                "_________________________________________";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testSearchFeature_invalidKeyword_expectErrorMessage() {
        String userInput = "search hehehehe siuuuuuuuu";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Oops! Your search results came up empty. Please try " +
                "searching with different keywords.";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testSearchFeature_emptyKeyword_expectErrorMessage() {
        String userInput = "search      ";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Empty input detected. Please enter a valid keyword after the search command.";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }



}
