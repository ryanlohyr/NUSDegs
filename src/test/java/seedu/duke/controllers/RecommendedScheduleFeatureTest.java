package seedu.duke.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.UserCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecommendedScheduleFeatureTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();
    private UserCommand currentUserCommand = new UserCommand();
    private String commandToTest = "recommend";

    @BeforeEach
    public void setUpStreams() {
        this.student = new Student();
        student.setName("Ryan Loh");
        student.setFirstMajor("CEG");
        student.setYear("Y3/S2");
        System.setOut(new PrintStream(outputStream));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testRecommend_generateCEGRecommendedSchedule() throws IOException {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CEG");
        System.out.println(recommendedSchedule);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "[GEA1000, MA1511, MA1512, DTK1234, GESS1000, CS1231, CS1010, GEN2000, EG2501," +
                " EG1311, GEC1000, PF1101, CDE2000, IE2141, CG1111A, EG2401A, ES2631, ST2334, MA1508E, CG2023," +
                " CG2111A, CS2040C, CG2027, EE2026, EE4204, EE2211, CG2271, CS2113, CG2028, CP3880, CG4002]";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_addCEGRecommendedScheduleToStudent() throws IOException {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CEG");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule, true);
        student.getSchedule().printMainModuleList();
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1:   X GESS1000     X DTK1234      " +
                "X MA1512       X MA1511       X GEA1000      \n" +
                "Sem 2:   X EG1311       X EG2501       X GEN2000      X CS1010       X CS1231       \n" +
                "Sem 3:   X CG1111A      X IE2141       X CDE2000      X PF1101       X GEC1000      \n" +
                "Sem 4:   X CG2023       X MA1508E      X ST2334       X ES2631       X EG2401A      \n" +
                "Sem 5:   X EE4204       X EE2026       X CG2027       X CS2040C      X CG2111A      \n" +
                "Sem 6:   X CG2028       X CS2113       X CG2271       X EE2211       \n" +
                "Sem 7:   X CG4002       X CP3880       \n" +
                "Sem 8:";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_generateCSRecommendedSchedule() throws IOException {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CS");
        System.out.println(recommendedSchedule);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "[GEA1000, MA1521, IS1108, MA1522, CS1231S, ES2660, CS2101, " +
                "CS1101S, GESS1000, GEN2000," +
                " GEC1000, ST2334, CS2030, CS2040S, CS2100, CS2103T, CS2109S, CS3230, CS2106, CP3880]";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_addCSRecommendedScheduleToStudent() throws IOException {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CS");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule,true);
        student.getSchedule().printMainModuleList();
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1:   X CS1231S      X MA1522       X IS1108       X MA1521       " +
                "X GEA1000      \n" +
                "Sem 2:   X GEN2000      X GESS1000     X CS1101S      X CS2101       X ES2660       \n" +
                "Sem 3:   X CS2100       X CS2040S      X CS2030       X ST2334       X GEC1000      \n" +
                "Sem 4:   X CS2106       X CS3230       X CS2109S      X CS2103T      \n" +
                "Sem 5:   X CP3880       \n" +
                "Sem 6:   \n" +
                "Sem 7:   \n" +
                "Sem 8:";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }

}
