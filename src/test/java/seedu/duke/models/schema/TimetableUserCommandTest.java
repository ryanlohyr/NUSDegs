package seedu.duke.models.schema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getArguments() throws InvalidTimetableUserCommandException {
        TimetableUserCommand currentTimetableCommand = new TimetableUserCommand(student, "a     b c d e");
        System.out.println(Arrays.toString(currentTimetableCommand.getArguments()));
        assertEquals("[a, b, c, d, e]", Arrays.toString(currentTimetableCommand.getArguments()));

    }
}
