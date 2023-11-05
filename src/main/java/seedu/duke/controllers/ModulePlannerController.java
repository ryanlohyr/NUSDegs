package seedu.duke.controllers;

import seedu.duke.models.logic.CompletePreqs;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.CommandManager;
import seedu.duke.models.schema.UserCommands;
import seedu.duke.models.logic.Api;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static seedu.duke.controllers.ModuleMethodsController.determinePrereq;
import static seedu.duke.controllers.ModuleMethodsController.showModulesLeft;
import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.getRequiredModulesForStudent;
import static seedu.duke.controllers.ModuleMethodsController.canCompleteModule;
import static seedu.duke.controllers.ModuleMethodsController.deleteModule;
import static seedu.duke.controllers.ModuleMethodsController.addModule;
import static seedu.duke.controllers.ModuleMethodsController.recommendScheduleToStudent;
import static seedu.duke.utils.Parser.parseArguments;
import static seedu.duke.utils.Parser.parseCommand;
import static seedu.duke.controllers.ModuleServiceController.validateMajorInput;
import static seedu.duke.views.CommandLineView.displayWelcome;
import static seedu.duke.views.CommandLineView.displayReady;
import static seedu.duke.views.CommandLineView.displayGoodbye;
import static seedu.duke.views.CommandLineView.displayGetMajor;
import static seedu.duke.views.CommandLineView.displayGetYear;
import static seedu.duke.views.CommandLineView.printListOfCommands;

public class ModulePlannerController {
    private final Parser parser;
    private Student student;
    private ModuleList modulesTaken;
    private CompletePreqs addModulePreqs;
    private CommandManager commandManager;

    public ModulePlannerController() {
        this.commandManager = new CommandManager();
        this.parser = new Parser();
        this.student = new Student();

        this.modulesTaken = new ModuleList();


        //Pass in Hashmap of mods with Preqs
        //this.addModulePreqs = new CompletePreqs(addModsWithPreqs(new HashMap<String, List<String>>()));
        this.addModulePreqs = new CompletePreqs();

        //Pass in the list of mods completed.
        addModulePreqs.initializeCompletedMods(modulesTaken);
    }

    /**
     * Starts the application, guiding the user through its execution.
     * This method performs the following steps:
     * 1. Display a welcome message to the user.
     * 2. Initialize user-related data or settings.
     * 3. Display a message indicating that the application is ready for input.
     * 4. Handle user input until an exit command is given.
     * 5. Display a goodbye message when the application is finished.\
     * @author ryanlohyr
     *
     */
    public void start() {
        displayWelcome();
        initialiseUser();
        displayReady();
        handleUserInputTillExitCommand();
        displayGoodbye();
    }

    public void initialiseUser() {
        Scanner in = new Scanner(System.in);
        String userInput;
        do {
            System.out.println("Please enter your name: ");
            userInput = in.nextLine().trim();
        } while (!parser.checkNameInput(userInput, commandManager.getListOfCommands()));
        student.setName(userInput);

        // Get and set student's major
        displayGetMajor(student.getName());
        do {
            userInput = in.nextLine().trim();
        } while (!validateMajorInput(userInput));
        student.setFirstMajor(userInput);

        // Get and set student's year
        displayGetYear();
        do {
            userInput = in.nextLine().trim();
        } while (!Parser.isValidAcademicYear(userInput.toUpperCase()));
        student.setYear(userInput.toUpperCase());
    }

    public void handleUserInputTillExitCommand() {

        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        String command = parseCommand(userInput);

        while (!command.equals(UserCommands.EXIT_COMMAND)) {
            command = parseCommand(userInput);
            String[] arguments = parseArguments(userInput);

            if(!commandManager.getListOfCommands().contains(command)){
                UserError.displayInvalidInputCommand(command);
                userInput = in.nextLine();
                continue;
            }

            boolean validInput = Parser.isValidInputForCommand(command, arguments);

            if (!validInput) {
                UserError.displayInvalidMethodCommand(command);
                userInput = in.nextLine();
                continue;
            }

            processCommand(command, arguments, userInput);

            userInput = in.nextLine();
        }
        in.close();
    }

    private void processCommand(String command, String[] arguments, String userInput) {
        switch (command) {
        case UserCommands.LEFT_COMMAND: {
            showModulesLeft(student.getModuleCodesLeft());
            break;
        }
        case UserCommands.PACE_COMMAND: {
            computePace(arguments, student.getCurrentModuleCredits());
            break;
        }
        case UserCommands.PREREQUISITE_COMMAND: {
            String module = arguments[0];
            determinePrereq(module.toUpperCase(), student.getMajor()); //to convert "CEG" to dynamic course
            break;
        }
        case UserCommands.RECOMMEND_COMMAND: {
            recommendScheduleToStudent(student);
            break;
        }
        case UserCommands.ADD_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            int targetSem = Integer.parseInt(arguments[1]);

            addModule(module, targetSem, student);
            break;
        }
        case UserCommands.DELETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();

            deleteModule(module,student);
            break;
        }
        case UserCommands.VIEW_SCHEDULE_COMMAND: {
            student.printSchedule();
            break;
        }
        case UserCommands.COMPLETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            //to add to user completed module
            if (canCompleteModule(arguments, student.getMajorModuleCodes(), addModulePreqs)) {
                student.completeModuleSchedule(module);
            }
            break;
        }
        case UserCommands.REQUIRED_MODULES_COMMAND: {
            getRequiredModulesForStudent(student.getMajor());
            break;
        }
        case UserCommands.INFO_COMMAND: {
            Api.infoCommands(arguments[0], userInput);
            break;
        }
        case UserCommands.SEARCH_MODULE_COMMAND: {
            Api.searchCommand(userInput);
            break;
        }
        case UserCommands.HELP_COMMAND: {
            printListOfCommands(commandManager);
            break;
        }
        default: {
            break;
        }
        }

    }

    /**
     * Add all mods that require prerequisites to a map storing the mod and a set of preqs
     *
     * @param list HashMap of ModsWithPreqs
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
     * Adds a value to a HashMap with a list of values associated with a key.
     * If the key does not exist in the map, it creates a new key-value pair with an empty list.
     * The value is added to the list associated with the key.
     *
     * @param map   The HashMap in which the value will be added.
     * @param key   The key to which the value will be associated.
     * @param value The value to add to the list.
     */
    public static void addValue(HashMap<String, List<String>> map, String key, String value) {
        // If the map does not contain the key, put an empty list for that key
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        // Add the value to the list associated with the key
        map.get(key).add(value);
    }
}
