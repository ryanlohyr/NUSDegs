package seedu.duke.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CommandLineView {
    private String topline = "_____________________________________________________________________";
    private String emptyline = "|___________________________________________________________________|";

    public void displayWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void printTXTFile(String filePath) throws FileNotFoundException {
        //ArrayList<Task> tasks = new ArrayList<>();

        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        boolean prevLineEndCell = false;

        while (s.hasNext()) {
            String currentLine = s.nextLine();
            if (!currentLine.isEmpty()) {
                if (currentLine.charAt(0) == '*') { //header
                    if (currentLine.charAt(1) == '*') { //subheader {
                        if (currentLine.charAt(2) == '*') { //subsubheader {
                            //print box
                            if (!prevLineEndCell) {
                                System.out.println(topline);
                            }
                            System.out.println("|\n|\t" + currentLine.substring(3) + "\n" + emptyline);
                            prevLineEndCell = true;
                        } else {
                            //print box
                            if (!prevLineEndCell) {
                                System.out.println(topline);
                            }
                            System.out.println("|\n|\t" + currentLine.substring(2) + "\n" + emptyline);
                            prevLineEndCell = true;
                        }
                    } else {
                        //print as per normal
                        System.out.println("\t" + currentLine.substring(1));
                        prevLineEndCell = false;
                    }
                } else {
                    System.out.println("|\t" + currentLine);
                    prevLineEndCell = false;
                }
            } else {
                if (prevLineEndCell) {
                    System.out.println("|");
                }
                System.out.println(emptyline + "\n");
                prevLineEndCell = false;
            }
        }
    }
}
