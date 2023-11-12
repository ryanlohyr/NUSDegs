package seedu.duke.controllers;

import seedu.duke.models.schema.Storage;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.CommandManager;
import seedu.duke.models.schema.UserCommand;
import seedu.duke.utils.Parser;
import seedu.duke.utils.exceptions.CorruptedFileException;
import seedu.duke.utils.exceptions.MissingFileException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.controllers.ModuleServiceController.validateMajorInput;

import static seedu.duke.utils.Utility.detectInternet;
import static seedu.duke.utils.Utility.saveStudentData;
import static seedu.duke.views.CommandLineView.displayWelcome;
import static seedu.duke.views.CommandLineView.displayReady;
import static seedu.duke.views.CommandLineView.displayGoodbye;
import static seedu.duke.views.CommandLineView.displayGetMajor;
import static seedu.duke.views.CommandLineView.displayGetYear;

public class ModulePlannerController {
    private final Parser parser;
    private final Student student;
    private final CommandManager commandManager;
    private Storage storage;

    public ModulePlannerController() {
        this.commandManager = new CommandManager();
        this.parser = new Parser();
        this.student = new Student();
    }

    /**
     * Starts the application, guiding the user through its execution.
     * This method performs the following steps:
     * 1. Display a welcome message to the user.
     * 2. Initialize user-related data or settings.
     * 3. Display a message indicating that the application is ready for input.
     * 4. Handle user input until an exit command is given.
     * 5. Display a goodbye message when the application is finished.\
     *
     * @author ryanlohyr
     */
    public void start() throws IOException {
        displayWelcome();
        detectInternet();
        initialiseUser();
        displayReady();
        handleUserInputTillExitCommand();
        saveStudentData(storage,student);
        displayGoodbye();
    }

    public void initialiseUser() throws IOException {
        Scanner in = new Scanner(System.in);
        String userInput;

        // Try to load storage file for name, major, year and schedule. If successful, will not prompt anymore
        // If load fails, will create storage file based on userName and prompt for major and year
        storage = new Storage();
        try {
            System.out.println("Attempting to retrieve data from save file... Sorry this takes a while!");

            // Load name, major and year from studentDetails.txt file
            ArrayList<String> studentDetails = storage.loadStudentDetails();

            int correctNumOfStudentInfo = 3;

            if (studentDetails == null || studentDetails.size() != correctNumOfStudentInfo) {
                throw new CorruptedFileException();
            }
            // Check if name is valid and set if yes
            if (!parser.checkNameInput(studentDetails.get(0), commandManager.getListOfCommandNames())) {
                throw new CorruptedFileException();
            }
            student.setName(studentDetails.get(0));

            // Check if major is valid and set if yes
            if (!validateMajorInput(studentDetails.get(1))) {
                throw new CorruptedFileException();
            }
            student.setFirstMajor(studentDetails.get(1));

            //Check if year is valid and set if yes
            if (!Parser.isValidAcademicYear(studentDetails.get(2).toUpperCase())) {
                throw new CorruptedFileException();
            }
            student.setYear(studentDetails.get(2).toUpperCase());
            // Load schedule from schedule.txt file
            student.setSchedule(storage.loadSchedule());

            System.out.println("Data successfully retrieved!");
            System.out.println("Welcome back " + student.getName() + ", you are currently in " + student.getYear() +
                    " studying " + student.getMajor());
            return;

        } catch (MissingFileException e) {
            System.out.println("Save file does not exist, creating new save file...");
            storage.createUserStorageFile();
            System.out.println("File successfully created!");
            Storage.saveSchedule(student);
        } catch (CorruptedFileException e) {
            System.out.println("Unable to retrieve any data. You do not have a save file yet " +
                    "or it may be corrupted.\n" +
                    "Please continue using the application to create a new save file or overwrite " +
                    "the corrupted file!");

        }
        do {
            System.out.println("Please enter your name: ");
            userInput = in.nextLine().trim();
        } while (!parser.checkNameInput(userInput, commandManager.getListOfCommandNames()));
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
        storage.saveStudentDetails(student);
    }

    public void handleUserInputTillExitCommand() {

        Scanner in = new Scanner(System.in);
        UserCommand currentUserCommand = new UserCommand();
        while (!currentUserCommand.isBye()) {
            currentUserCommand = new UserCommand(in.nextLine().replace("\r", ""));
            if (currentUserCommand.isValid() && !currentUserCommand.isBye()) {
                currentUserCommand.processCommand(student);
            }
        }
        in.close();
    }





}
