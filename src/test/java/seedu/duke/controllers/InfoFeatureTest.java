package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfoFeatureTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Rohit");
        student.setFirstMajor("CEG");
        student.setYear("Y2/S1");
        System.setOut(new PrintStream(outputStream));

    }
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testInfoFeature_validInput_expectAssertEquals() {
        String userInput = "info description CS2113";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "This course introduces the necessary skills for systematic and " +
                "rigorous development of software\n" +
                " systems. It covers requirements, design, implementation, " +
                "quality assurance, and project management\n" +
                " aspects of small-to-medium size multi-person software projects. The course uses the Object\n" +
                " Oriented Programming paradigm. Students of this course will receive hands-on practice of tools\n" +
                " commonly used in the industry, such as test automation tools, build automation tools, and code\n" +
                " revisioning tools will be covered.";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testInfoFeature_noModuleCode_expectErrorMessage() {
        String userInput = "info description";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Invalid Module Code: Only alphabets and digits are allowed in module codes!";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testInfoFeature_invalidModuleCode_expectErrorMessage() {
        String userInput = "info description CS2111113";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Invalid Module Name";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testInfoFeature_missingDescriptionCommand_expectErrorMessage() {
        String userInput = "info";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Empty input detected. Please enter a valid input after the " +
                "info command. (E.g description, workload, all)\n" + "Invalid argument for command info";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testInfoFeature_invalidSubcommand_expectErrorMessage() {
        String userInput = "info workload info";
        currentUserCommand = new UserCommand(userInput);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        String expectedOutput = "Please enter a valid command after the info command. (E.g description, workload)\n" +
                "Invalid argument for command info";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertEquals(expectedOutput, printedOutput);
    }

}
