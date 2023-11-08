package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PrereqFeatureTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();
    private String commandToTest = "prereq";


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
    void determinePrereq_invalidModuleCode() {
        String argument = " cs134";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Invalid Module Name";
        assertEquals(expectedResponse, printedOutput);
    }

    @Test
    void determinePrereq_emptyArgument() {
        currentUserCommand = new UserCommand(commandToTest);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Invalid argument for command prereq";
        assertEquals(expectedResponse, printedOutput);
    }

    @Test
    void determinePrereq_illegalModuleCode() {
        String argument = " \\cs134```";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Invalid Module Code: Only alphabets and digits are allowed in module codes!";
        assertEquals(expectedResponse, printedOutput);
    }

    @Test
    void determinePrereq_validModuleCodeWithNoPreReq() {
        String argument = " GEN2061";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Module GEN2061 has no prerequisites.";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void determinePrereq_validModuleCodeWithPreReq() {
        String argument = " EE2211";
        currentUserCommand = new UserCommand(commandToTest + argument);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "1. CS1010      2. MA1511      3. MA1508E";
        assertEquals(printedOutput, expectedResponse);
    }


}
