package seedu.duke.views;


import seedu.duke.models.schema.CommandManager;
import seedu.duke.utils.exceptions.InvalidPrereqException;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;

public class CommandLineView {

    public static void displayWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }

    public static void displayGoodbye(){
        System.out.println("Goodbye.");
    }

    public static void displayReady(){
        System.out.println("Now you're all set to use NUSDegs to kick start your degree planning!");
        displayHelp();
    }

    public static void displayHelp(){
        System.out.println("Type 'help' to see the available commands");
    }


    public static void displayGetMajor(String name){
        System.out.println("Welcome " + name + "! What major are you? (Only two available: CEG or CS)");
    }

    public static void displayGetYear(){
        System.out.println("What Year and Semester are you? Ex: Y1/S2 for year 1 semester 2");
    }

    public static void handleMajorMessage(int userInputLength, String major) {
        assert (userInputLength == 0 || userInputLength == 1);
        if (userInputLength == 0) {
            if (major == null) {
                displayMessage("No major selected!");
                return;
            }
            displayMessage("Current major is " + major + ".");
            return;
        }
        displayMessage("Major " + major + " selected!");
    }

    public static void printListOfCommands(CommandManager commandManager) {
        for (String command : commandManager.printListOfCommands()) {
            displayMessage(command);
        }
    }

    public static void showPrereqCEG(String module) {
        try{
            System.out.println("This module's prerequisites are "
                    + getModulePrereqBasedOnCourse(module.toUpperCase(),"CEG"));
        }catch (InvalidPrereqException e){
            System.out.println(e.getMessage());
        }
    }

    public static void showPrereq(String module,String major){
        try{
            System.out.println("This module's prerequisites are "
                    + getModulePrereqBasedOnCourse(module.toUpperCase(),major));
        }catch (InvalidPrereqException e){
            System.out.println(e.getMessage());
        }
    }
    public static void displaySuccessfulAddMessage() {
        displayMessage("Module Successfully Added");
    }

    public static void displaySuccessfulDeleteMessage() {
        displayMessage("Module Successfully Deleted");

    }

    public static void displaySuccessfulCompleteMessage() {
        displayMessage("Module Successfully Completed");
    }

    public static void displayUnsuccessfulCompleteMessage() {
        displayMessage("Module cannot be completed as its prereqs have not been completed.");
    }


    /**
     * Display a message to the command line view.
     *
     * @param o The object to be displayed.
     */
    public static void displayMessage(Object o) {
        System.out.println(o);
    }

    public static void printNewline(){
        System.out.println();
    }

}
