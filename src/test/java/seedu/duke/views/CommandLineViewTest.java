package seedu.duke.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.controllers.ModulePlannerController;
import seedu.duke.models.schema.Major;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandLineViewTest {
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

    //success scenario (CEG)
    @Test
    void printTXTFile_validFilePath_expectRequiredModulesShown() throws FileNotFoundException {
        ModulePlannerController controller = new ModulePlannerController();
        controller.getRequiredModules(Major.valueOf("CEG"));
        // Capture the printed output
        String printedOutput = outputStream.toString();

        // Assert the printed output matches the expected value
        assertTrue(printedOutput.startsWith(
                "#===========================================================================================#")
        );
    }
}
