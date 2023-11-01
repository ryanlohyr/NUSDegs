package seedu.duke.views;


import seedu.duke.models.schema.Major;

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

    public void handleAddMessage(boolean isSuccessful) {
        if (isSuccessful) {
            displayMessage("Module Successfully Added");
        }
    }

    public void handleDeleteMessage(boolean isSuccessful) {
        if (isSuccessful) {
            displayMessage("Module Successfully Deleted");
        }
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
