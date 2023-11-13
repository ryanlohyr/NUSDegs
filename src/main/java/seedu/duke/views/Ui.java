package seedu.duke.views;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.InputStream;

/**
 * Utility class for displaying messages and graphics to welcome and interact with CS and CEG students.
 * This class provides static methods for displaying welcome messages, logos, and handling user input.
 * It is designed for use in applications related to Computer Science (CS) and Computer Engineering (CEG).
 *
 */
public class Ui {
    private static Thread loadingThread;
    private static final String DIVIDER = "___________________________________________________________";
    private static final String DIVIDERWithoutPadding = "___________________________________________________________";
    private final Scanner in;
    private final PrintStream out;

    public Ui() {
        this(System.in, System.out);
    }

    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Display a message to the command line view.
     *
     * @param o The object to be displayed.
     */
    public static void displayMessage(Object o) {
        System.out.println(o);
    }

    //@@author ryanlohyr
    /**
     * Displays a welcome message to CS and CEG students.
     * This method prints a welcome message and a logo to the console, providing a friendly greeting
     * to Computer Science (CS) and Computer Engineering (CEG) students using the application.
     */
    public static void displayWelcome(){

        String logo = "  _   _ _   _ ____  ____                 \n"
                + " | \\ | | | | / ___||  _ \\  ___  __ _ ___ \n"
                + " |  \\| | | | \\___ \\| | | |/ _ \\/ _` / __|\n"
                + " | |\\  | |_| |___) | |_| |  __/ (_| \\__ \\\n"
                + " |_| \\_|\\___/|____/|____/ \\___|\\__, |___/\n"
                + "                               |___/     ";

        System.out.println("Hey there CS and CEG Students! Welcome to ");
        System.out.println(logo);
    }

    /**
     * Displays a farewell message to the user.
     * This method prints a goodbye message to the console, indicating the end of the application or interaction.
     * It is typically used when the user exits or completes a session with the application.
     */
    public static void displayGoodbye(){
        System.out.println("Goodbye!");
    }


    //@@author ryanlohyr
    /**
     * Prompts the user with a message and retrieves a command from the console.
     * This method displays a prompt message to the user, reads a command from the console,
     * and returns the entered command as a String.
     * @param toDisplay The message to display as a prompt before reading the user's input.
     * @return A String representing the command entered by the user.
     */
    public String getUserCommand(String toDisplay) {
        out.println(DIVIDERWithoutPadding);
        out.print(toDisplay);
        return in.nextLine();
    }

    //@@author ryanlohyr
    /**
     * Prints one or more messages surrounded by a divider.
     * This method prints messages to the console, each on a new line, surrounded by a divider.
     * It is a utility method for displaying information or messages in a formatted way.
     *
     * @param messages The messages to be printed. Each message is printed on a new line.
     */
    public void printMessage(String... messages) {
        out.println();
        out.println(DIVIDER);
        for (String m : messages) {
            out.println(m);
        }
        out.println(DIVIDER);
    }


    //@@author ryanlohyr
    /**
     * Prints an error message related to data storage issues.
     * This method prints an error message indicating that there is an issue with retrieving data.
     * It is typically used when no save file exists, or the existing save file is corrupted.
     * Users are instructed to continue using the application to create a new save file or overwrite
     * the corrupted file.
     *
     */
    public void printStorageError() {
        out.println();
        out.println(DIVIDER);
        System.out.println("Unable to retrieve any data. Your save file may be corrupted.\n" +
                "Please continue using the application to create new save files or overwrite " +
                "the corrupted files!");
        out.println("Please check ./data again");
        stopLoadingAnimation();
    }

    //@@author ryanlohyr
    /**
     * Displays a loading animation in the console.
     * This method creates a new thread that prints a loading animation sequence to the console.
     * The animation consists of a series of characters that change to give the appearance of movement.
     * The loading animation runs until the thread is interrupted.
     * Note: This method assumes that the console supports carriage return ("\r") for updating the loading animation.
     * Example animationChars: {"(.O_O.)", "(.o_o.)", "(.<_<.)", "(.^_^.)"}
     *
     *
     */
    public static void showLoadingAnimation() {
        String[] animationChars = {"(.O_O.)","(.o_o.)","(.<_<.)","(.>_>.)","(.>_<.)","(.^_^.)"};
        loadingThread = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Loading " + animationChars[i % 6] + "\r");
                i++;
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("\n");
        });
        loadingThread.start();
    }

    //@@author ryanlohyr
    /**
     * Stops the loading animation thread if it is currently running.
     * This method checks if the loading animation thread is active and interrupts it if so.
     * It is used to halt any ongoing loading animation gracefully.
     * Note: This method assumes that the loadingThread variable is appropriately managed.
     * If the loadingThread is null or not alive, no action is taken.
     */
    public static void stopLoadingAnimation() {
        if (loadingThread != null && loadingThread.isAlive()) {
            loadingThread.interrupt();
            try {
                // Wait for the loading thread to finish
                loadingThread.join();
            } catch (InterruptedException e) {
                // Handle the interruption if needed
                Thread.currentThread().interrupt();  // Restore interrupted status
            }
        }
    }
}
