package seedu.duke.views;

public class TimetableUserGuideView {
    private static final int justifyLength = 12;

    private static void print(String output) {
        System.out.print(output + " ");
    }
    private static void println(String output) {
        System.out.println(output);
    }

    /**
     * Prints spaces to justify the output to the given width.
     *
     * @param number The width for justification.
     */
    private static void printToJustify(int number) {
        print(String.format("%-" + number + "s", ""));
    }

    /**
     * Prints a string with spaces to justify it to the given width.
     *
     * @param string The string to be printed.
     * @param number The width for justification.
     */
    private static void printToJustify(String string, int number) {
        print(String.format("%-" + number + "s", string));
    }


    public static void addGuide() {
        println("Please add a module using this format: add [module code] [semester]");
    }


    private static String getTimetableShowGuide() {
        return "View your timetable using this format: timetable show";
    }

    private static String getTimetableModifyGuide() {
        return "Modify your lectures/tutorials/labs in timetable using this format: timetable modify";
    }

    public static void addOrRecommendGuide(String specificContext, int semester) {
        print(specificContext);
        println("Add modules using this format: add [module code] " + semester);
        println("Alternatively, get the recommended schedule for your major: recommend");
    }

    public static void addRecommendGuide(String specificContext) {
        print(specificContext);
        println("Add modules to your current semester or get the recommended schedule for your major first.");
    }

    public static void printTimetableModifyGuide(String specificContext) {
        println(specificContext);
        println("Enter Timetable Modify Mode to add lessons: timetable modify");
    }

    public static void printTTModifySimpleLessonGuide(String specificContext) {
        println(specificContext);
        println("To add a lesson for a module, enter: [moduleCode] [lessonType] [startTime] [duration] [day]\n" +
                "To clear lessons for a module, enter: [moduleCode] clear\n" +
                "To exit Timetable Modify Mode, enter: EXIT");
    }

    public static void printTTModifyDetailedLessonGuide(String specificContext) {
        println(specificContext);
        println("To add a lesson to a module: [moduleCode] [lessonType] [startTime] [duration] [day]");

        printToJustify(4);
        printToJustify("lessonType", justifyLength);
        println("lecture, tutorial, lab");

        printToJustify(4);
        printToJustify("startTime", justifyLength);
        println("integer from 5 to 23 (representing 5am to 11pm)");

        printToJustify(4);
        printToJustify("duration", justifyLength);
        println("time in hours");

        printToJustify(4);
        printToJustify("day", justifyLength);
        println("eg. monday, tuesday, wednesday");

        println("To clear all lessons for a module: [moduleCode] clear");
        println("To exit timetable modify: exit");
    }


    public static void timetableModifySuccessful() {
        println("Your timetable has been successfully modified!");
        println("Continue to " + getTimetableShowGuide().toLowerCase());
        println("Continue to " + getTimetableModifyGuide().toLowerCase());
    }

    public static void timetableModeIncorrectUserInput() {
        println("Invalid user input in Timetable-Modify-Mode.");
        println("Continue to " + getTimetableShowGuide().toLowerCase());
        println("Continue to " + getTimetableModifyGuide().toLowerCase());
    }
}
