package seedu.duke.views;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.duke.models.schema.Module;

import java.util.ArrayList;

public class ModuleInfoView {

    public static void print(String output) {
        System.out.println(output);
    }

    public static void printLine() {
        System.out.println("_________________________________________");
    }
    public static void printJsonArray(JSONArray modules) {
        for (Object module: modules) {
            JSONObject castedModule = (JSONObject) module;
            print("Title: " + (String)castedModule.get("title"));
            print("Module Code: " + (String)castedModule.get("moduleCode"));
            printLine();
        }
    }

    public static void searchHeader() {
        printLine();
        print("These are the modules that contain your keyword in the title:");
        print("");
    }

    /**
     * Print a list of modules in columns with a specified maximum height.
     * @author ryanlohyr
     * @param modules       An ArrayList of module names to be printed.
     */
    public static void printModuleStringArray(ArrayList<String> modules){
        int maxColumnHeight = 5;

        for (int i = 0; i < modules.size(); i += maxColumnHeight) {
            for (int j = 0; j < maxColumnHeight && i + j < modules.size(); j++) {
                String module = (i + j + 1) + ". " + modules.get(i + j);
                System.out.printf("%-15s", module);
            }
            System.out.println();
        }
    }

    /**
     * Print a list of modules in columns with a specified maximum height.
     * @author ryanlohyr
     * @param modules       An ArrayList of module names to be printed.
     */
    public static void printModuleArray(ArrayList<Module> modules){
        int maxColumnHeight = 5;

        for (int i = 0; i < modules.size(); i += maxColumnHeight) {
            for (int j = 0; j < maxColumnHeight && i + j < modules.size(); j++) {
                String module = (i + j + 1) + ". " + modules.get(i + j);
                System.out.printf("%-15s", module);
            }
            System.out.println();
        }
    }

}
