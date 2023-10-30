package seedu.duke.views;

import seedu.duke.models.schema.Major;

import java.util.Arrays;

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

    public void handleMajorMessage(String messageKey, Major major) {
        switch (messageKey) {
        case "CURRENT_MAJOR":
            if (major == null) {
                displayMessage("No major selected!");
                break;
            }
            displayMessage("Current major is " + major + ".");
            break;
        case "NEW_MAJOR":
            displayMessage("Major " + major + " selected!");
            break;
        case "INVALID_MAJOR":
            displayMessage("Please select a major from this list: " + Arrays.toString(Major.values()));
            break;
        // Empty default branch as outcome cannot take any other value
        default:
            break;
        }
    }

    public void handleAddMessage(String messageKey) {
        switch (messageKey) {
        case "NO_MAJOR":
            displayMessage("Please select a major.");
            break;
        case "INVALID_FORMAT":
            displayMessage("Please add a module using this format: add [module code] [semester]");
            break;
        case "INVALID_ACADEMIC_YEAR":
            displayMessage("Please enter a valid academic year");
            break;
        case "FAIL_PREREQ":
            displayMessage("Unable to add module as prerequisites are not satisfied");
            break;
        case "ADD_MODULE":
            displayMessage("Module Successfully Added");
            break;
        // Empty default branch as outcome cannot take any other value
        default:
            break;
        }
    }

}
