package seedu.duke;

import java.util.ArrayList;
import java.util.Objects;

public class ModuleList {

    private ArrayList<String> mainModuleList;
    public int numberOfModules;

    public ModuleList(String modules) {
        String[] moduleArray = modules.split(" ");
        mainModuleList = new ArrayList<String>();
        numberOfModules = 0;
        for (String module: moduleArray) {
            mainModuleList.add(module);
            numberOfModules += 1;
        }
    }

    public ModuleList() {
        mainModuleList = new ArrayList<String>();
        numberOfModules = 0;
    }

    public void getDifference (ModuleList A, ModuleList B) {
        //A - B
        //mainModuleList should be empty
        for (String moduleA: A.mainModuleList) {
            if (!B.exists(moduleA)) {
                mainModuleList.add(moduleA);
                numberOfModules += 1;
            }
        }
    }

    public boolean exists(String moduleA) {
        for (String moduleB: mainModuleList) {
            if (moduleA.equals(moduleB)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getMainModuleList() {
        return mainModuleList;
    }
}
