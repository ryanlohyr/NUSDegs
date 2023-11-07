package seedu.duke.models.schema;

import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.utils.exceptions.MissingModuleException;
import seedu.duke.utils.Parser;
import seedu.duke.utils.exceptions.InvalidPrereqException;
import seedu.duke.views.WeeklyScheduleView;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.DataRepository.getRequirements;
import static seedu.duke.views.CommandLineView.displaySuccessfulCompleteMessage;

/**
 * The Student class represents a student with a name, major, and module schedule.
 */
public class Student {

    private String name;
    private String major;
    private Schedule schedule;
    private String year;
    private int completedModuleCredits;
    private ArrayList<String> majorModuleCodes;
    private ModuleList currentSemesterModules;
    private ArrayList<ModuleWeekly> currentSemesterModulesWeekly;



    /**
     * Constructs a student with a name, major, and module schedule.
     *
     * @param name     The name of the student.
     * @param major    The major of the student.
     * @param schedule The module schedule of the student.
     */
    public Student(String name, String major, Schedule schedule) {
        this.name = name;
        this.major = major;
        this.schedule = schedule;
        this.year = null;
    }

    /**
     * Constructs a student with a null name, null major, and an empty module schedule.
     */
    public Student() {
        this.name = null;
        this.major = null;
        this.schedule = new Schedule();
        this.year = null;
    }

    /**
     * Sets the class schedule of the student.
     *
     * @param schedule The new module schedule.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Retrieves the module schedule of the student.
     *
     * @return The module schedule of the student.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    public int getCurrentModuleCredits(){
        return completedModuleCredits;
    }

    /**
     * Retrieves the name of the student.
     *
     * @return The name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the major of the student.
     *
     * @return The major of the student.
     * @throws NullPointerException If the major has not been set (i.e., it is `null`).
     */

    public String getMajor(){
        return major;
    }

