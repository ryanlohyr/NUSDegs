package seedu.duke.controllers;

import seedu.duke.models.logic.CompletePreqs;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.CommandManager;
import seedu.duke.models.schema.UserCommands;
import seedu.duke.models.logic.Api;
import seedu.duke.views.CommandLineView;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import static seedu.duke.controllers.ModuleMethodsController.showModulesLeft;
import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.getRequiredModulesForStudent;
import static seedu.duke.controllers.ModuleMethodsController.completeModule;
import static seedu.duke.controllers.ModuleMethodsController.deleteModule;
import static seedu.duke.controllers.ModuleMethodsController.addModule;
import static seedu.duke.controllers.ModuleServiceController.determinePrereq;
import static seedu.duke.models.logic.ScheduleGenerator.generateRecommendedSchedule;
import static seedu.duke.utils.Parser.parseArguments;
import static seedu.duke.utils.Parser.parseCommand;
import static seedu.duke.controllers.ModuleServiceController.checkMajorInput;
import static seedu.duke.controllers.ModuleServiceController.chooseToAddToSchedule;
import static seedu.duke.views.CommandLineView.displayWelcome;
import static seedu.duke.views.CommandLineView.displayReady;
import static seedu.duke.views.CommandLineView.displayGoodbye;
import static seedu.duke.views.CommandLineView.displayGetMajor;
import static seedu.duke.views.CommandLineView.displayGetYear;
import static seedu.duke.views.CommandLineView.handleMajorMessage;
import static seedu.duke.views.CommandLineView.printListOfCommands;




public class ModulePlannerController {
    private CommandLineView view;
    private Parser parser;
    private Student student;
    private ModuleList modulesMajor;
    private ModuleList modulesTaken;
    private ModuleList modulesLeft;
    private HashMap<String, List<String>> modsWithPreqs;
    private CompletePreqs addModulePreqs;

    private CommandManager commandManager;

    public ModulePlannerController() {
        this.commandManager = new CommandManager();
        this.parser = new Parser();
        this.student = new Student();

        //This modules list of taken and classes left can be in a storage class later on.
        this.modulesMajor = null;
        this.modulesTaken = new ModuleList();
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
        } while (!checkMajorInput(userInput));
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
            displayMessage(student.getModuleCodesLeft());
            /*
            showModulesLeft(modulesMajor,modulesTaken);

             */
            break;
        }
        case UserCommands.PACE_COMMAND: {
            computePace(arguments, student.getCurrentModuleCredits());
            break;
        }
        case UserCommands.PREREQUISITE_COMMAND: {
            String module = arguments[0];
            determinePrereq(module.toUpperCase(), student.getMajor().toString()); //to convert "CEG" to dynamic course
            break;
        }
        case UserCommands.RECOMMEND_COMMAND: {
            chooseToAddToSchedule(student, generateRecommendedSchedule(arguments[0].toUpperCase()));
            break;
        }
        case UserCommands.SET_MAJOR_COMMAND: {
            if (arguments.length == 1) {
                String major = arguments[0].toUpperCase();
                student.setMajor(major);
                modulesMajor = new ModuleList(student.getMajor().toString());
            }
            handleMajorMessage(arguments.length, student.getMajor());
            break;
        }
        case UserCommands.ADD_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            int targetSem = Integer.parseInt(arguments[1]);
            try {
                student.addModuleSchedule(module, targetSem);
                displaySuccessfulAddMessage();
                student.printSchedule();
            } catch (InvalidObjectException | IllegalArgumentException e) {
                displayMessage(e.getMessage());
            } catch (FailPrereqException f) {
                showPrereqCEG(module);
                displayMessage(f.getMessage());
            }
            //ryan's addModule(module, targetSem, student);
            break;
        }
        case UserCommands.DELETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            try {
                student.deleteModuleSchedule(module);
                displaySuccessfulDeleteMessage();
                student.printSchedule();
            } catch (IllegalArgumentException | FailPrereqException e) {
                displayMessage(e.getMessage());
            }
            //ryan's deleteModule(module,student);
            break;
        }
        case UserCommands.VIEW_SCHEDULE_COMMAND: {
            student.printSchedule();
            break;
        }
        case UserCommands.COMPLETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            //to add to user completed module
            student.completeModuleSchedule(module);
            /*
            if (modulesMajor != null) {
                if (addModulePreqs.checkModInput(arguments, modulesMajor)) {
                    String moduleCompleted = arguments[0].toUpperCase();
                    addModulePreqs.getUnlockedMods(moduleCompleted);
                    addModulePreqs.printUnlockedMods(moduleCompleted);
                    modulesTaken.addModule(moduleCompleted);
                    break;
                }
            } else {
                ErrorHandler.emptyMajor();
            }
            */

            //ryan's completeModule(arguments, modulesMajor, modulesTaken, addModulePreqs);
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



}
