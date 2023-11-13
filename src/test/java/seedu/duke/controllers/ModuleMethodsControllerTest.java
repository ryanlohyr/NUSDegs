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
import static seedu.duke.views.Ui.displayMessage;
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
    void completeModule_prereqNotCompleted() {
        ModuleMethodsController.executeAddModuleCommand("CS1010",1,student);
        ModuleMethodsController.executeAddModuleCommand("CS2040C",2,student);
        ModuleMethodsController.executeAddModuleCommand("CS2113T",3,student);
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
        ModuleMethodsController.executeAddModuleCommand("CS1010",1,student);
        ModuleMethodsController.executeAddModuleCommand("CS2040C",2,student);
        ModuleMethodsController.executeAddModuleCommand("CS2113T",3,student);
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
        String expectedOutput = "Required Modules Left: \n" +
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
    void testPrereq_addValidModuleToStudent() throws InvalidObjectException {

        String moduleCode = "EG1311";
        int targetSem = 1;
        boolean doesModuleExist = false;
        try {
            student.addModuleToSchedule(moduleCode, targetSem);
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
            student.addModuleToSchedule(moduleCode, targetSem);
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
            student.addModuleToSchedule(moduleCode, targetSem);
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
    void addModuleTest() {
    }

    @Test
    void deleteModuleTest() {
    }

    @Test
    void getRequiredModulesForStudentTest() {
    }
}
