package seedu.duke.views;

import seedu.duke.models.schema.Event;
import seedu.duke.models.schema.ModuleWeekly;

import java.util.ArrayList;
import java.util.List;

import static seedu.duke.views.UserGuideView.printTimetableModifyGuide;

public class TimetableView {
    private static final int columnWidth = 11;
    //private static final int singleColumnWidth = 60;

    private static final int dayColumnWidth = 10;
    private static final int eventColumnWidth = 45;
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * Prints the given output without a new line.
     *
     * @param output The string to be printed.
     */
    public static void print(String output) {
        System.out.print(output);
    }

    /**
     * Prints the given output with a new line.
     *
     * @param output The string to be printed.
     */
    public static void println(String output) {
        System.out.println(output);
    }

    /**
     * Prints a horizontal line as a separator in the console.
     */
    public static void printlnHorizontalLine() {
        println("------------------------------------------------------------");
    }

    /**
     * Prints a vertical line as a separator in the console.
     */
    public static void printVerticalLine() {
        System.out.print("| ");
    }

    /**
     * Prints a vertical line followed by a new line.
     */
    public static void printlnVerticalLine() {
        System.out.println("|");
    }

    /**
     * Prints spaces to justify the output to the given width.
     *
     * @param number The width for justification.
     */
    public static void printToJustify(int number) {
        print(String.format("%-" + number + "s", ""));
    }


    /**
     * Prints a string with spaces to justify it to the given width.
     *
     * @param string The string to be printed.
     * @param number The width for justification.
     */
    public static void printToJustify(String string, int number) {
        print(String.format("%-" + number + "s", string));
    }

    /**
     * Prints the timetable for the given list of ModuleWeekly objects.
     *
     * @param currentSemesterModules List of ModuleWeekly objects.
     */
    public static void printTimetable(ArrayList<ModuleWeekly> currentSemesterModules) {
        if (currentSemesterModules.isEmpty()) {
            return;
        }

        // create a List (by days) of EventList (modules, event type, time)
        List<ArrayList<Event>> weeklyTimetableByDay = createDailyEvents(currentSemesterModules);

        if (!eventsExist(weeklyTimetableByDay)) {
            printTimetableModifyGuide("Modules in your current sem have no lessons yet.");
            return;
        }

        // sort EventList by time
        for (ArrayList<Event> currentDayEvents : weeklyTimetableByDay) {
            sortByTime(currentDayEvents);
        }

        // print timetable
        printTimetableHeader();
        for (int day = 0; day < days.length; day++) { //8-9am index 0, 7-8pm index 11
            if (weeklyTimetableByDay.get(day).isEmpty()) {
                continue;
            }

            printlnHorizontalLine();
            printCurrentDayEvents(weeklyTimetableByDay.get(day), day);
        }
        printlnHorizontalLine();
    }

    /**
     * Creates a list of daily events for the given list of ModuleWeekly objects.
     *
     * @param currentSemesterModules List of ModuleWeekly objects.
     * @return A list of daily events.
     */
    public static List<ArrayList<Event>> createDailyEvents(ArrayList<ModuleWeekly> currentSemesterModules) {
        // List with 7 empty ArrayList
        List<ArrayList<Event>> weeklyTimetableByDay = initialiseOneDList();

        // Add events as string for all modules to weeklyTimetableByDay
        for (ModuleWeekly module : currentSemesterModules) {
            for (Event event : module.getWeeklyTimetable()) {
                addToWeeklyTimetableByDay(weeklyTimetableByDay, event);
            }
        }
        return weeklyTimetableByDay;
    }

    /**
     * Sorts an ArrayList of events in ascending order.
     * This method uses the bubble sort algorithm to sort the events.
     *
     * @param currentDayEvents An ArrayList of Event objects to be sorted.
     */
    public static void sortByTime(ArrayList<Event> currentDayEvents) {
        // bubble sort O(n^2)
        for (int index = 0; index < currentDayEvents.size(); index++) {
            int bubbleIndex = index;
            while (bubbleIndex > 0) {
                Event unsortedEvent = currentDayEvents.get(bubbleIndex);
                Event sortedEvent = currentDayEvents.get(bubbleIndex - 1);
                if (unsortedEvent.isEarlierThan(sortedEvent)) {
                    // swap
                    currentDayEvents.set(bubbleIndex, sortedEvent);
                    currentDayEvents.set(bubbleIndex - 1, unsortedEvent);
                    bubbleIndex--;
                } else {
                    break;
                }
            }
        }

    }

