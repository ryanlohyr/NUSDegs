package seedu.duke.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandLineView {
    private int formatLineLength = 0;
    private int accountForTabs = 15;

    public void displayWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    public void displayMessage(Object o) {
        System.out.println(o);
    }

    public void displayMessage() {
        System.out.println();
    }

    private void printTopLine() {
        displayMessage(String.format("┌%-" + formatLineLength + "s┐", "").replace(' ', '—'));
    }

    private void printDoubleTopLine() {
        displayMessage(String.format("╔%-" + formatLineLength + "s╗", "").replace(' ', '═'));
    }

    private void printBottomLine() {
        displayMessage(String.format("└%-" + formatLineLength + "s┘", "").replace(' ', '—'));
    }

    private void printDoubleBottomLine() {
        displayMessage(String.format("╚%-" + formatLineLength + "s╝", "").replace(' ', '═'));
    }

    private String returnJustified(String name, String description, int length) {
        return String.format("%-" + length + "s\t", name) + description;
    }

    public void printTXTFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        int longestStringLength = getLongestStringLength(f);
        formatLineLength = longestStringLength + accountForTabs;

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
                displayMessage(" ~~\t" + returnJustified(actualModuleName, moduleMCs, longestStringLength) + "\t ~~");
                displayMessage();
            } else if (currentLine.startsWith("**")) { //subheader has box
                printTopLine();
                String actualModuleName = moduleName.substring(2);
                displayMessage("│\t" + returnJustified(actualModuleName, moduleMCs, longestStringLength) + "\t\t│");
                printBottomLine();
            } else if (currentLine.startsWith("*")) { //header
                printDoubleTopLine();
                String actualModuleName = moduleName.substring(1);
                displayMessage("║\t" + returnJustified(actualModuleName, moduleMCs, longestStringLength) + "\t║");
                printDoubleBottomLine();
            } else if (!currentLine.isEmpty()) {
                displayMessage("\t" + returnJustified(moduleName, moduleMCs, longestStringLength));
            } else {
                displayMessage();
            }

        }
    }

    private static int getLongestStringLength(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        int longestStringLength = 0;

        while (s.hasNext()) {
            String currentLine = s.nextLine();
            if (currentLine.indexOf(" - ") > longestStringLength) {
                longestStringLength = currentLine.indexOf(" - ");
            }
        }
        if (longestStringLength % 4 > 0) { // remainder
            longestStringLength = (longestStringLength / 4) * 4;
        }
        return longestStringLength;
    }
}
