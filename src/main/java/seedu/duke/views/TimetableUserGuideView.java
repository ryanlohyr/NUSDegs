package seedu.duke.views;

public class TimetableUserGuideView {

    public static void print(String output) {
        System.out.print(output + " ");
    }
    public static void println(String output) {
        System.out.println(output);
    }

    public static void addGuide() {
        println("Please add a module using this format: add [module code] [semester]");
    }


    public static String getTimetableShowGuide() {
        return "View your timetable using this format: timetable show";
    }

    public static String getTimetableModifyGuide() {
        return "Modify your lectures/tutorials/labs in timetable using this format: timetable modify";
    }

    public static void addOrRecommendGuide(String specificContext, int semester) {
        print(specificContext);
        println("Add modules using this format: add [module code] " + semester);
        println("Alternatively, get the recommended schedule for your major: recommend");
    }

    public static void printTimetableModifyGuide(String specificContext) {
        println(specificContext);
        println("Please modify your lectures/tutorials/labs using this format: timetable modify");
    }

    public static void printTTModifyDetailedLessonGuide(String specificContext) {
        println(specificContext);
        println("To add a lesson to a module: [moduleCode] [lessonType] [startTime] [duration] [day]");
        println(    "lessonType - lecture, tutorial, lab");
        println(    "startTime - integer from 8 to 20 (representing 8am to 8pm)");
        println(    "duration - time in hours");
        println(    "day - eg. monday, tuesday, wednesday");
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