    /**
     * Prints the timetable header.
     */
    public static void printTimetableHeader() {
        printlnHorizontalLine();

        printVerticalLine();
        printToJustify("DAY", dayColumnWidth);

        printVerticalLine();
        printToJustify("TIMETABLE", eventColumnWidth);

        printlnVerticalLine();
    }

    /**
     * Prints events for the current day.
     *
     * @param eventList List of events for the current day.
     * @param day      The index of the day.
     */
    public static void printCurrentDayEvents(ArrayList<Event> eventList, int day) {
        // Need to print day for first line
        boolean isFirstLine = true;

        while (!eventList.isEmpty()) {
            Event currentEvent = eventList.get(0);
            printCurrentDayOneEvent(currentEvent, day, isFirstLine);
            eventList.remove(0);
            isFirstLine = false;
        }
    }

    /**
     * Prints one line of events for the current day.
     *
     * @param currentEvent The current event.
     * @param day          The index of the day.
     * @param isFirstLine  Whether it is the first line.
     */
    public static void printCurrentDayOneEvent(Event currentEvent, int day, boolean isFirstLine) {
        String currentEventDetails = currentEvent.toString();
        while (!currentEventDetails.isEmpty()) {

            printVerticalLine();

            // print day
            if (isFirstLine) {
                print(days[day]);
                printToJustify(dayColumnWidth - days[day].length());

                isFirstLine = false;
            } else {
                printToJustify(dayColumnWidth);
            }

            printVerticalLine();

            // if currentEvent is too long
            if (currentEventDetails.length() > eventColumnWidth) {
                String[] words = currentEventDetails.split(" ");
                int eventColumnWidthLeft = eventColumnWidth;
                int currentWordIndex = 0;

                while (eventColumnWidthLeft > words[currentWordIndex].length()) {
                    print(words[currentWordIndex] + " ");
                    eventColumnWidthLeft -= (words[currentWordIndex] + " ").length();
                    currentWordIndex++;
                }
                printToJustify(eventColumnWidthLeft);

                String wordNotPrintedYet = words[currentWordIndex];
                int indexNotPrintedYet = currentEventDetails.indexOf(wordNotPrintedYet);
                currentEventDetails = currentEventDetails.substring(indexNotPrintedYet);
                printlnVerticalLine();
                continue;
            }

            // currentEvent can be printed
            print(currentEventDetails);
            printToJustify(eventColumnWidth - currentEventDetails.length());
            currentEventDetails = "";
            printlnVerticalLine();
        }
    }

    /**
     * Fills and sets an object at a specific index in a list, creating intermediate objects if needed.
     *
     * @param index  Index where the object should be placed.
     * @param object Object to be placed.
     * @param list   List where the object should be placed.
     * @param <T>    Type of the object.
     */
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

    /**
     * Initializes a one-dimensional list for daily events.
     *
     * @return A list of daily events.
     */
    public static List<ArrayList<Event>> initialiseOneDList() {
        List<ArrayList<Event>> parentList = new ArrayList<>();

        for (int j = 0; j < 7; j++) { //7 days
            ArrayList<Event> childList = new ArrayList<Event>();
            fillAndSet(j, childList, parentList);
            parentList.add(childList);
        }

        return parentList;
    }


    /**
     * Adds an event to the daily schedule for a specific day, start time, and duration.
     *
     * @param list  List of daily events.
     * @param event Event to be added.
     */
    public static void addToWeeklyTimetableByDay(List<ArrayList<Event>> list, Event event) {
        ArrayList<Event> childList = list.get(event.getDay());
        childList.add(event);
    }

    /**
     * Checks if there are any events in the weekly schedule for each day.
     *
     * @param weeklyTimetableByDay List of daily events.
     * @return True if events exist for any day, false otherwise.
     */
    public static boolean eventsExist(List<ArrayList<Event>> weeklyTimetableByDay) {
        for (ArrayList<Event> currentDayEvents : weeklyTimetableByDay) {
            if (!currentDayEvents.isEmpty()) {
                // true if at least 1 event exists in weeklyTimetable
                return true;
            }
        }
        return false;
    }
}
