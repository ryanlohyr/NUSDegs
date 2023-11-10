package seedu.duke.models.schema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimetableUserCommandTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Janelle");
        student.setYear("Y2/S1");
        student.setFirstMajor("CEG");
        System.setOut(new PrintStream(outputStream));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    void partialTestTimetableModify_badDayInput_expectTimetableErrorMessage() {
        System.setOut(originalOut);
        String addUserInputs = "add cs1010 3";
        currentUserCommand = new UserCommand(addUserInputs);
        if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
            currentUserCommand.processCommand(student);
        }

        System.setOut(new PrintStream(outputStream));
        student.setCurrentSemesterModules();
        student.setCurrentSemesterModulesWeekly();

        assertThrows(InvalidTimetableUserCommandException.class,
                () -> badInput("cs1010 lecture 9 2 Mon"));
    }

    public void badInput(String timetableUserInput) throws InvalidTimetableUserCommandException {
        TimetableUserCommand currentTimetableCommand = new TimetableUserCommand(student,
                student.getTimetable().getCurrentSemesterModulesWeekly(), timetableUserInput);
        currentTimetableCommand.processTimetableCommand(student.getTimetable().getCurrentSemesterModulesWeekly());

    }
}
