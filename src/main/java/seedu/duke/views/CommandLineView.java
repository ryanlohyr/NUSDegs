package seedu.duke.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineView {
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
        while (s.hasNext()) {
            String currentLine = s.nextLine();
            System.out.println("\t" + currentLine);
        }
    }
}
