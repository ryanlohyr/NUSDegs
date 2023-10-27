package seedu.duke.models;

import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    //success scenario: valid major -> major updated
    @Test
    void updateMajorTest_validMajor_expectNewMajorMessage() {
        Student student = new Student();
        String userInput = "major cs";
        String printedOutputCommand = student.updateMajor(userInput);
        assertEquals("newMajor", printedOutputCommand);
    }

    //success scenario: no major -> return current major
    @Test
    void updateMajorTest_noMajor_expectCurrentMajorMessage() {
        Student student = new Student();
        String userInput = "major";
        String printedOutputCommand = student.updateMajor(userInput);
        assertEquals("currentMajor", printedOutputCommand);
    }

    //failure scenario invalid major -> throw exception
    @Test
    void updateMajorTest_invalidMajor_expectFailureMessage() {
        Student student = new Student();
        String userInput = "major abc";
        String printedOutputCommand = student.updateMajor(userInput);
        assertEquals("invalidMajor", printedOutputCommand);
    }
}
