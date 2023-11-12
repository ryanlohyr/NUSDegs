package seedu.duke.utils;

import seedu.duke.models.schema.Storage;
import seedu.duke.models.schema.Student;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static seedu.duke.utils.errors.HttpError.displaySocketError;
import static seedu.duke.views.Ui.displayGoodbye;

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

    public static void saveStudentData(Storage storage, Student student) {
        try {
            storage.saveStudentDetails(student);
            Storage.saveSchedule(student);
            Storage.saveTimetable(student);
            System.out.println("Data successfully saved in save file");
        } catch (IOException e) {
            System.out.println("Unable to save data.");
        }
    }

    /*Sebestians version
    private static boolean isInternetReachable() {
        try {
            // Try connecting to a well-known server (Google's DNS server)
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(3000); // 3 seconds timeout
        } catch (java.io.IOException e) {
            return false; // Unable to connect
        }
    }
    */
}
