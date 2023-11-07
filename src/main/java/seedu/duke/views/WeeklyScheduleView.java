package seedu.duke.views;

import seedu.duke.models.schema.Event;
import seedu.duke.models.schema.ModuleWeekly;

import java.util.ArrayList;
import java.util.List;

import static seedu.duke.views.UserGuideView.addGuide;

public class WeeklyScheduleView {
    private static final int columnWidth = 11;
    //private static final int singleColumnWidth = 60;

    private static final int dayColumnWidth = 10;
    private static final int eventColumnWidth = 45;
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

    //ideally a function that can be called in Student
    public static void printWeeklySchedule(ArrayList<ModuleWeekly> currentSemesterModules) {
        //List(by days) of TaskList (modules, event type, time)
        List<ArrayList<String>> weeklyScheduleByDay = createDailyEvents(currentSemesterModules);

        if (!eventsExist(weeklyScheduleByDay)) {
            //no event error statement
            println("Weekly Schedule is unavailable because you have not added any lectures/tutorials/labs yet.");
            addGuide("To use your Timetable, ");
            return;
        }
        //printDayHeader();
        for (int day = 0; day < days.length; day++) { //8-9am index 0, 7-8pm index 11
            if (weeklyScheduleByDay.get(day).isEmpty()) {
                continue;
            }

            //printRow(weeklyScheduleByTime.get(timePeriod), timePeriod, timePeriod == 11);
            printlnHorizontalLine();
            printCurrentDayEvents(weeklyScheduleByDay.get(day), day);
        }
        printlnHorizontalLine();
    }

    public static List<ArrayList<String>> createDailyEvents(ArrayList<ModuleWeekly> currentSemesterModules) {
        List<ArrayList<String>> weeklyScheduleByDay = initialiseOneDList();

        for (ModuleWeekly module : currentSemesterModules) {
            for (Event event : module.getWeeklySchedule()) {
                addToWeeklyScheduleByDay(weeklyScheduleByDay, event.getDay(), event.getStartTime() - 8,
                        event.getDuration(), module.getModuleCode(), event.getEventType());
            }
        }
        return weeklyScheduleByDay;
    }


    public static void printCurrentDayEvents(ArrayList<String> taskList, int day) {
        boolean isFirstLine = true;
        while (!taskList.isEmpty()) {
            String currentTask = taskList.get(0);
            printCurrentDayEventsOneLine(currentTask, day, isFirstLine);
            taskList.remove(0);
            isFirstLine = false;
        }
    }

    public static void printCurrentDayEventsOneLine(String currentTask, int day, boolean isFirstLine) {

        while (!currentTask.isEmpty()) {
            //print day
            if (isFirstLine) {
                printVerticalLine();
                print(days[day]);
                printToJustify(dayColumnWidth - days[day].length());
                printVerticalLine();
                isFirstLine = false;
            } else {
                printVerticalLine();
                printToJustify(dayColumnWidth);
                printVerticalLine();
            }


            //if line too long
            if (currentTask.length() > eventColumnWidth) {
                String[] words = currentTask.split(" ");
                int singleColumnWidthLeft = eventColumnWidth;
                int currentWordIndex = 0;

                while (singleColumnWidthLeft > words[currentWordIndex].length()) {
                    print(words[currentWordIndex]);
                    singleColumnWidthLeft -= words[currentWordIndex].length();
                    currentWordIndex++;
                }

                printToJustify(singleColumnWidthLeft);
                String wordNotPrintedYet = words[currentWordIndex];
                int indexNotPrintedYet = currentTask.indexOf(wordNotPrintedYet);
                currentTask = currentTask.substring(indexNotPrintedYet);
                printlnVerticalLine();
                continue;
            }

            //line can be printed
            print(currentTask);
            printToJustify(eventColumnWidth - currentTask.length());
            currentTask = "";
            printlnVerticalLine();

        }
    }

