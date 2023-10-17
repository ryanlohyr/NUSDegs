package seedu.duke.controllers;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ModuleList;
import seedu.duke.models.Major;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String[] userInput = {"pace"};
        int creditsLeft = 60;
        controller.computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals(String.format("You currently have %s MCs till graduation",creditsLeft), printedOutput);
    }

    @Test
    void computePaceInvalidArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"pace","y2s1"};
        int creditsLeft = 60;
        controller.computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", printedOutput);
    }

    @Test
    void computePaceInvalidSemester() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"pace","y2/s10"};
        int creditsLeft = 60;
        controller.computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"pace","y20/s1"};
        int creditsLeft = 60;
        controller.computePace(userInput,creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }
    @Test
    void computePaceValidYear() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"pace","y2/s1"};
        int creditsLeft = 60;
        controller.computePace(userInput,creditsLeft);
        String test = "hi";
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        String line = "You have 60MCs for 5 semesters. Recommended Pace: 12MCs per sem until graduation";
        // Assert the printed output matches the expected value
        assertEquals(printedOutput, line);
    }

    //success scenario: difference
    @Test
    void listModulesLeftTest_expectArrayList() {

        ModulePlannerController controller = new ModulePlannerController();
        ModuleList actualDifference = new ModuleList("CS2100 CS2101 CS2106 CS2109S CS3230");
        ArrayList<String> list = controller.listModulesLeft();

        //test
        int numberOfModules = actualDifference.getNumberOfModules();

        for (int i = 0; i < numberOfModules; i += 1) {
            assertEquals(actualDifference.getMainModuleList().get(i), list.get(i));
        }
    }

    //success scenario: valid major -> major updated
    @Test
    void updateMajor_validMajor_expectSuccessMessage() {
        ModulePlannerController controller = new ModulePlannerController();
        String major = "cs";
        controller.updateMajor(major);
        String printedOutput = outputStream.toString().trim();
        assertEquals(String.format("Major %s selected!",major.toUpperCase()), printedOutput);
    }

    //failure scenario invalid major -> throw exception
    @Test
    void updateMajor_invalidMajor_expectFailureMessage() {
        ModulePlannerController controller = new ModulePlannerController();
        String major = "abc";
        controller.updateMajor(major);
        String printedOutput = outputStream.toString().trim();
        assertEquals("Please select a major from this list: " + Arrays.toString(Major.values()),
                    printedOutput);

    }
}
