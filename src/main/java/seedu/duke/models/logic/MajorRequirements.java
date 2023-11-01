package seedu.duke.models.logic;

import seedu.duke.models.schema.Major;
import seedu.duke.views.CommandLineView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MajorRequirements {
    private CommandLineView view = new CommandLineView();
    private String filePath = null;

    private int formatLineLength = 0;
    private final int accountForTabs = 15;

    public MajorRequirements(Major major) {
        try {
            filePath = DataRepository.getFullRequirements(major);

        } catch (NullPointerException e) {
            view.displayMessage("Cannot get filePath. " + e.getMessage());
        }
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Print a single line for a formatted subheader.
     */
    private void printSingleLine() {
        view.displayMessage(String.format("+%-" + formatLineLength + "s+", "").replace(' ', '-'));
    }

    /**
     * Print a double line for a formatted header.
     */
    private void printDoubleLine() {
        view.displayMessage(String.format("#%-" + formatLineLength + "s#", "").replace(' ', '='));
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
        try {
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
                    view.displayMessage(" ~~\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength)
                            + "\t ~~");
                    view.printNewline();
                } else if (currentLine.startsWith("**")) { //subheader has box
                    printSingleLine();
                    String actualModuleName = moduleName.substring(2);
                    view.displayMessage("│\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength)
                            + "\t\t│");
                    printSingleLine();
                } else if (currentLine.startsWith("*")) { //header
                    printDoubleLine();
                    String actualModuleName = moduleName.substring(1);
                    view.displayMessage("║\t" + returnJustified(actualModuleName, moduleMCs, longestLineLength)
                            + "\t║");
                    printDoubleLine();
                } else if (!currentLine.isEmpty()) {
                    view.displayMessage("\t" + returnJustified(moduleName, moduleMCs, longestLineLength));
                } else {
                    view.printNewline();
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
