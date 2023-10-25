package seedu.duke.models;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class ModuleList {

    private ArrayList<String> mainModuleList;
    private int numberOfModules;

    public ModuleList(String modules) {
        try {
            String[] moduleArray = modules.split(" ");
            mainModuleList = new ArrayList<String>();
            numberOfModules = 0;
            for (String module : moduleArray) {
                mainModuleList.add(module);
                numberOfModules += 1;
            }
        } catch (NullPointerException e) {
            new ModuleList();
        }
    }

    public ModuleList() {
        mainModuleList = new ArrayList<String>();
        numberOfModules = 0;
    }

    /**
     * Computes the difference between two ModuleList objects (A - B) and updates the current list.
     *
     * @param a The first ModuleList.
     * @param b The second ModuleList.
     * @throws InvalidObjectException If either A or B is null.
     */
    public void getDifference (ModuleList a, ModuleList b) throws InvalidObjectException {
        //A - B
        if (a == null || b == null) {
            throw new InvalidObjectException("Null Inputs");
        }
        mainModuleList.clear();

        for (String moduleA : a.mainModuleList) {
            try {
                if (!b.exists(moduleA)) {
                    mainModuleList.add(moduleA);
                    numberOfModules += 1;
                }
            } catch (InvalidObjectException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Checks if a module exists in the list.
     *
     * @param moduleA The module to check for existence.
     * @return true if the module exists in the list; false otherwise.
     * @throws InvalidObjectException If moduleA is null.
     */
    public boolean exists(String moduleA) throws InvalidObjectException {
        if (moduleA == null || mainModuleList == null) {
            throw new InvalidObjectException("Null Inputs");
        }
        for (String moduleB : mainModuleList) {
            if (moduleA.equals(moduleB)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the list of modules.
     *
     * @return The ArrayList containing the modules.
     */
    public ArrayList<String> getMainModuleList() {
        return mainModuleList;
    }

    public void printMainModuleList(){
        for (String mod: mainModuleList){
            System.out.print(mod + " ");
        }
        System.out.println();
    }

    /**
     * Retrieves the number of modules.
     *
     * @return The number of modules.
     */
    public int getNumberOfModules() {
        return numberOfModules;
    }
}
