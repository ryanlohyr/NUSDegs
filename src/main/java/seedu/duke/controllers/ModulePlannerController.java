package seedu.duke.controllers;
import org.json.simple.JSONObject;
import seedu.duke.models.logic.CompletePreqs;
import seedu.duke.models.logic.DataRepository;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Major;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.logic.Api;
import seedu.duke.views.CommandLineView;
import seedu.duke.utils.Parser;

import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;

import static seedu.duke.models.logic.Api.getFullModuleInfo;
import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.DataRepository.getRequirements;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;

public class ModulePlannerController {
    private CommandLineView view;
    private Parser parser;
    private Student student;
    private ModuleList modulesMajor;
    private ModuleList modulesTaken;
    private ModuleList modulesLeft;
    private HashMap<String, List<String>> modsWithPreqs;
    private CompletePreqs addModulePreqs;

    public ModulePlannerController() {
        this.view = new CommandLineView();
        this.parser = new Parser();
        this.student = new Student();



        //This modules list of taken and classes left can be in a storage class later on.
        this.modulesMajor = new ModuleList("CS1231S CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");
        this.modulesTaken = new ModuleList("CS1231S MA1511");
        this.modulesLeft = new ModuleList();

        Schedule schedule = new Schedule();

        student.setSchedule(schedule);

        modsWithPreqs = new HashMap<>();

        //Pass in Hashmap of mods with Preqs
        this.addModulePreqs = new CompletePreqs(addModsWithPreqs(modsWithPreqs));
        //Pass in the list of mods completed.
        addModulePreqs.initializeCompletedMods(modulesTaken);


    }

    /**
     * Starts the interactive command-line interface for the academic module management system.
     * This method displays a welcome message, reads user input, and processes various commands.
     * While the user input is not "bye," the method processes the input and responds accordingly.
     * The commands are case-insensitive, and the response is displayed in the view.
     */
    public void start() {
        view.displayWelcome();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while (!userInput.equals("bye")) {

            String[] words = userInput.split(" ");

            String initialWord = words[0].toLowerCase();
            boolean validInput;

            validInput = Parser.isValidInput(initialWord, words);
            if (validInput) {
                switch (initialWord) {
                case "hi": {
                    view.displayMessage("can put the commands here");
                    break;
                }
                case "hello": {
                    view.displayMessage("yup");
                    break;
                }
                case "left": {
                    ArrayList<String> modules = listModulesLeft();
                    view.displayMessage("Modules left:");
                    for (String module : modules) {
                        view.displayMessage(module);
                    }
                    break;
                }
                case "pace": {
                    //assumed that everyone graduates at y4s2
                    //waiting for retrieving logic
                    int modulesCreditsCompleted = 100;
                    int totalCreditsToGraduate = 160;
                    int creditsLeft = totalCreditsToGraduate - modulesCreditsCompleted;
                    computePace(words, creditsLeft);
                    break;
                }
                case "prereq": {
                    String module = words[1];
                    determinePrereq(module.toUpperCase(),"CEG"); //to convert "CEG" to dynamic course
                    break;
                }
                case "test": {
                    System.out.println(getRequirements("CEG"));
                    break;
                }
                case "recommend": {
                    String keyword = words[1];
                    chooseToAddToSchedule(generateRecommendedSchedule(keyword.toUpperCase()),in);
                    break;
                }
                case "major": {
                    if (words.length == 2) {
                        Major major = Major.valueOf(words[1].toUpperCase());
                        student.setMajor(major);
                    }
                    view.handleMajorMessage(words.length, student.getMajor());
                    break;
                }
                case "add": {
                    String module = words[1].toUpperCase();
                    int targetSem = Integer.parseInt(words[2]);
                    boolean isSuccessful = student.getSchedule().addModule(module, targetSem);
                    view.handleAddMessage(isSuccessful);
                    if (isSuccessful) {
                        student.getSchedule().printMainModuleList();
                    }
                    break;
                }
                case "delete": {
                    String module = words[1].toUpperCase();
                    boolean isSuccessful = student.getSchedule().deleteModule(module);
                    view.handleDeleteMessage(isSuccessful);
                    if (isSuccessful) {
                        student.getSchedule().printMainModuleList();
                    }
                    break;
                }
                case "schedule": {
                    student.getSchedule().printMainModuleList();
                    break;
                }
                case "complete": {
                    if (addModulePreqs.checkModInput(words, modulesMajor)) {
                        String moduleCompleted = words[1];
                        addModulePreqs.getUnlockedMods(moduleCompleted);
                        addModulePreqs.printUnlockedMods(moduleCompleted);
                        break;
                    }
                    break;
                }
                case "required": {
                    getRequiredModules(student.getMajor());
                    break;
                }
                case "info": {
                    Api.infoCommands(words[1], userInput);
                    break;
                }
                case "search": {
                    Api.searchCommand(userInput);
                    break;
                }
                default: {
                    view.displayMessage("Hello " + userInput);
                    break;
                }
                }
            }
            userInput = in.nextLine();
        }
    }

