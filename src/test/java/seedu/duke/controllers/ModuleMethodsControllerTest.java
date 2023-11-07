package seedu.duke.controllers;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;

import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.determinePrereq;
import static seedu.duke.views.CommandLineView.displayMessage;
import static seedu.duke.controllers.ModuleMethodsController.completeModule;
import static seedu.duke.controllers.ModuleMethodsController.showModulesLeft;
import static seedu.duke.views.CommandLineView.displaySuccessfulAddMessage;
import static seedu.duke.views.CommandLineView.showPrereq;



class ModuleMethodsControllerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Student student = new Student();

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
    void computePaceWithoutArgument() {
        String[] userInput = {};
        int creditsCompleted = 60;
        computePace(userInput, creditsCompleted, student.getYear());
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        String expectedOutput = "You have 100MCs for 2 semesters. Recommended Pace: 50MCs per sem until graduation";

        assertEquals(expectedOutput,printedOutput);
    }

    @Test
    void computePaceInvalidArgument() {
        String[] userInput = {"y2s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft,student.getYear());
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", printedOutput);
    }

    @Test
    void computePaceInvalidSemester() {
        String[] userInput = {"y2/s10"};
        int creditsLeft = 60;
        String studentsCurrYear = "y1/s2";
        computePace(userInput, creditsLeft,studentsCurrYear);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        String[] userInput = {"y20/s1"};
        int creditsLeft = 60;
        String studentsCurrYear = "y1/s2";
        computePace(userInput, creditsLeft,studentsCurrYear);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }

    @Test
    void computePaceValidYear() {
        String[] userInput = {"y2/s1"};
        int creditsLeft = 60;
        String studentsCurrYear = "y1/s2";
        computePace(userInput, creditsLeft,studentsCurrYear);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        String line = "You have 100MCs for 5 semesters. Recommended Pace: 20MCs per sem until graduation";
        // Assert the printed output matches the expected value
        assertEquals(printedOutput, line);
    }

    @Test
    void determinePrereq_invalidModuleCode() {
        String invalidModuleCode = "cs134.";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Invalid Module Code: Only alphabets and digits are allowed in module codes!";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void determinePrereq_validModuleCodeWithNoPreReq() {
        String invalidModuleCode = "GEN2061";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "Module GEN2061 has no prerequisites.";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void determinePrereq_validModuleCodeWithPreReq() {
        String invalidModuleCode = "EE2211";
        String major = "CEG";
        determinePrereq(invalidModuleCode, major);
        String printedOutput = outputStream.toString().trim();
        String expectedResponse = "1. CS1010      2. MA1511      3. MA1508E";
        assertEquals(printedOutput, expectedResponse);
    }

    @Test
    void testPrereq_addValidModuleToStudent() throws InvalidObjectException {

        String moduleCode = "EG1311";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModuleSchedule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.getModulesPlanned().existsByCode(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereq(moduleCode,student.getMajor());
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Module Successfully Added\n" +
                "Sem 1:   X EG1311       \n" +
                "Sem 2:   \n" +
                "Sem 3:   \n" +
                "Sem 4:   \n" +
                "Sem 5:   \n" +
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

        assertTrue(doesModuleExist);
    }

    @Test
    void testPrereq_addInValidModuleToStudent() throws InvalidObjectException {
        String moduleCode = "eEG1311";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModuleSchedule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.getModulesPlanned().existsByCode(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereq(moduleCode,student.getMajor());
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Invalid Module Name\n" +
                "Please select a valid module";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(printedOutput, expectedOutput);
        assertFalse(doesModuleExist);

    }

    @Test
    void testPrereq_addInvalidModuleToStudent() throws InvalidObjectException {
        String moduleCode = "CS2113";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModuleSchedule(moduleCode, targetSem);
            displaySuccessfulAddMessage();
            student.printSchedule();
            Schedule currentSchedule = student.getSchedule();
            doesModuleExist = currentSchedule.getModulesPlanned().existsByCode(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereq(moduleCode,student.getMajor());
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "This module's prerequisites are [CS2040C]\n" +
                "Unable to add module as prerequisites not satisfied for: CS2113";
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        assertFalse(doesModuleExist);
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_generateCEGRecommendedSchedule() {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CEG");
        System.out.println(recommendedSchedule);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "[GEA1000, MA1511, MA1512, DTK1234, GESS1000, CS1231, CS1010, GEN2000, EG2501," +
                " EG1311, GEC1000, PF1101, CDE2000, IE2141, CG1111A, EG2401A, ES2631, ST2334, MA1508E, CG2023," +
                " CG2111A, CS2040C, CG2027, EE2026, EE4204, EE2211, CG2271, CS2113, CG2028, CP3880, CG4002]";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_addCEGRecommendedScheduleToStudent() {
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
    void testRecommend_generateCSRecommendedSchedule() {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CS");
        System.out.println(recommendedSchedule);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "[GEA1000, MA1521, IS1108, MA1522, CS1231S, ES2660," +
                " CS2101, CS1101S, GESS1000, GEN2000," +
                " GEC1000, ST2334, CS2030S, CS2040S, CS2100, CS2103T, CS2109S, CS3230, CS2106, CP3880]";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_addCSRecommendedScheduleToStudent() {
        ArrayList<String> recommendedSchedule = student.getSchedule().generateRecommendedSchedule("CS");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule,true);
        student.getSchedule().printMainModuleList();
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1:   X CS1231S      X MA1522       " +
                "X IS1108       X MA1521       X GEA1000      \n" +
                "Sem 2:   X GEN2000      X GESS1000     X CS1101S      X CS2101       X ES2660       \n" +
                "Sem 3:   X CS2100       X CS2040S      X CS2030S      X ST2334       X GEC1000      \n" +
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

    @Test
    void completeModule_prereqNotCompleted() {
        ModuleMethodsController.addModule("CS1010",1,student);
        ModuleMethodsController.addModule("CS2040C",2,student);
        ModuleMethodsController.addModule("CS2113T",3,student);
        completeModule(student,"CS2113T");
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Prerequisites not completed for CS2113T\n" +
                "This module's prerequisites are [CS2040C]";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        //We need this extra logic as addModule prints text as well
        int index = printedOutput.indexOf("Prerequisites not completed for");
        String targetOutputText = "";
        if (index != -1) {
            // Extract the text starting from the found index
            targetOutputText = printedOutput.substring(index);
        } else {
            targetOutputText = "invalid";
        }

        assertEquals(expectedOutput,targetOutputText);
    }

    @Test
    void completeModule_prereqSatisfied() {
        ModuleMethodsController.addModule("CS1010",1,student);
        ModuleMethodsController.addModule("CS2040C",2,student);
        ModuleMethodsController.addModule("CS2113T",3,student);
        completeModule(student,"CS1010");
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Module Successfully Completed";

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        //We need this extra logic as addModule prints text as well
        int index = printedOutput.indexOf("Module Successfully Completed");
        String printedText = "";
        if (index != -1) {
            // Extract the text starting from the found index
            printedText = printedOutput.substring(index);
        } else {
            printedText = "invalid";
        }

        assertEquals(expectedOutput,printedText);
    }



    @Test
    void showModulesLeftTest_arrayListModules_expectModulesLeft() {
        String expectedOutput = "Modules Left: \n" +
                "1. GEA1000     2. MA1521      3. IS1108      4. MA1522      5. CS1231S     \n" +
                "6. ES2660      7. CS2101      8. CS1101S     9. GESS1000    10. GEN2000";

        showModulesLeft(new ArrayList<String>(List.of("GEA1000", "MA1521", "IS1108", "MA1522", "CS1231S", "ES2660",
                "CS2101", "CS1101S", "GESS1000", "GEN2000")));

        String printedOutput = outputStream.toString().trim();

        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(expectedOutput, printedOutput);
    }


    @Test
    void addModuleTest() {
    }

    @Test
    void recommendScheduleToStudentTest() {
    }

    @Test
    void deleteModuleTest() {
    }

    @Test
    void getRequiredModulesForStudentTest() {
    }
}