    //ideally a function that can be called in Student
    public static void printOldWeeklySchedule(ArrayList<ModuleWeekly> currentSemesterModules) {
        // 8am to 8pm, Monday to Sunday
        // Convert current semester modules (ArrayList<Module>, ModuleList)
        // to weeklySchedule, "2D array" of ArrayList of Event (List<List<ArrayList<Event>>>, ArrayList<String>[][])
        List<List<ArrayList<String>>> weeklyScheduleByTime = initialiseTwoDList();

        for (ModuleWeekly module : currentSemesterModules) {
            for (Event event : module.getWeeklySchedule()) {
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





/*


        if (firstLine) {
            print(weeklyTask.get(day).get(0)); //print time
            printToJustify(columnWidth - weeklyTask.get(day).get(0).length());

        } else {
            printToJustify(columnWidth);
        }
        printVerticalLine(); //for header


        if (weeklyTask.get(day).isEmpty()) {
            //nothing for this day
            printToJustify(singleColumnWidth); //leave line for clarity
            return;
        }

        //task on this day

        String currentTask = weeklyTask.get(day).get(0); //get 1st task
        String[] words = currentTask.split(" ");
        if (currentTask.length() < singleColumnWidth) {
            print(currentTask + " (" + days[day - 1] + ")");
            printToJustify(singleColumnWidth - currentTask.length());
            weeklyTask.get(day).remove(0);
        } else if (words[0].length() > singleColumnWidth) {
            //split super long word
            try {
                String columnWidthLengthWord = words[0].substring(0, singleColumnWidth - 1);
                String remainingWord = words[0].substring(singleColumnWidth);
                print(columnWidthLengthWord);
                weeklyTask.get(day).set(0, remainingWord); //update currentTask
            } catch (IndexOutOfBoundsException e) {
                print(words[0]);
            }
        } else {
            int columnWidthLeft = singleColumnWidth;

            int j = 0;
            while (words[j].length() < columnWidthLeft) {
                print(words[j]);
                //print(String.valueOf(words[j].length())); //troubleshooting
                columnWidthLeft -= words[j].length();
                words[j] = ""; //???
                j += 1;
            }
            //print(String.valueOf(columnWidthLeft)); //troubleshooting

            printToJustify(columnWidthLeft);
            //currentTask should be updated to start from index j
            String startingWord = words[j];
            int startingIndex = currentTask.indexOf(startingWord);
            weeklyTask.get(day).set(0, currentTask.substring(startingIndex)); //update currentTask
        }
        //}
        //}
    }
    }
    */



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
    /*

    public static void printSingleRowSingleColumn(List<ArrayList<String>> weeklyTask, int day, boolean firstLine) {

                    if (firstLine) {
                        print(weeklyTask.get(day).get(0)); //print time
                        printToJustify(columnWidth - weeklyTask.get(day).get(0).length());

                    } else {
                        printToJustify(columnWidth);
                    }
        printVerticalLine(); //for header


                if (weeklyTask.get(day).isEmpty()) {
                    //nothing for this day
                    printToJustify(singleColumnWidth); //leave line for clarity
                    return;
                }

                    //task on this day

                    String currentTask = weeklyTask.get(day).get(0); //get 1st task
                    String[] words = currentTask.split(" ");
                    if (currentTask.length() < singleColumnWidth) {
                        print(currentTask + " (" + days[day - 1] + ")");
                        printToJustify(singleColumnWidth - currentTask.length());
                        weeklyTask.get(day).remove(0);
                    } else if (words[0].length() > singleColumnWidth) {
                        //split super long word
                        try {
                            String columnWidthLengthWord = words[0].substring(0, singleColumnWidth - 1);
                            String remainingWord = words[0].substring(singleColumnWidth);
                            print(columnWidthLengthWord);
                            weeklyTask.get(day).set(0, remainingWord); //update currentTask
                        } catch (IndexOutOfBoundsException e) {
                            print(words[0]);
                        }
                    } else {
                        int columnWidthLeft = singleColumnWidth;

                        int j = 0;
                        while (words[j].length() < columnWidthLeft) {
                            print(words[j]);
                            //print(String.valueOf(words[j].length())); //troubleshooting
                            columnWidthLeft -= words[j].length();
                            words[j] = ""; //???
                            j += 1;
                        }
                        //print(String.valueOf(columnWidthLeft)); //troubleshooting

                        printToJustify(columnWidthLeft);
                        //currentTask should be updated to start from index j
                        String startingWord = words[j];
                        int startingIndex = currentTask.indexOf(startingWord);
                        weeklyTask.get(day).set(0, currentTask.substring(startingIndex)); //update currentTask
                    }
                //}
            //}
        }

    }

    public static void printSingleTableRowMultipleDays(List<ArrayList<String>> weeklyTask) {
        boolean tasksPrinted = false;
        while (!tasksPrinted) { //line, tasks

            //print row by row
            for (int i = 0; i <= 7; i++) { //timePeriod + 7 days
                printVerticalLine();
                //for (int t = 0; t < weeklyTask[i].size(); t++) { //print limited char
                //weeklyTask[i] is an ArrayList<String> that contains tasks in that time period
                if (weeklyTask.get(i).isEmpty()) {
                    printToJustify(columnWidth);
                } else {

                    String currentTask = weeklyTask.get(i).get(0); //get 1st task
                    String[] words = currentTask.split(" ");
                    if (currentTask.length() < columnWidth) {
                        print(currentTask);
                        printToJustify(columnWidth - currentTask.length());
                        weeklyTask.get(i).remove(0);
                    } else if (words[0].length() > columnWidth) {
                        //split word
                        try {
                            String columnWidthLengthWord = words[0].substring(0, 10);
                            String remainingWord = words[0].substring(11);
                            print(columnWidthLengthWord);
                            weeklyTask.get(i).set(0, remainingWord); //update currentTask
                        } catch (IndexOutOfBoundsException e) {
                            print(words[0]);
                        }
                    } else {
                        int columnWidthLeft = columnWidth;

                        int j = 0;
                        while (words[j].length() < columnWidthLeft) {
                            print(words[j]);
                            //print(String.valueOf(words[j].length())); //troubleshooting
                            columnWidthLeft -= words[j].length();
                            words[j] = ""; //???
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

    }

    */

    public static String getTime(int timePeriod, int duration) {
        return getTime(timePeriod) + "-" + getTime(timePeriod + duration);
    }

    public static String getTime(int timePeriod) {
        if (0 <= timePeriod && timePeriod <= 3) {
            return (timePeriod + 8) + "am";
        } else if (timePeriod == 4) {
            return (4 + 8) + "pm";
        } else if (5 <= timePeriod && timePeriod <= 11) {
            return (timePeriod - 4) + "pm";
        }
        return "";
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

    public static List<ArrayList<String>> initialiseOneDList() {
        List<ArrayList<String>> parentList = new ArrayList<>();

        for (int j = 0; j < 7; j++) { //7 days
            ArrayList<String> childList = new ArrayList<String>();
            fillAndSet(j, childList, parentList);
            parentList.add(childList);
        }

        return parentList;
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

    public static void addToWeeklyScheduleByDay(List<ArrayList<String>> list, int day, int startTime, int duration,
                                                String moduleCode, String eventType) {

        ArrayList<String> childList = list.get(day);
        childList.add(moduleCode + " " + eventType + " (" + getTime(startTime, duration) + ")");
    }

    public static boolean eventsExist(List<ArrayList<String>> weeklyScheduleByDay) {
        for (ArrayList<String> currentDayEvents : weeklyScheduleByDay) {
            if (!currentDayEvents.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
