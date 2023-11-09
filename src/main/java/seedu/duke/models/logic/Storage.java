package seedu.duke.models.logic;

import seedu.duke.models.schema.ModuleList;
import seedu.duke.models.schema.Schedule;
import seedu.duke.models.schema.Student;
import seedu.duke.utils.exceptions.CorruptedFileException;
import seedu.duke.utils.exceptions.MissingFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {

    private String userName;

    private String userDirectory = System.getProperty("user.dir");

    /**
     * Constructs a new Storage instance with the specified file path.
     *
     * @param userName The path to the storage file.
     */
    public Storage(String userName) {
        this.userName = userName;
    }

    /**
     * Creates a "schedule.txt" file in the storage directory.
     */
    public void createUserStorageFile() {
        String storageDirectory = userDirectory + "/" + userName;
        createDirectory(storageDirectory);
        createFileInDirectory(storageDirectory, "schedule.txt");
        //createFileInDirectory(storageDirectory, "timetable.txt");
    }



    public Schedule loadSchedule() throws MissingFileException, CorruptedFileException {

        String scheduleFilePath = userDirectory + "/" + userName + "/" + "schedule.txt";

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
            int[] modulesPerSemArray = new int[] {0,0,0,0,0,0,0,0};

            // Read lines from the file and add them to the ArrayList.
            while ((line = bufferedReader.readLine()) != null) {

                String[] splitParts = line.split(" \\| ");

                switch(splitParts[0]) {

                case "ModulesPerSem":
                    String[] modulesPerSemStringArray = splitParts[1].split(",");
                    for (int i = 0; i < modulesPerSemArray.length; i++) {
                        modulesPerSemArray[i] = Integer.parseInt(modulesPerSemStringArray[i]);
                    }
                    break;
                case "Module":
                    String module = splitParts[1];
                    int targetSemester = 1;
                    int indexOfLastModuleOfSem = 0;
                    while (targetIndex > indexOfLastModuleOfSem) {
                        indexOfLastModuleOfSem += modulesPerSemArray[targetSemester - 1];
                        targetSemester += 1;
                    }

                    schedule.addModule(module, targetSemester);
                    if (splitParts[2].equals("O")) {
                        schedule.getModule(module).markModuleAsCompleted();
                    }
                    targetIndex += 1;
                    break;
                default:
                }
            }

            // Close the BufferedReader to release resources.
            bufferedReader.close();
        } catch (Exception e) {
            throw new CorruptedFileException();
        }

        return schedule;

    }

    public void saveSchedule(Student student) throws IOException {

        String scheduleFilePath = userDirectory + "/" + userName + "/" + "schedule.txt";

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

















    /**
     * Takes in the location of the file in question and returns whether the file exist
     *
     * @param filePath
     * @return return true if the file exist,return false otherwise
     */
    public static boolean isFileExist(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    /**
     * This method takes in a path and creates a directory at that location. Should the
     * directory already exist, no new directory will be created.
     *
     * @param folderPath the location of where the directory should be created
     */
    public static void createDirectory(String folderPath) {

        File folder = new File(folderPath);
        if (folder.mkdir()) {
            System.out.println("Folder created successfully.");
        } else {
            System.out.println("Folder already exists");
        }
    }

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
                System.out.println("Text file created successfully at: " + filePath);
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An IOException occurred: " + e.getMessage());
        }
    }

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
