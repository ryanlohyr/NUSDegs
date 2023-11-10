package seedu.duke.models.schema;

import seedu.duke.models.logic.Api;
import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;

import static seedu.duke.utils.Parser.parseArguments;
import static seedu.duke.utils.Parser.parseCommand;
import static seedu.duke.views.CommandLineView.printListOfCommands;

import static seedu.duke.controllers.ModuleMethodsController.computePace;
import static seedu.duke.controllers.ModuleMethodsController.showModulesLeft;
import static seedu.duke.controllers.ModuleMethodsController.determinePrereq;
import static seedu.duke.controllers.ModuleMethodsController.recommendScheduleToStudent;
import static seedu.duke.controllers.ModuleMethodsController.addModule;
import static seedu.duke.controllers.ModuleMethodsController.deleteModule;
import static seedu.duke.controllers.ModuleMethodsController.shiftModule;
import static seedu.duke.controllers.ModuleMethodsController.clearSchedule;
import static seedu.duke.controllers.ModuleMethodsController.completeModule;
import static seedu.duke.controllers.ModuleMethodsController.getRequiredModulesForStudent;

public class UserCommand implements UserCommandWord {

    private final String userInput;
    private final String commandWord;
    private final String[] arguments;
    private final boolean isValid;

    private CommandManager commandManager;

    public UserCommand(String userInput) {
        this.userInput = userInput.trim();
        commandWord = parseCommand(userInput);
        arguments = parseArguments(userInput);
        this.commandManager = new CommandManager();

        if (!commandManager.getListOfCommandNames().contains(commandWord)){
            UserError.displayInvalidInputCommand(commandWord);
            isValid = false;
            return;
        }

        boolean validArgument = Parser.isValidInputForCommand(commandWord, arguments);

        if (!validArgument) {
            UserError.displayInvalidMethodCommand(commandWord);
            isValid = false;
            return;
        }

        isValid = true;
    }

    public UserCommand() {
        userInput = null;
        commandWord = null;
        arguments = null;
        isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean isBye() {
        if (commandWord == null) {
            return false;
        }
        return this.commandWord.equals(UserCommandWord.EXIT_COMMAND);
    }

    public void processCommand(Student student) {
        switch (commandWord) {
        case UserCommandWord.LEFT_COMMAND: {
            showModulesLeft(student.getModuleCodesLeft());
            break;
        }
        case UserCommandWord.PACE_COMMAND: {
            computePace(arguments, student.getCurrentModuleCredits(), student.getYear());
            break;
        }
        case UserCommandWord.PREREQUISITE_COMMAND: {
            String module = arguments[0];
            determinePrereq(module.toUpperCase(), student.getMajor()); //to convert "CEG" to dynamic course
            break;
        }
        case UserCommandWord.RECOMMEND_COMMAND: {
            recommendScheduleToStudent(student);
            break;
        }
        case UserCommandWord.ADD_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            int targetSem = Integer.parseInt(arguments[1]);

            addModule(module, targetSem, student);
            break;
        }
        case UserCommandWord.DELETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();

            deleteModule(module,student);
            break;
        }
        case UserCommandWord.SHIFT_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            int targetSem = Integer.parseInt(arguments[1]);

            shiftModule(module, targetSem, student);
            break;
        }
        case UserCommandWord.VIEW_SCHEDULE_COMMAND: {
            student.printSchedule();
            break;
        }
        case UserCommandWord.CLEAR_SCHEDULE_COMMAND: {
            clearSchedule(student);
            break;
        }
        case UserCommandWord.COMPLETE_MODULE_COMMAND: {
            String module = arguments[0].toUpperCase();
            //to add to user completed module
            completeModule(student, module);

            break;
        }
        case UserCommandWord.REQUIRED_MODULES_COMMAND: {
            getRequiredModulesForStudent(student.getMajor());
            break;
        }
        case UserCommandWord.INFO_COMMAND: {
            Api.infoCommands(arguments[0], userInput);
            break;
        }
        case UserCommandWord.SEARCH_MODULE_COMMAND: {
            Api.searchCommand(userInput);
            break;
        }
        case UserCommandWord.HELP_COMMAND: {
            printListOfCommands(commandManager);
            break;
        }
        case UserCommandWord.TIMETABLE_COMMAND: {
            student.timetableShowOrModify(arguments[0]);
            break;
        }
        default: {
            break;
        }
        }

    }

}
