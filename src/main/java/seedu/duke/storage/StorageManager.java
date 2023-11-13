package seedu.duke.storage;

import seedu.duke.models.schema.Event;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.ModuleWeekly;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.TimetableUserCommand;
import seedu.duke.utils.exceptions.CorruptedFileException;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.utils.exceptions.MissingFileException;
import seedu.duke.utils.exceptions.TimetableUnavailableException;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static seedu.duke.storage.FileDecoder.createNewStorageFile;
import static seedu.duke.storage.FileDecoder.retrieveTimetable;
import static seedu.duke.storage.FileDecoder.retrieveStudentDetails;
import static seedu.duke.storage.FileDecoder.retrieveSchedule;
import static seedu.duke.storage.ResourceStorage.determineRequirements;


public class StorageManager {

    private final String userDirectory;

    /**
     * Constructs a new Storage instance with the specified file path.
     */
    public StorageManager() {
        this.userDirectory = System.getProperty("user.dir");
    }

    //@@author ryanlohyr
    /**
     * Retrieves a list of modules requirements for a specified major.
     *
     * @param major The major for which to retrieve requirements.
     * @return An ArrayList of module codes.
     * @throws RuntimeException If the specified major requirements file is not found.
     */
    public static ArrayList<String> getRequirements(String major) {
        String[] courseArray = determineRequirements(major);
        return new ArrayList<>(Arrays.asList(courseArray));
    }



    //@@author SebasFok
    /**
     * Creates a data directory containing txt folders to store student details, schedule and timetable.
     */
    public void createUserStorageFile() {
        String dataDirectory = userDirectory + "/data";
        createNewStorageFile(dataDirectory);
    }

    /**
     * Loads the student's schedule from the "schedule.txt" file, including modules per semester and individual modules.
     * Also retains the completion status of each module in the schedule.
     *
     * @return A Schedule object representing the loaded schedule.
     * @throws MissingFileException    If the "schedule.txt" file is missing.
     * @throws CorruptedFileException  If the file is corrupted or has unexpected content.
     */
    public Schedule loadSchedule() throws MissingFileException, CorruptedFileException {
        String scheduleFilePath = userDirectory + "/data/schedule.txt";
        return retrieveSchedule(scheduleFilePath);
    }

    //@@author SebasFok
    /**
     * Loads the student's details (name, major, and year) from the "studentDetails.txt" file.
     *
     * @return An ArrayList containing the loaded student details in the order [Name, Major, Year].
     * @throws MissingFileException    If the "studentDetails.txt" file is missing.
     * @throws CorruptedFileException  If the file is corrupted or has unexpected content.
     */
    public ArrayList<String> loadStudentDetails() throws MissingFileException, CorruptedFileException {

        String studentDetailsFilePath = userDirectory + "/data/studentDetails.txt";

        return retrieveStudentDetails(studentDetailsFilePath);
    }

    //@@author janelleenqi
    /**
     * Loads timetable user commands from the timetable.txt save file and processes them to update the student's
     * timetable.
     *
     * @param student The student whose timetable is being updated.
     * @return An ArrayList of TimetableUserCommand objects representing the loaded commands.
     * @throws MissingFileException If the timetable file is missing.
     * @throws CorruptedFileException If the timetable file is corrupted or contains invalid commands.
     */
    public ArrayList<TimetableUserCommand> loadTimetable(Student student)
            throws MissingFileException, CorruptedFileException {

        String timetableFilePath = userDirectory + "/data/timetable.txt";

        return retrieveTimetable(student,timetableFilePath);
    }

    //@@author janelleenqi
    /**
     * Adds events to the student's timetable based on the provided timetable user commands.
     *
     * @param timetableUserCommands An ArrayList of TimetableUserCommand objects representing the commands to process.
     * @param student               The student whose timetable is being updated.
     * @throws CorruptedFileException If the provided timetable user commands are corrupted or contain invalid commands.
     */
    public void addEventsToStudentTimetable(ArrayList<TimetableUserCommand> timetableUserCommands, Student student)
            throws CorruptedFileException {
        ArrayList<ModuleWeekly> currentSemModulesWeekly = student.getTimetable().getCurrentSemesterModulesWeekly();
        for (TimetableUserCommand currentTimetableCommand : timetableUserCommands) {
            //not exit, not clear
            try {
                currentTimetableCommand.processTimetableCommandLesson(currentSemModulesWeekly);
            } catch (InvalidTimetableUserCommandException e) {
                //corrupted
                throw new CorruptedFileException();
            }
        }
    }

