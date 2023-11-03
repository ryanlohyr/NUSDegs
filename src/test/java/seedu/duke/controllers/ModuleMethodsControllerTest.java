package seedu.duke.controllers;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;

import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.determinePrereq;
import static seedu.duke.views.CommandLineView.displayMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulAddMessage;
import static seedu.duke.views.CommandLineView.showPrereqCEG;

class ModuleMethodsControllerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student;

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y2/S1");
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void computePaceWithoutArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {};
        int creditsCompleted = 60;
        computePace(userInput, creditsCompleted);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals(String.format("You currently have %s MCs till graduation", 160 - 60), printedOutput);
    }

    @Test
    void computePaceInvalidArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", printedOutput);
    }

    @Test
    void computePaceInvalidSemester() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2/s10"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        String[] userInput = {"y20/s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }

    @Test
    void computePaceValidYear() {
        String[] userInput = {"y2/s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        String line = "You have 100MCs for 5 semesters. Recommended Pace: 20MCs per sem until graduation";
        // Assert the printed output matches the expected value
        assertEquals(printedOutput, line);
    }

    @Test
    void determinePrereq_invalidModuleCode() {
        String invalidModuleCode = "cs134.";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Invalid Module Name";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void determinePrereq_validModuleCodeWithNoPreReq() {
        String invalidModuleCode = "GEN2061";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Module GEN2061 has no prerequisites.";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void determinePrereq_validModuleCodeWithPreReq() {
        String invalidModuleCode = "EE2211";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "[CS1010, MA1511, MA1508E]";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void testPrereq_addValidModuleToStudent() throws InvalidObjectException {
        String moduleCode = "EG1311";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Module Successfully Added\n" +
                "Sem 1: EG1311 \n" +
                "Sem 2: \n" +
                "Sem 3: \n" +
                "Sem 4: \n" +
                "Sem 5: \n" +
                "Sem 6: \n" +
                "Sem 7: \n" +
                "Sem 8:";
        assertEquals(printedOutput, expectedOutput);

        assertTrue(doesModuleExist);

    }

    @Test
    void testPrereq_addInValidModuleToStudent() throws InvalidObjectException {
        String moduleCode = "eEG1311";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Invalid Module Name\n" +
                "Please select a valid module";
        assertEquals(printedOutput, expectedOutput);
        assertFalse(doesModuleExist);

    }

    @Test
    void testPrereq_addInvalidModuleToStudent() throws InvalidObjectException {
        String moduleCode = "CS2113";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "This module's prerequisites are [CS2040C]\n" +
                "Unable to add module as prerequisites not satisfied for: CS2113";
        assertFalse(doesModuleExist);
        assertEquals(printedOutput, expectedOutput);

    }


}
