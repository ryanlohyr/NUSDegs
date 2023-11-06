package seedu.duke.models.logic;

import static seedu.duke.views.CommandLineView.displayMessage;


public class MajorRequirements {

    public static void printRequiredModules(String major) {
        switch (major) {
        case "CEG":
            printRequiredModulesCEG();
            return;
        case "CS":
            printRequiredModulesCS();
            return;
        default:
            return;
        }
    }

    public static void printRequiredModulesCEG() {
        displayMessage(
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
            "+-------------------------------------------------------------------------------------------+");
    }



    public static void printRequiredModulesCS() {
        displayMessage(
                "#===================================================================================#\n" +
                "║\tModular Requirements for CS                                         \tUnits\t║\n" +
                "#===================================================================================#\n" +
                "+-----------------------------------------------------------------------------------+\n" +
                "│\tCommon Curriculum Requirements                                      \t40\t\t│\n" +
                "+-----------------------------------------------------------------------------------+\n" +
                " ~~\tUniversity Level Requirements: 6 University Pillars                 \t24\t ~~\n" +
                "\n" +
                "\tCS1101S Programming Methodology (Digital Literacy)                  \t4\n" +
                "\tES2660 Communicating in the Information Age (Critique and Expression)\t4\n" +
                "\tGEC1% (Cultures and Connections)                                    \t4\n" +
                "\tGEA1000 / BT1101 / ST1131 / DSA1101 (Data Literacy)                 \t4\n" +
                "\tGES1% (Singapore Studies)                                           \t4\n" +
                "\tGEN2% (Communities and Engagement)                                  \t4\n" +
                "\n" +
                " ~~\tComputing Ethics                                                    \t4\t ~~\n" +
                "\n" +
                "\tIS1108 Digital Ethics and Data Privacy                              \t\n" +
                "\n" +
                " ~~\tInterdisciplinary & Cross-Disciplinary Education                    \t12\t ~~\n" +
                "\n" +
                "\tInterdisciplinary (ID) Courses (at least 2)                         \t\n" +
                "\tCross-disciplinary (CD) Courses (no more than 1)                    \t\n" +
                "\n" +
                "+-----------------------------------------------------------------------------------+\n" +
                "│\tProgramme Requirements                                              \t80\t\t│\n" +
                "+-----------------------------------------------------------------------------------+\n" +
                " ~~\tComputer Science Foundation                                         \t36\t ~~\n" +
                "\n" +
                "\tCS1231S Discrete Structures                                         \t4\n" +
                "\tCS2030S Programming Methodology II                                  \t4\n" +
                "\tCS2040S Data Structures and Algorithms                              \t4\n" +
                "\tCS2100 Computer Organisation                                        \t4\n" +
                "\tCS2101 Effective Communication for Computing Professionals          \t4\n" +
                "\tCS2103T Software Engineering                                        \t4\n" +
                "\tCS2106 Introduction to Operating Systems                            \t4\n" +
                "\tCS2109S Introduction to AI and Machine Learning                     \t4\n" +
                "\tCS3230 Design and Analysis of Algorithms                            \t4\n" +
                "\n" +
                " ~~\tComputer Science Breadth and Depth                                  \t32\t ~~\n" +
                "\n" +
                "\n" +
                " ~~\tMathematics and Sciences                                            \t12\t ~~\n" +
                "\n" +
                "\tMA1521 Calculus for Computing                                       \t4\n" +
                "\tMA1522 Linear Algebra for Computing                                 \t4\n" +
                "\tST2334 Probability and Statistics                                   \t4\n" +
                "\n" +
                "+-----------------------------------------------------------------------------------+\n" +
                "│\tUnrestricted Electives                                              \t40\t\t│\n" +
                "+-----------------------------------------------------------------------------------+\n");
    }

}
