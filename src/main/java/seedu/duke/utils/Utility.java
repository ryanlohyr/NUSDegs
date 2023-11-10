package seedu.duke.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
