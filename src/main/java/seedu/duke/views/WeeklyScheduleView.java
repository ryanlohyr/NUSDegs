package seedu.duke.views;

import seedu.duke.models.schema.Event;
import seedu.duke.models.schema.ModuleWeekly;

import java.util.ArrayList;
import java.util.List;

public class WeeklyScheduleView {
    private static final int columnWidth = 11;
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static void print(String output) {
        System.out.print(output);
    }

    public static void println(String output) {
        System.out.println(output);
    }

    public static void printlnHorizontalLine() {
        println("------------------------------------------------------------");
    }

    public static void printVerticalLine() {
        System.out.print("| ");
    }


    public static void printlnVerticalLine() {
        System.out.println("|");
    }

    public static void printToJustify(int number) {
        print(String.format("%-" + number + "s", ""));
    }

    public static void printToJustify(String string, int number) {
        print(String.format("%-" + number + "s", string));
    }

    //ideally a function that can be called in Student

    public static void printWeeklySchedule(ArrayList<ModuleWeekly> currentSemesterModules) {
        // 8am to 8pm, Monday to Sunday
        // Convert current semester modules (ArrayList<Module>, ModuleList)
        // to weeklySchedule, "2D array" of ArrayList of Event (List<List<ArrayList<Event>>>, ArrayList<String>[][])
        List<List<ArrayList<String>>> weeklyScheduleByTime = initialiseTwoDList();

        for (ModuleWeekly module : currentSemesterModules) {
            for (Event event : module.getWeeklyTimetable()) {
                int day = event.getDay();
                int timePeriod = event.getStartTime() - 8; //8am index 0
                int eventDurationLeft = event.getDuration();
                while (eventDurationLeft > 0) {
                    addToWeeklyScheduleByTime(timePeriod, day,
                            module.getModuleCode() + " " + event.getEventType(), weeklyScheduleByTime);
                    //check if java pass by reference

                    timePeriod += 1;
                    eventDurationLeft -= 1;
                }
            }
        }

        printDayHeader();
        for (int timePeriod = 0; timePeriod < 12; timePeriod++) { //8-9am index 0, 7-8pm index 11
            printRow(weeklyScheduleByTime.get(timePeriod), timePeriod, timePeriod == 11);
        }
    }

    public static void printDayHeader() {
        printlnHorizontalLine();
        printVerticalLine();
        printToJustify(columnWidth);//printblank
        for (int i = 0; i < 7; i++) { //7 days
            printVerticalLine();

            String currentDay = days[i];
            print(currentDay);
            printToJustify(columnWidth - currentDay.length());
        }
        printlnVerticalLine();
    }

    public static void printRow(List<ArrayList<String>> hourSchedule, int timePeriod, boolean lastLine) {
        //header & 7 days

        //save a copy
        //List<String>[] weeklyTask = new List<String>[8];
        List<ArrayList<String>> weeklyTask = new ArrayList<>();
        String header = getTime(timePeriod);
        fillAndSet(0, new ArrayList<String>(List.of(header)), weeklyTask);
        //weeklyTask.set(0, new ArrayList<String>(List.of(header))); //????
        for (int i = 0; i < 7; i++) { //7 days
            ArrayList<String> task = new ArrayList<String>(hourSchedule.get(i));
            fillAndSet(i + 1, task, weeklyTask);
            //weeklyTask.set(i + 1, task);
        }

        boolean tasksPrinted = false;
        printlnHorizontalLine();
        boolean firstLine = true;
        while (!tasksPrinted) { //line, tasks

            for (int i = 0; i <= 7; i++) { //timePeriod + 7 days
                printVerticalLine();
                //for (int t = 0; t < weeklyTask[i].size(); t++) { //print limited char
                //weeklyTask[i] is an ArrayList<String> that contains tasks in that time period
                if (weeklyTask.get(i).isEmpty()) {
                    printToJustify(columnWidth);
                } else {
                    String currentTask = weeklyTask.get(i).get(0); //get 1st task
                    if (currentTask.length() < columnWidth) {
                        print(currentTask);
                        printToJustify(columnWidth - currentTask.length());
                        weeklyTask.get(i).remove(0);
                    } else {
                        String[] words = currentTask.split(" ");
                        int columnWidthLeft = columnWidth;

                        int j = 0;
                        while (words[j].length() < columnWidthLeft) {
                            print(words[j]);
                            //print(String.valueOf(words[j].length())); //troubleshooting
                            columnWidthLeft -= words[j].length();
                            words[j] = "";
                            j += 1;
                        }
                        //print(String.valueOf(columnWidthLeft)); //troubleshooting

                        printToJustify(columnWidthLeft);
                        //currentTask should be updated to start from index j
                        String startingWord = words[j];
                        int startingIndex = currentTask.indexOf(startingWord);
                        weeklyTask.get(i).set(0, currentTask.substring(startingIndex)); //update currentTask
                    }
                }
            }
            printlnVerticalLine();

            boolean thisTaskPrinted = true;
            for (int i = 0; i <= 7; i++) { //timePeriod + 7 days
                if (!weeklyTask.get(i).isEmpty()) {
                    thisTaskPrinted = false; //not finished
                    break;
                }
            }
            tasksPrinted = thisTaskPrinted;
        }
        if (lastLine) {
            printlnHorizontalLine();
        }

    }

    public static String getTime(int timePeriod, int duration) {
        String startTime = getTime(timePeriod);
        String endTime = getTime(timePeriod + duration);

        // time is outside 8am-8pm
        if (startTime.isEmpty() || endTime.isEmpty()) {
            return "";
        }

        return "(" + startTime + "-" + endTime + ")";
    }

    public static String getTime(int timePeriod) {
        if (8 <= timePeriod && timePeriod <= 11) {
            return (timePeriod) + "am";
        } else if (timePeriod == 12) {
            return (timePeriod) + "pm";
        } else if (13 <= timePeriod && timePeriod <= 19) {
            return (timePeriod - 12) + "pm";
        } else {
            // time is outside 8am-8pm
            return "";
        }
    }

    public static <T> void fillAndSet(int index, T object, List<T> list) {
        if (index > (list.size() - 1)) {
            for (int i = list.size(); i < index; i++) {
                list.add(null);
            }
            list.add(object);
        } else {
            list.set(index, object);
        }
    }

    public static List<List<ArrayList<String>>> initialiseTwoDList() {
        List<List<ArrayList<String>>> grandparentList = new ArrayList<>();
        for (int i = 0; i < 12; i++) { //12 time periods
            List<ArrayList<String>> parentList = new ArrayList<>();
            for (int j = 0; j < 7; j++) { //7 days
                ArrayList<String> childList = new ArrayList<String>();
                fillAndSet(j, childList, parentList);
                parentList.add(childList);
            }
            fillAndSet(i, parentList, grandparentList);
            grandparentList.add(parentList);
        }
        return grandparentList;
    }

    public static void addToWeeklyScheduleByTime(int indexParent, int indexChild, String eventName,
                                                 List<List<ArrayList<String>>> listOfList) {
        //"2D" array
        List<ArrayList<String>> parentList = listOfList.get(indexParent);
        ArrayList<String> childList = parentList.get(indexChild);
        childList.add(eventName);
    }
}
