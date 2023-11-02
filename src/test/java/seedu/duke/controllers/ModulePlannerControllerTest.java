package seedu.duke.controllers;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import seedu.duke.models.logic.DataRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.controllers.ModuleMethodsController.computePace;

class ModulePlannerControllerTest {
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
    void computePaceWithoutArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {};
        int creditsCompleted = 60;
        computePace(userInput,creditsCompleted);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals(String.format("You currently have %s MCs till graduation",160 - 60), printedOutput);
    }

    @Test
    void computePaceInvalidArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2s1"};
        int creditsLeft = 60;
        computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", printedOutput);
    }
    @Test
    void test() throws FileNotFoundException {
        System.out.println(DataRepository.getRequirements("CEG"));
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", "Needs to be in format of Y2/S1");
    }

    @Test
    void computePaceInvalidSemester() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2/s10"};
        int creditsLeft = 60;
        computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        String[] userInput = {"y20/s1"};
        int creditsLeft = 60;
        computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }
    @Test
    void computePaceValidYear() {
        String[] userInput = {"y2/s1"};
        int creditsLeft = 60;
        computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        String line = "You have 100MCs for 5 semesters. Recommended Pace: 20MCs per sem until graduation";
        // Assert the printed output matches the expected value
        assertEquals(printedOutput, line);
    }
}
