package seedu.duke;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class ModuleList {

    private ArrayList<String> mainModuleList;
    public int numberOfModules;

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

    public void getDifference (ModuleList A, ModuleList B) throws InvalidObjectException {
        //A - B
        if (A == null || B == null) {
            throw new InvalidObjectException("Null Inputs");
        }
        mainModuleList.clear();
        if (!A.mainModuleList.isEmpty()) {
            for (String moduleA : A.mainModuleList) {
                try {
                    if (!B.exists(moduleA)) {
                        mainModuleList.add(moduleA);
                        numberOfModules += 1;
                    }
                } catch (InvalidObjectException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public boolean exists(String moduleA) throws InvalidObjectException {
        if (moduleA == null) {
            throw new InvalidObjectException("Null Input");
        }
        if (!mainModuleList.isEmpty()) {
            for (String moduleB : mainModuleList) {
                if (moduleA.equals(moduleB)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getMainModuleList() {
        return mainModuleList;
    }
}
