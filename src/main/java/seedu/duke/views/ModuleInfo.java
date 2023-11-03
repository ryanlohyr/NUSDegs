package seedu.duke.views;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ModuleInfo {

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

}