    public void determinePrereq(String module, String major){
        JSONObject moduleInfo =  getFullModuleInfo(module);
        if(moduleInfo == null){
            return;
        }

        ArrayList<String> prereq = getModulePrereqBasedOnCourse(module, major);
        view.displayMessage(Objects.requireNonNullElseGet(prereq, () -> "Module " + module +
                " has no prerequisites."));
    }

    /**
     * Prompts the user to choose whether to add a list of modules to their draft schedule.
     * Displays the list of modules and asks for user input. Handles user input validation.
     *
     * @param scheduleToAdd A list of modules to be added to the schedule.
     * @param in            A Scanner object for user input.
     */
    public void chooseToAddToSchedule(ArrayList<String> scheduleToAdd, Scanner in){

        view.displayMessage(scheduleToAdd);
        view.displayMessage("Do you want to add this to your draft schedule?, please input 'Y' or 'N'");

        String userInput = in.nextLine();

        while (!userInput.equals("N") && !userInput.equals(("Y"))){
            view.displayMessage("Invalid input, please choose Y/N");
            userInput = in.nextLine();
        }

        if(userInput.equals("Y")){
            view.displayMessage("yes was chosen");
            student.getSchedule().addRecommendedScheduleListToSchedule(scheduleToAdd);
            student.getSchedule().printMainModuleList();

        }else {
            view.displayMessage("No was chosen");
        }

    }



    /**
     * Computes and returns the list of modules that are left in the ModuleList modulesMajor
     * after subtracting the modules in the ModuleList modulesTaken.
     *
     * @author janelleenqi
     * @return An ArrayList of module codes representing the modules left after the subtraction.
     *
     */
    public ArrayList<String> listModulesLeft() {
        //modulesMajor.txt - modulesTaken.txt
        try {
            modulesLeft.getDifference(modulesMajor, modulesTaken);
            return modulesLeft.getMainModuleList();
        } catch (InvalidObjectException e) {
            view.displayMessage("Error: " + e.getMessage());
        }
        return null;
    }



    /**
     * Computes the recommended pace for completing a degree based on the provided academic year
     * and credits left until graduation.
     *
     * @author ryanlohyr
     * @param userInput   An array of user input where userInput[0] is the command and userInput[1]
     *                    is the academic year.
     * @param creditsLeft The number of credits left until graduation.
     *
     */
    public void computePace(String[] userInput, int creditsLeft) {
        boolean argumentProvided = userInput.length != 1;
        //wait for text file logic
        if (!argumentProvided) {
            view.displayMessage("You currently have " + creditsLeft + " MCs till graduation");
            return;
        }
        if (!parser.isValidAcademicYear(userInput[1])) {
            return;
        }

        String[] parts = userInput[1].split("/");
        String year = parts[0].toUpperCase();
        String semester = parts[1].toUpperCase();

        int lastSemesterOfYear = 2;
        int lastYearOfDegree = 4;


        int yearIntValue = Character.getNumericValue(year.charAt(1));
        int semesterIntValue = Character.getNumericValue(semester.charAt(1));
        //if we are at y2s1, we have 5 semesters left
        int semestersLeft = (lastYearOfDegree - yearIntValue) * 2 + (lastSemesterOfYear - semesterIntValue);
        int creditsPerSem = Math.round((float) creditsLeft / semestersLeft);
        view.displayMessage("You have " + creditsLeft + "MCs for " + semestersLeft + " semesters. "
                + "Recommended Pace: " + creditsPerSem + "MCs per sem until graduation");
    }


    /**
     * Add all mods that require prerequisites to a map storing the mod and a set of preqs
     *
     * @param list
     * @return HashMap of Mods with their corresponding preqs
     */

    private HashMap<String, List<String>> addModsWithPreqs(HashMap<String, List<String>> list) {
        //Only two mods don't have preqs MA1511 and CS1231S.
        // In the future this will be dealt
        addValue(list, "CS3230", "CS2030S");
        addValue(list, "CS3230", "CS1231S");

        addValue(list, "CS2030S", "CS1231S");

        addValue(list, "CS2040S", "CS1231S");

        addValue(list, "CS2106", "CS1231S");

        addValue(list, "CS2109S", "CS1231S");

        return list;
    }


    /**
     * Helper function to addModsWithPreqs to add Strings and sets together
     *
     * @param map
     * @param key
     * @param value
     */
    public static void addValue(HashMap<String, List<String>> map, String key, String value) {
        // If the map does not contain the key, put an empty list for that key
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        // Add the value to the list associated with the key
        map.get(key).add(value);
    }

    public void getRequiredModules(Major major) {
        try {
            view.printTXTFile(DataRepository.getFullRequirements(major));
        } catch (NullPointerException | FileNotFoundException e) {
            view.displayMessage("☹ An error occurred. " + e.getMessage());
        }
    }

}