    /**
     * Sets the first major without the major command
     * @author Isaiah Cerven
     * @param userInput must be validated in parser as CS or CEG
     */
    public void setFirstMajor(String userInput){
        try {
            setMajor(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void addModuleSchedule(String moduleCode, int targetSem) throws InvalidObjectException, FailPrereqException {
        this.schedule.addModule(moduleCode, targetSem);
    }

    /**
     * Completes a module with the specified module code.
     *
     * @param moduleCode The code of the module to be completed.
     */
    public void completeModuleSchedule(String moduleCode) throws InvalidObjectException,
            FailPrereqException, InvalidPrereqException {

        Module module = schedule.getModule(moduleCode);

        ArrayList<String> modulePrereq = getModulePrereqBasedOnCourse(moduleCode,this.getMajor());

        schedule.completeModule(module,modulePrereq);
        this.completedModuleCredits += module.getModuleCredits();

        displaySuccessfulCompleteMessage();

    }
    /**
     * Deletes a module with the specified module code. This method also updates the completed
     * module credits and removes the module from the planned modules list.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module to be deleted.
     * @throws FailPrereqException If deleting the module fails due to prerequisite dependencies.
     */
    public void deleteModuleSchedule(String moduleCode) throws FailPrereqException, MissingModuleException {
        try{
            Module module = schedule.getModule(moduleCode);
            schedule.deleteModule(moduleCode);
            if(module.getCompletionStatus()){
                this.completedModuleCredits -= module.getModuleCredits();
            }
        }catch (InvalidObjectException e) {
            throw new MissingModuleException(e.getMessage());
        }



    }


    //@@author janelleenqi
    public Module getModuleFromSchedule(String moduleCode) throws MissingModuleException {
        try {
            return schedule.getModule(moduleCode);
        } catch (InvalidObjectException e) {
            throw new MissingModuleException(moduleCode + " is not in Modules Planner. " +
                    "Please add the module to your schedule first!");
        }
    }

    public boolean completionStatusModuleSchedule(Module module) {
        return module.getCompletionStatus();
    }

    //@@author
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Sets the name of the student.
     *
     * @param name The new name of the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the major of the student.
     *
     * @param major The new major to set.
     */
    public void setMajor(String major) {
        this.major = major;
        majorModuleCodes = getRequirements(major);
    }

    /**
     * Retrieves the module codes that are left to be completed in the major's curriculum.
     *
     * This method compares the list of major module codes with the list of completed module codes
     * in the current schedule. It returns a list of module codes that are still left to be completed
     * as per the major's curriculum.
     *
     * @return An ArrayList of Strings representing module codes that are left to be completed.
     */
    public ArrayList<String> getModuleCodesLeft () {
        ArrayList<String> moduleCodesLeft = new ArrayList<String>();
        ArrayList<String> completedModuleCodes = schedule.getModulesPlanned().getModulesCompleted();

        for (String moduleCode: majorModuleCodes) {
            if (!completedModuleCodes.contains(moduleCode)) {
                moduleCodesLeft.add(moduleCode);
            }
        }
        return moduleCodesLeft;
    }

    public ArrayList<String> getMajorModuleCodes() {
        return majorModuleCodes;
    }

    public ModuleList getModulesPlanned() {
        return schedule.getModulesPlanned();
    }

    public void printSchedule(){
        this.schedule.printMainModuleList();
    }

    public void setCurrentSemesterModules() {
        try {
            int[] yearAndSem = Parser.parseStudentYear(year);
            System.out.println(Arrays.toString(yearAndSem));
            int currSem = ((yearAndSem[0] - 1) * 2) + yearAndSem[1];
            int[] modulesPerSem = schedule.getModulesPerSem();
            System.out.println(Arrays.toString(modulesPerSem));
            ModuleList modulesPlanned = schedule.getModulesPlanned();
            // schedule.printMainModuleList();
            // System.out.println("-------------------------------");
            // printSchedule();
            int numberOfModulesInCurrSem = modulesPerSem[currSem - 1];
            int numberOfModulesCleared = 0;
            for (int i = 0; i < currSem - 1; i++) {
                numberOfModulesCleared += modulesPerSem[i];
            }
            int startIndex = numberOfModulesCleared;
            int endIndex = startIndex + numberOfModulesInCurrSem;
            ArrayList<Module> modulesInSchedule = modulesPlanned.getMainModuleList();
            currentSemesterModules = new ModuleList();
            for (int i = startIndex; i < endIndex; i++) {
                currentSemesterModules.addModule(modulesInSchedule.get(i));
                //          System.out.println(currentSemesterModules.getModule(i));
                //          System.out.println(modulesInSchedule.get(i).getModuleCode());
            }
            ArrayList<Module> toprint = currentSemesterModules.getMainModuleList();
            for (int i = 0; i < toprint.size(); i++) {
                System.out.println(toprint.get(i).getModuleCode());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("why array out of bounds bruh");
        } catch (NullPointerException e) {
            System.out.print("null ptr exception");
        }
    }

    public void setCurrentSemesterModulesWeekly() {

        if (currentSemesterModules.getMainModuleList().isEmpty()) {
            System.out.println(" array list in modulelist is null");
        }
        ArrayList<Module> currentSemModuleList = currentSemesterModules.getMainModuleList();
        currentSemesterModulesWeekly = new ArrayList<>();
        for (int i = 0; i < currentSemModuleList.size(); i++) {
            //       System.out.println(currentSemesterModules.getModuleByIndex(i));
            //       System.out.println(currentSemModuleList.get(i).getModuleCode());
            String currModuleCode = currentSemModuleList.get(i).getModuleCode();
            ModuleWeekly currModule = new ModuleWeekly(currModuleCode);
            currentSemesterModulesWeekly.add(currModule);
            //       currentSemesterModulesWeekly.add(new ModuleWeekly(currentSemesterModules.getModuleByIndex(i).
            //               getModuleCode()));
        }
        //   for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
        //       System.out.println(currentSemesterModulesWeekly.get(i).getModuleCode());
        //   }
    }

    public void plannerCommand(Student student, String userInput) {
        setCurrentSemesterModules();
        setCurrentSemesterModulesWeekly();
        String argument = userInput.substring(userInput.indexOf("planner") + 7).trim().toUpperCase();
        if (argument.equals("SHOW")) {
            if (checkIfValidTime(student)) {
                WeeklyScheduleView.printWeeklySchedule(currentSemesterModulesWeekly);
            }
        }
    }


    /*
    int startIndexOfTutorialStartTime = userNewInput.indexOf("/tutorial");
                        int startIndexOfLectureStartTime = userNewInput.indexOf("/lecture");
                        int startIndexOfLabStartTime = userNewInput.indexOf("/lab");
                 //       int startIndexOfDuration = userNewInput.indexOf("/duration");
                        int startIndexOfDay = userNewInput.indexOf("/day");
                        String lecture = userNewInput.substring(startIndexOfLectureStartTime + 8,
                                startIndexOfTutorialStartTime);
                 //       String duration = userNewInput.substring(startIndexOfDuration + 9, startIndexOfDay);
                        String day = userNewInput.substring(startIndexOfDay + 4);
                        String tutorial = userNewInput.substring(startIndexOfTutorialStartTime + 9, startIndexO
                        fLabStartTime);
                        String lab = userNewInput.substring(startIndexOfLabStartTime + 6, startIndexOfDay);
                        for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
                            if (!currentSemesterModulesWeekly.get(i).getModuleCode().equals(argument)) {
                                System.out.println("error in exists by code");
                                continue;
                            }
                            currentSemesterModulesWeekly.get(i).setDay(day);
                            currentSemesterModulesWeekly.get(i).setLectureTime(Integer.parseInt(lecture.trim()));
                            currentSemesterModulesWeekly.get(i).setLabTime(Integer.parseInt(lab.trim()));
                            currentSemesterModulesWeekly.get(i).setTutorialTime(Integer.parseInt(tutorial.trim()));
                        }
     */

    public boolean checkIfValidTime(Student student) {
        for (int i = 0; i < currentSemesterModulesWeekly.size(); i++) {
            if (currentSemesterModulesWeekly.get(i).getLectureTime() == 0) {
                System.out.println("PLEASE INSERT VALID TIME FOR LECTURE TIME FOR " +
                        currentSemesterModulesWeekly.get(i).getModuleCode());
                return false;
            }
            if (currentSemesterModulesWeekly.get(i).getTutorialTime() == 0) {
                System.out.println("PLEASE INSERT VALID TIME FOR TUTORIAL TIME FOR " +
                        currentSemesterModulesWeekly.get(i)
                                .getModuleCode());
                return false;
            }
            if (currentSemesterModulesWeekly.get(i).getLabTime() == 0) {
                System.out.println("PLEASE INSERT VALID TIME FOR LAB TIME FOR " + currentSemesterModulesWeekly.get(i)
                        .getModuleCode());
                return false;
            }
            if (!currentSemesterModulesWeekly.get(i).getDay().isEmpty()) {
                System.out.println("PLEASE INSERT VALID DAY FOR " + currentSemesterModulesWeekly.get(i)
                        .getModuleCode());
                return false;
            }
        }
        return true;
    }

    public ArrayList<ModuleWeekly> getCurrentSemesterModulesWeekly() {
        return currentSemesterModulesWeekly;
    }

    public ModuleList getCurrentSemesterModules() {
        return currentSemesterModules;
    }


}
