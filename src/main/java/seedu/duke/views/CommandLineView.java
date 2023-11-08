package seedu.duke.views;


import seedu.duke.models.schema.CommandManager;
import seedu.duke.utils.exceptions.InvalidPrereqException;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;

/**
 * The CommandLineView class provides methods to display messages and user prompts in the command line interface.
 * It also includes a method to show prerequisites for a given module based on the course and major.
 */
public class CommandLineView {

    public static void displayWelcome(){
        /*String logo = "  _____       _                 __  __        _____      _              _       _      \n"
                + " |  __ \\     | |               |  \\/  |      / ____|    | |            | |     | |     \n"
                + " | |  | | ___| |__  _   _  __ _| \\  / |_   _| (___   ___| |__   ___  __| |_   _| | ___ \n"
                + " | |  | |/ _ \\ '_ \\| | | |/ _` | |\\/| | | | |\\___ \\ / __| '_ \\ / _ \\/ _` | | | | |/ _ \\\n"
                + " | |__| |  __/ |_) | |_| | (_| | |  | | |_| |____) | (__| | | |  __/ (_| | |_| | |  __/\n"
                + " |_____/ \\___|_.__/ \\__,_|\\__, |_|  |_|\\__, |_____/ \\___|_| |_|\\___|\\__,_|\\__,_|_|\\___|\n"
                + "                           __/ |        __/ |                                          \n"
                + "                          |___/        |___/                                           ";*/

        String logo = "  _   _ _   _ ____  ____                 \n"
                + " | \\ | | | | / ___||  _ \\  ___  __ _ ___ \n"
                + " |  \\| | | | \\___ \\| | | |/ _ \\/ _` / __|\n"
                + " | |\\  | |_| |___) | |_| |  __/ (_| \\__ \\\n"
                + " |_| \\_|\\___/|____/|____/ \\___|\\__, |___/\n"
                + "                               |___/     ";

        System.out.println("Hey there CS and CEG Students! Welcome to ");
        System.out.println(logo);
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

    /**
     * Displays the prerequisites for a given module based on the course and major.
     *
     * @param module The module for which prerequisites need to be displayed.
     * @param major The major or course associated with the module.
     */
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

    public static void displaySuccessfulShiftMessage() {
        displayMessage("Module Successfully Shifted");
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
