package seedu.duke.controllers;

import seedu.duke.models.schema.ModuleWeekly;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.Timetable;
import seedu.duke.models.schema.TimetableUserCommand;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.utils.exceptions.InvalidModifyArgumentException;
import seedu.duke.views.TimetableView;
import seedu.duke.views.Ui;

import java.util.ArrayList;

import static seedu.duke.utils.TimetableParser.isExitModify;
import static seedu.duke.views.TimetableUserGuideView.printTTModifyDetailedLessonGuide;
import static seedu.duke.views.TimetableUserGuideView.printTTModifySimpleLessonGuide;
import static seedu.duke.views.Ui.displayMessage;

public class TimetableModifyController {

    /**
     * Modifies the timetable for the specified student based on user input.
     *
     * @param student The student object.
     * @throws InvalidModifyArgumentException If an invalid argument is provided.
     */
    public void modifyTimetable(Student student) throws InvalidModifyArgumentException {
        Timetable timetable = student.getTimetable();
        ArrayList <ModuleWeekly> currentSemModulesWeekly = timetable.getCurrentSemesterModulesWeekly();
        //verify accepted timetableuser command
        System.out.println("List of modules in current semester: ");
        if (currentSemModulesWeekly.isEmpty()) {
            System.out.println("There are no modules in your current semester. " +
                    "Please add in modules, or generate using the 'recommend' command.");
            return;
        }
        for (ModuleWeekly moduleWeekly : currentSemModulesWeekly) {
            System.out.println(moduleWeekly.getModuleCode());
        }
        System.out.println();
        printTTModifyDetailedLessonGuide("Entered Timetable Modify Mode");

        Ui ui = new Ui();

        boolean inTimetableModifyMode = true;
        while (inTimetableModifyMode) {
            try {
                String userInput = ui.getUserCommand("Input timetable modify command here: ");

                TimetableUserCommand currentTimetableCommand = new TimetableUserCommand(student,
                        currentSemModulesWeekly, userInput);


                String[] arguments = currentTimetableCommand.getArguments();

                //if exit
                if (isExitModify(arguments)) {
                    inTimetableModifyMode = false;
                    System.out.println("Exited Timetable Modify Mode");
                    continue;
                }

                currentTimetableCommand.processTimetableCommand(currentSemModulesWeekly);
                if (timetable.timetableViewIsAvailable()) {
                    TimetableView.printTimetable(currentSemModulesWeekly);
                } else {
                    printTTModifySimpleLessonGuide("Timetable view is unavailable as modules in your " +
                            "current semester have no lessons yet.");
                }

            } catch (InvalidTimetableUserCommandException e) {
                displayMessage(e.getMessage());
            }
        }
    }
}
