package seedu.duke.views;


import seedu.duke.models.schema.Major;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;

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

    public void displayGoodbye(){
        System.out.println("Goodbye.");
    }

    public void displayInvalidInputCommand(){
        System.out.println("Invalid command. Type help to see the available commands");
    }

    public void displayHelp(){
        System.out.println("To be added");
    }


    public void handleMajorMessage(int userInputLength, Major major) {
        assert (userInputLength == 1 || userInputLength == 2);
        if (userInputLength == 1) {
            if (major == null) {
                displayMessage("No major selected!");
                return;
            }
            displayMessage("Current major is " + major + ".");
            return;
        }
        displayMessage("Major " + major + " selected!");
    }

    public void showPrereqCEG(String module) {
        System.out.println("This module's prerequisites are "
                + getModulePrereqBasedOnCourse(module.toUpperCase(),"CEG"));
    }
    public void displaySuccessfulAddMessage() {
        displayMessage("Module Successfully Added");
    }

    public void displaySuccessfulDeleteMessage() {
        displayMessage("Module Successfully Deleted");

    }

    /**
     * Display a message to the command line view.
     *
     * @param o The object to be displayed.
     */
    public void displayMessage(Object o) {
        System.out.println(o);
    }


    public void printNewline(){
        System.out.println();
    }


}
