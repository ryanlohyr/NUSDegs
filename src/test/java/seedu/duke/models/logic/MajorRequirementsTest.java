package seedu.duke.models.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.controllers.ModulePlannerController;
import seedu.duke.models.schema.Major;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.controllers.ModuleServiceController.getRequiredModules;

class MajorRequirementsTest {

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
    void printRequiredModules_validMajor_expectRequiredModulesShown() {
        getRequiredModules("CEG");
        // Capture the printed output
        String printedOutput = outputStream.toString();

        // Assert the printed output matches the expected value
        assertTrue(printedOutput.startsWith(
                "#===========================================================================================#")
        );
    }
}
