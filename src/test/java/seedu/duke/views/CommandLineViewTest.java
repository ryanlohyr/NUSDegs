package seedu.duke.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.controllers.ModulePlannerController;
import seedu.duke.models.schema.Major;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLineViewTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    //success scenario (CEG)
    @Test
    void printTXTFile_validFilePath_expectRequiredModulesShown() throws FileNotFoundException {
        ModulePlannerController controller = new ModulePlannerController();
        controller.getRequiredModules(Major.valueOf("CEG"));
        // Capture the printed output
        String printedOutput = outputStream.toString().trim();

        // Assert the printed output matches the expected value
        assertEquals((
                "#===========================================================================================#\n" +
                "║\tModular Requirements for CEG                                                \tUnits\t║\n" +
                "#===========================================================================================#\n" +
                "+-------------------------------------------------------------------------------------------+\n" +
                "│\tCommon Curriculum Requirements                                              \t60\t\t│\n" +
                "+-------------------------------------------------------------------------------------------+\n" +
                "\tGES1000 (Singapore Studies)                                                 \t4\n" +
                "\tGEC1000 (Cultures and Connections)                                          \t4\n" +
                "\tGEN2000 (Communities and Engagement)                                        \t4\n" +
                "\tES2631 Critique & Communication of Thinking & Design (Critique & Expression)\t4\n" +
                "\tCS1010 Programming Methodology (Digital Literacy)                           \t4\n" +
                "\tGEA1000 Quantitative Reasoning with Data (Data Literacy)                    \t4\n" +
                "\tDTK1234 Design Thinking (Design Thinking)                                   \t4\n" +
                "\tEG1311 Design and Make (Maker Space)                                        \t4\n" +
                "\tIE2141 Systems Thinking and Dynamics (Systems Thinking)                     \t4\n" +
                "\tEE2211 Introduction to Machine Learning (Artificial Intelligence)           \t4\n" +
                "\tCDE2501 Liveable Cities (Sustainable Futures)                               \t4\n" +
                "\tCDE2000 (Creating Narratives)                                               \t4\n" +
                "\tPF1101 Fundamentals of Project Management (Project Management)              \t4\n" +
                "\tCG4002 Computer Engineering Capstone Project 1 (Integrated Project)         \t8\n" +
                "\n" +
                "+-------------------------------------------------------------------------------------------+\n" +
                "│\tProgramme Requirements                                                      \t60\t\t│\n" +
                "+-------------------------------------------------------------------------------------------+\n" +
                " ~~\tEngineering Core                                                            \t20\t ~~\n" +
                "\n" +
                "\tMA1511 Engineering Calculus                                                 \t2\n" +
                "\tMA1512 Differential Equations for Engineering                               \t2\n" +
                "\tMA1508E Linear Algebra for Engineering                                      \t4\n" +
                "\tEG2401A Engineering Professionalism                                         \t2\n" +
                "\tCP3880 Advanced Technology Attachment Programme                             \t12\n" +
                "\n" +
                " ~~\tCEG Major                                                                   \t40\t ~~\n" +
                "\n" +
                "\tCG1111A Engineering Principles and Practice I                               \t4\n" +
                "\tCG2111A Engineering Principles and Practice II                              \t4\n" +
                "\tCS1231 Discrete Structures                                                  \t4\n" +
                "\tCG2023 Signals & Systems                                                    \t4\n" +
                "\tCG2027 Transistor-level Digital Circuit                                     \t2\n" +
                "\tCG2028 Computer Organization                                                \t2\n" +
                "\tCG2271 Real-time Operating System                                           \t4\n" +
                "\tCS2040C Data Structures and Algorithms                                      \t4\n" +
                "\tCS2113 Software Engineering & Object-Oriented Programming                   \t4\n" +
                "\tEE2026 Digital Design                                                       \t4\n" +
                "\tEE4204 Computer Networks                                                    \t4\n" +
                "\n" +
                "+-------------------------------------------------------------------------------------------+\n" +
                "│\tUnrestricted Electives                                                      \t40\t\t│\n" +
                "+-------------------------------------------------------------------------------------------+\n")
                        .trim(), printedOutput);
    }
}
