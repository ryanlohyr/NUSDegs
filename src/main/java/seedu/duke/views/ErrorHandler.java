package seedu.duke.views;

public class ErrorHandler {
    public static void invalidInput(){
        System.out.println("Invalid Input provided");
    }

    public static void invalidMajorFormat() {
        System.out.println("Please select a major using this format: major [major]");
        System.out.println("To check your current major, type: major");
    }
    public static void invalidMajor(String availableMajors) {
        System.out.println("Please select a major from this list: " + availableMajors);
    }
    public static void invalidAddFormat() {
        System.out.println("Please add a module using this format: add [module code] [semester]");
    }

    public static void invalidSemester() {
        System.out.println("Please select an integer from 1 to 8 for semester selection");
    }
    public static void invalidDeleteFormat() {
        System.out.println("Please delete a module using this format: delete [module code]");
    }

    public static void emptyInputforInfoCommand() {
        System.out.println("Empty input detected. Please enter a valid input after the info command." +
                " (E.g description, workload, all)");
    }

    public static void invalidCommandforInfoCommand() {
        System.out.println("Please enter a valid command after the info command. (E.g description, workload, all)");
    }

    public static void emptyKeywordforSearchCommand() {
        System.out.println("Empty input detected. Please enter a valid keyword after the search command.");
    }

    public static void emptyArrayforSearchCommand() {
        System.out.println("Oops! Your search results came up empty. Please try searching with different keywords.");
    }

}
