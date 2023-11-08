package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaceFeatureTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();
    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y1/S2");
        System.setOut(new PrintStream(outputStream));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void computePaceInvalidArgument() {
        String userInput = "pace year2 sem1";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        String expectedOutput = "Needs to be in format of Y2/S1";

        assertEquals(expectedOutput,printedOutput);
    }

    @Test
    void computePaceWithoutArgument() {

        String userInput = "pace";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        String expectedOutput = "You have 160MCs for 6 semesters. Recommended Pace: 27MCs per sem until graduation";

        assertEquals(expectedOutput,printedOutput);
    }


    @Test
    void computePaceInvalidSemester() {
        String userInput = "pace y2/s10";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        String userInput = "pace y0/s1";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }

    @Test
    void computePaceValidYear() {
        String userInput = "pace y3/s2";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        String line = "You have 160MCs for 2 semesters. Recommended Pace: 80MCs per sem until graduation";
        // Assert the printed output matches the expected value
        assertEquals(printedOutput, line);
    }

}
