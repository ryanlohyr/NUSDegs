package seedu.duke.views;

public class UserGuideView {

    public static void println(String output) {
        System.out.println(output);
    }

    public static void addGuide() {
        println("Please add a module using this format: add [module code] [semester]");
    }

    public static void addGuide(String specificContext) {
        println(specificContext + "please add a module using this format: add [module code] [semester]");
    }
}
