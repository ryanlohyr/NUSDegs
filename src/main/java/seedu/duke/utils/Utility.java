package seedu.duke.utils;

import seedu.duke.storage.StorageManager;
import seedu.duke.models.schema.Student;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static seedu.duke.utils.errors.HttpError.displaySocketError;
import static seedu.duke.views.Ui.displayGoodbye;

/**
 * Utility class containing reusable functions for various tasks.
 * This class provides a set of static methods that can be used across different parts of an application.
 * The methods cover tasks such as checking internet reachability, handling internet connection detection,
 * and saving student data to storage.
 *
 * Note: Some methods in this class may throw IOException in case of errors, and appropriate error
 * messages are displayed to the console.
 */
public class Utility {
    public static boolean isInternetReachable() {
        try (Socket socket = new Socket()) {
            // Try connecting to Google's DNS server on port 53 (DNS)
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 3000); // 3 seconds timeout
            return true; // Connection successful
        } catch (IOException e) {
            return false; // Unable to connect
        }
    }

    public static void detectInternet() throws IOException {
        if (!Utility.isInternetReachable()) {
            displaySocketError();
            displayGoodbye();
            throw new IOException();
        }
    }

    public static void saveStudentData(StorageManager storage, Student student) {
        try {
            storage.saveStudentDetails(student);
            StorageManager.saveSchedule(student);
            StorageManager.saveTimetable(student);
            System.out.println("Data successfully saved in save files");
        } catch (IOException e) {
            System.out.println("Unable to save data.");
        }
    }
}
