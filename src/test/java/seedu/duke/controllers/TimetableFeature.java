package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.TimetableUserCommand;
import seedu.duke.models.schema.UserCommand;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;

//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.util.Scanner;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.duke.controllers.ModuleMethodsController.completeModule;

public class TimetableFeature {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //private final ByteArrayInputStream inputStream = new ByteArrayInputStream();
    //private final InputStream originalIn = System.in;
    //Scanner in = new Scanner(System.in);

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

    /*
    @Test
    void testTimetableFeature_addDuplicateLessons_expectNoDuplicates() {

        String[] timetableSetUpUserInputs = {"add cs1010 3", "add cs1231 3"};
        int currentIndex = 0;
        currentUserCommand = new UserCommand(timetableSetUpUserInputs[currentIndex]);
        while (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);

            currentIndex ++;
            currentUserCommand = new UserCommand(timetableSetUpUserInputs[currentIndex]);
        }

        currentIndex = 0;
        String[] timetableUserInput = {"timetable modify", "cs1010", "lecture /time 12 /duration 2 /day wednesday",
                "timetable modify", "cs1010", "lecture /time 12 /duration 2 /day wednesday", "add"};
        currentUserCommand = new UserCommand(timetableUserInput[currentIndex]);
        while (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);

            currentIndex ++;
            //System.setIn(new InputStream(inputStream));
            //give input
            currentIndex ++;
            //give input
            currentIndex ++;

            //System.setIn(originalIn);
            currentUserCommand = new UserCommand(timetableSetUpUserInputs[currentIndex]);
        }

        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        // To exclude printedOutput from add features
        String firstPrintedOutput = "";
        int indexTimetableFirstModifyOutput = printedOutput.indexOf("Modules Left: ");
        if (indexTimetableFirstModifyOutput != -1) {
            // Extract the text starting from the found index
            firstPrintedOutput = firstPrintedOutput.substring(indexTimetableFirstModifyOutput);
        }

        // To separate the 2 timetable modify output
        String secondPrintedOutput = "";
        int indexTimetableSecondModifyOutput = firstPrintedOutput.indexOf("Modules Left: ", 1);
        if (indexTimetableSecondModifyOutput != -1) {
            // Extract the text starting from the found index
            firstPrintedOutput = firstPrintedOutput.substring(0, indexTimetableSecondModifyOutput);
            secondPrintedOutput = secondPrintedOutput.substring(indexTimetableSecondModifyOutput);
        }

        // Remove last error message
        int indexTimetableIrrelevantOutput = secondPrintedOutput.indexOf("Invalid");
        if (indexTimetableIrrelevantOutput != -1) {
            // Extract the text starting from the found index
            secondPrintedOutput = secondPrintedOutput.substring(0, indexTimetableIrrelevantOutput);
        }

        assertEquals(firstPrintedOutput, secondPrintedOutput);
    }

    */



    @Test
    void usercommandTest() throws InvalidTimetableUserCommandException {
        String userInput = "gess1000 lecture 13 buffalo tuesday";
            TimetableUserCommand timetableUserCommand = new TimetableUserCommand(student, userInput);
            String[] arguments = timetableUserCommand.getArguments();
            timetableUserCommand.printArguments();
            for (String argument : arguments) {
                System.out.println(argument);
            }
            assertEquals("", Arrays.toString(timetableUserCommand.getArguments()));

    }
}