    //@@author SebasFok
    /**
     * Saves the student's details (name, major, and year) to the "studentDetails.txt" file.
     *
     * @param student The Student object containing the details to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveStudentDetails (Student student) throws IOException {

        String studentDetailsFilePath = userDirectory + "/data/studentDetails.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentDetailsFilePath))) {

            String name = student.getName();

            String major = student.getMajor();

            String year = student.getYear();

            // Write the new content to the file
            writer.write("Name | " + name);
            writer.newLine();

            writer.write("Major | " + major);
            writer.newLine();

            writer.write(("Year | " + year));
            writer.newLine();
        }
    }

    //@@author SebasFok
    /**
     * Saves the student's schedule to the "schedule.txt" file.
     *
     * @param student The Student object containing the details to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveSchedule(Student student) throws IOException {

        String scheduleFilePath = System.getProperty("user.dir") + "/data/schedule.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFilePath))) {

            int[] modulesPerSemArray = student.getSchedule().getModulesPerSem();

            StringBuilder modulesPerSemNumbers = new StringBuilder(Integer.toString(modulesPerSemArray[0]));
            for (int i = 1; i < modulesPerSemArray.length; i++) {
                modulesPerSemNumbers.append(",").append(modulesPerSemArray[i]);
            }

            // Write the new content to the file
            writer.write("ModulesPerSem | " + modulesPerSemNumbers);
            writer.newLine();

            ModuleList modulesPlanned = student.getSchedule().getModulesPlanned();
            int numberOfModules = student.getSchedule().getModulesPlanned().getMainModuleList().size();
            String completionStatus;
            for (int i = 0; i < numberOfModules; i++) {
                String moduleCode = modulesPlanned.getModuleByIndex(i).getModuleCode();
                if (modulesPlanned.getModuleByIndex(i).getCompletionStatus()) {
                    completionStatus = "O";
                } else {
                    completionStatus = "X";
                }
                writer.write("Module | " + moduleCode + " | " + completionStatus);
                writer.newLine();  // Move to the next line
            }
        }
    }

    public static void saveTimetable(Student student) throws IOException {

        String timetableFilePath = System.getProperty("user.dir") + "/data/timetable.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(timetableFilePath))) {

            // Write the new content to the file
            writer.write("TimetableForCurrentSem");
            writer.newLine();

            //latest info
            student.updateTimetable();

            ArrayList<ModuleWeekly> currentSemesterModules = student.getTimetable().getCurrentSemesterModulesWeekly();
            for (ModuleWeekly module : currentSemesterModules) {
                for (Event event : module.getWeeklyTimetable()) {
                    writer.write(event.toSave());
                    writer.newLine();
                }
            }
        } catch (TimetableUnavailableException e) {
            //no events in timetable, do nothing
        }
    }

    // Below this comment are standard file methods

    //@@author SebasFok
    /**
     * This method takes in the path of a txt file and adds 'textToAdd' to the last line
     * of the file
     *
     * @param filePath location of the file to be edited
     * @param textToAdd String to be added to the end of the txt file
     */
    public static void addTextToFile(String filePath, String textToAdd) {
        try {
            // Create a FileWriter object with the specified file path in append mode (true).
            FileWriter fileWriter = new FileWriter(filePath, true);

            // Create a BufferedWriter to efficiently write text.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the text to the file.
            bufferedWriter.write(textToAdd);

            // Write a newline character to separate lines.
            bufferedWriter.newLine();

            // Close the BufferedWriter to release resources.
            bufferedWriter.close();

            //System.out.println("Text added to the file successfully.");
        } catch (IOException e) {
            System.out.println("An IOException occurred: " + e.getMessage());
        }
    }
}
