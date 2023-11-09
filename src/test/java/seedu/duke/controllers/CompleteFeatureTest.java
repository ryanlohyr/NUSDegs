package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleteFeatureTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();
    private String commandToTest = "recommend";

    @BeforeEach
    public void setUpStreams() throws IOException {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y3/S2");
        System.setOut(new PrintStream(outputStream));
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CEG");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule, true);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    //complete a module that is not there
    //complete a module that pre requisite is not satisfied
    //complete a module that pre requisite is satisfied
    //complete a module that pre req is not satisfied, complete the pre req, than complete the module
    @Test
    void testCompleteFeature_completeModuleThatPrerequisiteIsSatisfied() {
        String userInput = "complete CS1010";
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
        String expectedOutput = "Module Successfully Completed";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testCompleteFeature_completeModuleThatPrerequisiteIsNotSatisfied() {
        String userInput = "complete CS2040C";
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
        String expectedOutput = "Prerequisites not completed for CS2040C\n" +
                "This module's prerequisites are [CS1010]";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testCompleteFeature_completeModuleAndMakeSureIsSatisfied() {
        String userInput = "complete CS2040C";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        userInput = "complete CS1010";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        userInput = "complete CS2040C";
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
        String expectedOutput = "Prerequisites not completed for CS2040C\n" +
                "This module's prerequisites are [CS1010]\n" +
                "Module Successfully Completed\n" +
                "Module Successfully Completed";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testCompleteFeature_completeModuleThatPrerequisiteIsNotThere() {
        String userInput = "complete IS1108";
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
        String expectedOutput = "IS1108 is not in Modules Planner. Please add the module to your schedule first!";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }
















}
