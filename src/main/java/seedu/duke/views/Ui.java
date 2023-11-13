package seedu.duke.views;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.InputStream;

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

    public static void displayGoodbye(){
        System.out.println("Goodbye!");
    }

    public String getUserCommand(String toDisplay) {
        out.println(DIVIDERWithoutPadding);
        out.print(toDisplay);
        return in.nextLine();
    }

    public void printMessage(String... messages) {
        out.println();
        out.println(DIVIDER);
        for (String m : messages) {
            out.println(m);
        }
        out.println(DIVIDER);
    }


    public void printStorageError() {
        out.println();
        out.println(DIVIDER);
        System.out.println("Unable to retrieve any data. Your save file may be corrupted.\n" +
                "Please continue using the application to create a new save file or overwrite " +
                "the corrupted file!");
        out.print("Please check ./data again");
        stopLoadingAnimation();
    }

    public static void showLoadingAnimation() {
        String[] animationChars = {"(.O_O.)","(.o_o.)","(.<_<.)","(.^_^.)"};
        loadingThread = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Loading " + animationChars[i % 4] + "\r");
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

    public static void stopLoadingAnimation() {
        if (loadingThread != null && loadingThread.isAlive()) {
            loadingThread.interrupt();
        }
    }
}
