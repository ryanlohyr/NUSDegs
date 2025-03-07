package seedu.duke.views;

//@@author janelleenqi
/**
 * The MajorRequirementsView class provides methods to print the required modules for specific majors.
 */
public class MajorRequirementsView {

    /**
     * Prints the required modules for a specified major.
     *
     * This method takes a major as input and prints the required modules for that major.
     *
     * @param major The major for which to print the required modules.
     */
    public static void printRequiredModules(String major) {
        switch (major) {
        case "CEG":
            printRequiredModulesCEG();
            return;
        case "CS":
            printRequiredModulesCS();
            return;
        default:
            // should not be reached, prints nothing
            displayMessage("");
            return;
        }
    }

    /**
     * Prints the required modules for the Computer Engineering (CEG) major.
     */
    public static void printRequiredModulesCEG() { //60 char
        displayMessage(
            "#==========================================================#\n" +
            "║   Modular Requirements for CEG                    Units  ║\n" +
            "#==========================================================#\n" +
            "+----------------------------------------------------------+\n" +
            "│   Common Curriculum Requirements                  60     │\n" +
            "+----------------------------------------------------------+\n" +
            "    GES1000 (Singapore Studies)                     4\n" +
            "    GEC1000 (Cultures and Connections)              4\n" +
            "    GEN2000 (Communities and Engagement)            4\n" +
            "    ES2631 Critique & Communication of Thinking\n" +
            "    & Design (Critique & Expression)                4\n" +
            "    CS1010 Programming Methodology (Digital \n" +
            "    Literacy)                                       4\n" +
            "    GEA1000 Quantitative Reasoning with Data (Data \n" +
            "    Literacy)                                       4\n" +
            "    DTK1234 Design Thinking (Design Thinking)       4\n" +
            "    EG1311 Design and Make (Maker Space)            4\n" +
            "    IE2141 Systems Thinking and Dynamics (Systems \n" +
            "    Thinking)                                       4\n" +
            "    EE2211 Introduction to Machine Learning \n" +
            "    (Artificial Intelligence)                       4\n" +
            "    CDE2501 Liveable Cities (Sustainable Futures)   4\n" +
            "    CDE2000 (Creating Narratives)                   4\n" +
            "    PF1101 Fundamentals of Project Management \n" +
            "    (Project Management)                            4\n" +
            "    CG4002 Computer Engineering Capstone Project 1 \n" +
            "    (Integrated Project)                            8\n" +
            "\n" +
            "+----------------------------------------------------------+\n" +
            "│   Programme Requirements                          60     │\n" +
            "+----------------------------------------------------------+\n" +
            " ~~ Engineering Core                                20  ~~\n" +
            "\n" +
            "    MA1511 Engineering Calculus                     2\n" +
            "    MA1512 Differential Equations for Engineering   2\n" +
            "    MA1508E Linear Algebra for Engineering          4\n" +
            "    EG2401A Engineering Professionalism             2\n" +
            "    CP3880 Advanced Technology Attachment Programme 12\n" +
            "\n" +
            " ~~ CEG Major                                       40  ~~\n" +
            "\n" +
            "    CG1111A Engineering Principles and Practice I   4\n" +
            "    CG2111A Engineering Principles and Practice II  4\n" +
            "    CS1231 Discrete Structures                      4\n" +
            "    CG2023 Signals & Systems                        4\n" +
            "    CG2027 Transistor-level Digital Circuit         2\n" +
            "    CG2028 Computer Organization                    2\n" +
            "    CG2271 Real-time Operating System               4\n" +
            "    CS2040C Data Structures and Algorithms          4\n" +
            "    CS2113 Software Engineering & Object-Oriented \n" +
            "    Programming                                     4\n" +
            "    EE2026 Digital Design                           4\n" +
            "    EE4204 Computer Networks                        4\n" +
            "\n" +
            "+----------------------------------------------------------+\n" +
            "│   Unrestricted Electives                          40     │\n" +
            "+----------------------------------------------------------+\n");
    }

    /**
     * Prints the required modules for the Computer Science (CS) major.
     */
    public static void printRequiredModulesCS() {
        displayMessage(
            "#==========================================================#\n" +
            "║   Modular Requirements for CS                     Units  ║\n" +
            "#==========================================================#\n" +
            "+----------------------------------------------------------+\n" +
            "│   Common Curriculum Requirements                  40     │\n" +
            "+----------------------------------------------------------+\n" +
            " ~~ University Requirements: 6 University Pillars   24  ~~\n" +
            "\n" +
            "    CS1101S Programming Methodology (Digital \n" +
            "    Literacy)                                       4\n" +
            "    ES2660 Communicating in the Information Age \n" +
            "    (Critique and Expression)                       4\n" +
            "    GEC1% (Cultures and Connections)                4\n" +
            "    GEA1000 / BT1101 / ST1131 / DSA1101 (Data \n" +
            "    Literacy)                                       4\n" +
            "    GES1% (Singapore Studies)                       4\n" +
            "    GEN2% (Communities and Engagement)              4\n" +
            "\n" +
            " ~~ Computing Ethics                                4  ~~\n" +
            "\n" +
            "    IS1108 Digital Ethics and Data Privacy          4\n" +
            "\n" +
            " ~~   Inter & Cross-Disciplinary Education          12 ~~\n" +
            "\n" +
            "    Interdisciplinary (ID) Courses (at least 2)\n" +
            "    Cross-disciplinary (CD) Courses (no more than 1)\n" +
            "\n" +
            "+----------------------------------------------------------+\n" +
            "│   Programme Requirements                          80     │\n" +
            "+----------------------------------------------------------+\n" +
            " ~~ Computer Science Foundation                     36  ~~\n" +
            "\n" +
            "    CS1231S Discrete Structures                     4\n" +
            "    CS2030S Programming Methodology II              4\n" +
            "    CS2040S Data Structures and Algorithms          4\n" +
            "    CS2100 Computer Organisation                    4\n" +
            "    CS2101 Effective Communication for Computing \n" +
            "    Professionals                                   4\n" +
            "    CS2103T Software Engineering                    4\n" +
            "    CS2106 Introduction to Operating Systems        4\n" +
            "    CS2109S Introduction to AI and Machine Learning 4\n" +
            "    CS3230 Design and Analysis of Algorithms        4\n" +
            "\n" +
            " ~~ Computer Science Breadth and Depth              32  ~~\n" +
            "\n" +
            "\n" +
            " ~~ Mathematics and Sciences                        12  ~~\n" +
            "\n" +
            "    MA1521 Calculus for Computing                   4\n" +
            "    MA1522 Linear Algebra for Computing             4\n" +
            "    ST2334 Probability and Statistics               4\n" +
            "\n" +
            "+----------------------------------------------------------+\n" +
            "│   Unrestricted Electives                          40     │\n" +
            "+----------------------------------------------------------+\n");
    }

    /**
     * Displays a message to the console.
     *
     * This method prints the specified object to the console.
     *
     * @param o The object to be displayed as a message.
     */
    public static void displayMessage(Object o) {
        System.out.print(o);
    }

}
