package seedu.duke.controllers;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;

import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.determinePrereq;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;
import static seedu.duke.views.CommandLineView.displayMessage;
import static seedu.duke.views.CommandLineView.displaySuccessfulAddMessage;
import static seedu.duke.views.CommandLineView.showPrereqCEG;

class ModuleMethodsControllerTest {
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
    void computePaceWithoutArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {};
        int creditsCompleted = 60;
        computePace(userInput, creditsCompleted);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals(String.format("You currently have %s MCs till graduation", 160 - 60), printedOutput);
    }

    @Test
    void computePaceInvalidArgument() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Needs to be in format of Y2/S1", printedOutput);
    }

    @Test
    void computePaceInvalidSemester() {
        ModulePlannerController controller = new ModulePlannerController();
        String[] userInput = {"y2/s10"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Semester", printedOutput);
    }

    @Test
    void computePaceInvalidYear() {
        String[] userInput = {"y20/s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();
        // Assert the printed output matches the expected value
        assertEquals("Invalid Year", printedOutput);
    }

    @Test
    void computePaceValidYear() {
        String[] userInput = {"y2/s1"};
        int creditsLeft = 60;
        computePace(userInput, creditsLeft);
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
        String expectedResponse = "Invalid Module Code :Only alphabets and digits are allowed in module codes!";
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
        String expectedResponse = "[CS1010, MA1511, MA1508E]";
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
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
            displayMessage(f.getMessage());
        }
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Module Successfully Added\n" +
                "Sem 1: EG1311 \n" +
                "Sem 2: \n" +
                "Sem 3: \n" +
                "Sem 4: \n" +
                "Sem 5: \n" +
                "Sem 6: \n" +
                "Sem 7: \n" +
                "Sem 8:";
        printedOutput = printedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        expectedOutput = expectedOutput
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");

        assertEquals(printedOutput, expectedOutput);

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
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
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
            doesModuleExist = currentSchedule.exists(moduleCode);
        } catch (InvalidObjectException | IllegalArgumentException e) {
            displayMessage(e.getMessage());
        } catch (FailPrereqException f) {
            showPrereqCEG(moduleCode);
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
        assertEquals(printedOutput, expectedOutput);
    }

    @Test
    void testRecommend_generateCEGRecommendedSchedule() {
        ArrayList<String> recommendedSchedule = generateRecommendedSchedule(student.getMajor());
        System.out.println(recommendedSchedule);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "[GEA1000, MA1511, MA1512, DTK1234, GESS1000, CS1010, GEN2000, EG2501, EG1311"
                + ", GEC1000, PF1101, CDE2000, IE2141, CG1111A, EG2401A, ES2631, ST2334, MA1508E, CS1231, CG2023, "
                + "CG2111A, CS2040C, CG2027, EE2026, EE4204, EE2211, CG2271, CS2113, CG2028, CP3880, CG4002]";
        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    void testRecommend_addCEGRecommendedScheduleToStudent() {
        ArrayList<String> recommendedSchedule = generateRecommendedSchedule("CEG");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule);
        student.getSchedule().printMainModuleList();
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1: GESS1000 DTK1234 MA1512 MA1511 GEA1000 \n" +
                "Sem 2: GEC1000 EG1311 EG2501 GEN2000 CS1010 \n" +
                "Sem 3: EG2401A CG1111A IE2141 CDE2000 PF1101 \n" +
                "Sem 4: CG2023 CS1231 MA1508E ST2334 ES2631 \n" +
                "Sem 5: EE4204 EE2026 CG2027 CS2040C CG2111A \n" +
                "Sem 6: CG2028 CS2113 CG2271 EE2211 \n" +
                "Sem 7: CG4002 CP3880 \n" +
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
    void testRecommend_addCSRecommendedScheduleToStudent() {
        ArrayList<String> recommendedSchedule = generateRecommendedSchedule("CS");
        student.getSchedule().addRecommendedScheduleListToSchedule(recommendedSchedule);
        student.getSchedule().printMainModuleList();
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Sem 1: CS1231S MA1522 IS1108 MA1521 GEA1000 \n" +
                "Sem 2: GEN2000 GESS1000 CS1101S CS2101 ES2660 \n" +
                "Sem 3: CS2100 CS2040S CS2030S ST2334 GEC1000 \n" +
                "Sem 4: CS2103T \n" +
                "Sem 5: CP3880 CS2106 CS3230 CS2109S \n" +
                "Sem 6: \n" +
                "Sem 7: \n" +
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
