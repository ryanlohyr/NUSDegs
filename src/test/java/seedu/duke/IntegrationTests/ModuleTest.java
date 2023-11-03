package seedu.duke.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.logic.Api;
import seedu.duke.models.schema.Student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleTest {

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
    void testPrereq_invalidModuleShouldReturnError() {
        String module = "CS1231";
        String correctModuleInfo = "\"description\":\"This course introduces the necessary skills for systematic " +
                "and rigorous development of software systems. It covers";
        String moduleCode = "CS2113";
        String moduleInfo = null;
        moduleInfo = Objects.requireNonNull(Api.getFullModuleInfo(moduleCode)).toJSONString();
        assertNotNull(moduleInfo, "Module info should not be null");
        assertTrue(moduleInfo.contains(correctModuleInfo), "Module info should contain relevant info");
    }
}
