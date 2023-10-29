package seedu.duke.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandLineView {
    private int formatLineLength = 0;
    private final int accountForTabs = 15;

    public void displayWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    /**
     * Display a message to the command line view.
     *
     * @param o The object to be displayed.
     */
    public void displayMessage(Object o) {
        System.out.println(o);
    }

    /**
     * Display an empty line to the command line view.
     */
    public void displayMessage() {
        System.out.println();
    }

    /**
     * Print a top line for a formatted subheader.
     */
    private void printTopLine() {
        displayMessage(String.format("+%-" + formatLineLength + "s+", "").replace(' ', '-'));
    }

    /**
     * Print a double top line for a formatted header.
     */
    private void printDoubleTopLine() {
        displayMessage(String.format("+%-" + formatLineLength + "s+", "").replace(' ', '='));
    }

    /**
     * Print a bottom line for a formatted subheader.
     */
    private void printBottomLine() {
        displayMessage(String.format("+%-" + formatLineLength + "s+", "").replace(' ', '-'));
    }

    /**
     * Print a double bottom line for a formatted header.
     */
    private void printDoubleBottomLine() {
        displayMessage(String.format("+%-" + formatLineLength + "s+", "").replace(' ', '='));
    }

    /**
     * Return a justified string with a specified length.
     *
     * @param name       The name part of the string.
     * @param description The description part of the string.
     * @param length     The total length of the formatted string.
     * @return The justified string.
     */
    private String returnJustified(String name, String description, int length) {
        return String.format("%-" + length + "s\t", name) + description;
    }

    /**
     * Print the contents of a text file about major requirements with structured formatting.
     *
     * @param filePath The path to the text file to be printed.
     * @throws FileNotFoundException If the file specified by filePath is not found.
     */
    public void printTXTFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        int longestLineLength = getLongestLineLength(f);
        formatLineLength = longestLineLength + accountForTabs;

        Scanner s = new Scanner(f);
        String moduleName;
        String moduleMCs;

        while (s.hasNext()) {
            String currentLine = s.nextLine();
            if (currentLine.indexOf(" - ") > 0) { //module exist
                moduleName = currentLine.substring(0, currentLine.indexOf(" - "));
                moduleMCs = currentLine.substring(currentLine.indexOf(" - ") + 3);
            } else {
                moduleName = currentLine;
                moduleMCs = "";
            }

            if (currentLine.startsWith("***")) { //subsubheader
                String actualModuleName = moduleName.substring(3);
                displayMessage(" ~~\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength) + "\t ~~");
                displayMessage();
            } else if (currentLine.startsWith("**")) { //subheader has box
                printTopLine();
                String actualModuleName = moduleName.substring(2);
                displayMessage("│\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength) + "\t\t│");
                printBottomLine();
            } else if (currentLine.startsWith("*")) { //header
                printDoubleTopLine();
                String actualModuleName = moduleName.substring(1);
                displayMessage("║\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength) + "\t║");
                printDoubleBottomLine();
            } else if (!currentLine.isEmpty()) {
                displayMessage("\t" + returnJustified(moduleName, moduleMCs, longestLineLength));
            } else {
                displayMessage();
            }

        }
    }

    /**
     * Find the length of the longest line in a text file.
     *
     * @param f The text file to analyze.
     * @return The length of the longest line found.
     * @throws FileNotFoundException If the file specified by f is not found.
     */
    private static int getLongestLineLength(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        int longestLineLength = 0;

        while (s.hasNext()) {
            String currentLine = s.nextLine();
            if (currentLine.indexOf(" - ") > longestLineLength) {
                longestLineLength = currentLine.indexOf(" - ");
            }
        }
        if (longestLineLength % 4 > 0) { // remainder
            longestLineLength = (longestLineLength / 4) * 4;
        }
        return longestLineLength;
    }
}
