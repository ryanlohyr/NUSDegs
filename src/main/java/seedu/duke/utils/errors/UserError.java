package seedu.duke.utils.errors;

public class UserError {
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

    public static void invalidModule(String moduleCode){
        String response = String.format("Sorry, Module %s does not exist",moduleCode);
        System.out.println(response);
    }

    public static void displayInvalidInputCommand(String command){
        String response = String.format("Invalid command %s. Type help to see the available commands",command);
        System.out.println(response);
    }

    public static void displayInvalidMethodCommand(String command){
        String response = String.format("Invalid argument for command %s", command);
        System.out.println(response);
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

    public static void emptyInputforPlannerCommand() {
        System.out.println("Empty input detected. Please enter a valid input after the planner command." +
                " (E.g show, (moduleCode)");
    }
    public static void emptyMajor() {
        System.out.println("Major has not been provided yet.");
    }

    public static void moduleDoesNotExist(String moduleCode) {
        System.out.println(moduleCode + "does not exist in your schedule.");
    }

    public static void invalidModuleCode() {
        System.out.println("Invalid Module Code. Please try again!");
    }

    public static void displayModuleAlreadyCompleted(String moduleCode){
        System.out.printf("%s has already been marked as completed.%n", moduleCode);
    }

}
