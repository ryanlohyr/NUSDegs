package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeftFeatureTest {
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
    void testLeftFeature_noModulesCompleted_expectMajorModuleCodes() {
        String userInput = "left";
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
        String expectedOutput = "Modules Left: \n" +
                "1. CG1111A     2. MA1511      3. MA1512      4. CS1010      5. GESS1000    \n" +
                "6. GEC1000     7. GEN2000     8. ES2631      9. GEA1000     10. DTK1234    \n" +
                "11. EG1311     12. IE2141     13. EE2211     14. EG2501     15. CDE2000    \n" +
                "16. PF1101     17. CG4002     18. MA1508E    19. EG2401A    20. CP3880     \n" +
                "21. CG2111A    22. CS1231     23. CG2023     24. CG2027     25. CG2028     \n" +
                "26. CG2271     27. ST2334     28. CS2040C    29. CS2113     30. EE2026     \n" +
                "31. EE4204";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testLeftFeature_completedSomeNoPrereqModules_expectPrintLeft() {
        System.setOut(originalOut);

        String[] completeUserInputs = {"add cs1010 1", "add dtk1234 2", "complete cs1010", "complete"};
        int currentIndex = 0;
        currentUserCommand = new UserCommand(completeUserInputs[currentIndex]);
        while (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);

            currentIndex ++;
            currentUserCommand = new UserCommand(completeUserInputs[currentIndex]);
        }

        System.setOut(new PrintStream(outputStream));

        String userInput = "left";
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
        String expectedOutput = "Modules Left: \n" +
                "1. CG1111A     2. MA1511      3. MA1512      4. GESS1000    5. GEC1000     \n" +
                "6. GEN2000     7. ES2631      8. GEA1000     9. DTK1234     10. EG1311     \n" +
                "11. IE2141     12. EE2211     13. EG2501     14. CDE2000    15. PF1101     \n" +
                "16. CG4002     17. MA1508E    18. EG2401A    19. CP3880     20. CG2111A    \n" +
                "21. CS1231     22. CG2023     23. CG2027     24. CG2028     25. CG2271     \n" +
                "26. ST2334     27. CS2040C    28. CS2113     29. EE2026     30. EE4204";
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }
}
