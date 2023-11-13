package seedu.duke.storage;

import seedu.duke.models.schema.Major;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.models.schema.TimetableUserCommand;
import seedu.duke.utils.Parser;
import seedu.duke.utils.exceptions.CorruptedFileException;
import seedu.duke.utils.exceptions.InvalidTimetableUserCommandException;
import seedu.duke.utils.exceptions.MissingFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileDecoder {

    /**
     * Retrieves a schedule from a specified file path.
     *
     * @param scheduleFilePath The file path where the schedule is stored.
     * @return The retrieved schedule.
     * @throws MissingFileException If the file is missing.
     * @throws CorruptedFileException If the file is corrupted or the data format is invalid.
     */
    public static Schedule retrieveSchedule(String scheduleFilePath) throws MissingFileException,
            CorruptedFileException {


        if (!isFileExist(scheduleFilePath)) {
            throw new MissingFileException();
        }

        Schedule schedule = new Schedule();

        try {
            // Create a FileReader and BufferedReader to read the file.
            FileReader fileReader = new FileReader(scheduleFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            int targetIndex = 0;
            int[] modulesPerSemArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

            // Read lines from the file and add them to the ArrayList.
            while ((line = bufferedReader.readLine()) != null) {

                String[] splitParts = line.split(" \\| ");

                switch (splitParts[0]) {

                // Happens once on the first line of txt file so that sorting subsequent modules is possible
                case "ModulesPerSem":
                    String[] modulesPerSemStringArray = splitParts[1].split(",");
                    for (int i = 0; i < modulesPerSemArray.length; i++) {
                        modulesPerSemArray[i] = Integer.parseInt(modulesPerSemStringArray[i]);
                    }
                    break;
                case "Module":
                    String module = splitParts[1];
                    int targetSemester = 1;
                    int indexOfLastModuleOfSem = modulesPerSemArray[targetSemester - 1] - 1;
                    while (targetIndex > indexOfLastModuleOfSem) {
                        indexOfLastModuleOfSem += modulesPerSemArray[targetSemester];
                        targetSemester += 1;
                    }

                    schedule.addModule(module, targetSemester);
                    if (splitParts[2].equals("O")) {
                        schedule.getModule(module).markModuleAsCompleted();
                    }
                    targetIndex += 1;
                    break;
                default:
                    if (!splitParts[0].trim().isEmpty()) {
                        throw new CorruptedFileException();
                    }
                }
            }

            // Close the BufferedReader to release resources.
            bufferedReader.close();
        } catch (Exception e) {
            throw new CorruptedFileException();
        }

        return schedule;

    }

    /**
     * Retrieves student details from a specified file path.
     *
     * @param studentDetailsFilePath The file path where the student details are stored.
     * @return The retrieved student details.
     * @throws MissingFileException If the file is missing.
     * @throws CorruptedFileException If the file is corrupted or the data format is invalid.
     */
    public static ArrayList<String> retrieveStudentDetails(String studentDetailsFilePath) throws MissingFileException,
            CorruptedFileException {

        if (!isFileExist(studentDetailsFilePath)) {
            throw new MissingFileException();
        }

        try {
            // Create a FileReader and BufferedReader to read the file.
            FileReader fileReader = new FileReader(studentDetailsFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> studentDetails = new ArrayList<>(3);

            String line;
            int lineNumber = 0;

            // to track which line it is supposed to be on
            HashMap<String, Integer> variableMap = new HashMap<>();
            // Adding key-value pairs
            variableMap.put("Name", 0);
            variableMap.put("Major", 1);
            variableMap.put("Year", 2);

            // Read lines from the file and add them to the ArrayList.
            while ((line = bufferedReader.readLine()) != null) {

                String[] splitParts = line.split(" \\| ");

                String userAttribute = splitParts[0];

                //validation to see that variables has not been tampered with
                if(variableMap.get(userAttribute) != lineNumber){
                    throw new CorruptedFileException();
                }

                switch (splitParts[0]) {

                case "Name":
                    String name = splitParts[1];
                    studentDetails.add(0, name);
                    break;
                case "Major":
                    String major = splitParts[1];

                    // Check if major stored in txt file is valid
                    Major.valueOf(major.toUpperCase());

                    studentDetails.add(1, major);
                    break;
                case "Year":
                    String year = splitParts[1];

                    //Check if year stored in txt file is valid
                    if (!Parser.isValidAcademicYear(year)){
                        throw new CorruptedFileException();
                    }

                    studentDetails.add(2, year);
                    break;
                default:
                    if (!splitParts[0].trim().isEmpty()) {
                        throw new CorruptedFileException();
                    }
                }
                lineNumber += 1;
            }
            // Close the BufferedReader to release resources.
            bufferedReader.close();

            return studentDetails;
        } catch (Exception e) {
            throw new CorruptedFileException();
        }
    }

    //@@author janelleenqi
    /**
     * Retrieves a timetable from a specified file path.
     *
     * @param student The student for whom the timetable is retrieved.
     * @param timetableFilePath The file path where the timetable is stored.
     * @return The retrieved timetable as a list of commands.
     * @throws MissingFileException If the file is missing.
     * @throws CorruptedFileException If the file is corrupted or the data format is invalid.
     */
    public static ArrayList<TimetableUserCommand> retrieveTimetable(Student student, String timetableFilePath)
            throws MissingFileException, CorruptedFileException {

        if (!isFileExist(timetableFilePath)) {
            throw new MissingFileException();
        }

        ArrayList<TimetableUserCommand> timetableUserCommands;
        try {
            // Create a FileReader and BufferedReader to read the file.
            FileReader fileReader = new FileReader(timetableFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            if ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("TimetableForCurrentSem")) {
                    throw new CorruptedFileException();
                }
            }
            timetableUserCommands = new ArrayList<>();

            // Read lines from the file and add them to the ArrayList.
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    timetableUserCommands.add(new TimetableUserCommand(student,
                            student.getTimetable().getCurrentSemesterModulesWeekly(), line));
                } catch (InvalidTimetableUserCommandException e) {
                    //corrupted
                    throw new CorruptedFileException();
                }

            }

            // Close the BufferedReader to release resources.
            bufferedReader.close();
        } catch (Exception e) {
            throw new CorruptedFileException();
        }

        return timetableUserCommands;

    }

    //@@author SebasFok
    /**
     * Creates "schedule.txt", "studentDetails.txt" and "timetable.txt" files in the data directory to store student
     * data
     *
     * @param storageDirectory location of storage directory to be created
     */
    public static void createNewStorageFile(String storageDirectory) {

        createDirectory(storageDirectory);

        createFileInDirectory(storageDirectory, "schedule.txt");
        createFileInDirectory(storageDirectory, "studentDetails.txt");
        createFileInDirectory(storageDirectory, "timetable.txt");

    }

    //@@author SebasFok
    /**
     * Takes in the location of the file in question and returns whether the file exist
     *
     * @param filePath location of the file in question
     * @return return true if the file exist,return false otherwise
     */
    public static boolean isFileExist(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    //@@author SebasFok
    /**
     * This method takes in a path and creates a directory at that location. Should the
     * directory already exist, no new directory will be created.
     *
     * @param folderPath the location of where the directory should be created
     */
    public static void createDirectory(String folderPath) {

        File folder = new File(folderPath);
        if (folder.mkdir()) {
            //System.out.println("Folder created successfully.");
        } else {
            //System.out.println("Folder already exists");
        }
    }

    //@@author SebasFok
    /**
     * This method takes in the path of a directory and creates a file 'fileName' in
     * the directory. Should the file already exist, no new file will be created.
     *
     * @param directoryPath the location of the directory where the file should be created
     * @param fileName the name of the file to be created
     */
    public static void createFileInDirectory(String directoryPath, String fileName) {

        // Create the full path to the text file
        String filePath = directoryPath + "/" + fileName;

        File file = new File(filePath);

        try {
            // Create the file
            if (file.createNewFile()) {
                //System.out.println("Text file created successfully at: " + filePath);
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An IOException occurred: " + e.getMessage());
        }
    }
}
