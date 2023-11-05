package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.views.ModuleInfoView.printModuleArray;

/**
 * A class representing a list of modules and providing operations to manage them.
 */
public class ModuleList {
    private ArrayList<Module> mainModuleList;

    /**
     * Constructs an empty ModuleList.
     */
    public ModuleList() {
        mainModuleList = new ArrayList<Module>();
    }

    /**
     * Constructs a ModuleList from a space-separated string of modules.
     *
     * @param modules A space-separated string of module codes.
     */

    public ModuleList(String modules) {
        this();

        if (modules == null || modules.isEmpty()) {
            return;
        }

        String[] moduleArray = modules.split(" ");

        for (String module : moduleArray) {
            try {
                mainModuleList.add(new Module(module));

            } catch (NullPointerException e) {
                System.out.println("null pointer");
                //fail
            }
        }
    }

    public ModuleList(ArrayList<String> Modules) {
        this();

        for (String module: Modules) {
            try {
                mainModuleList.add(new Module(module));

            } catch (NullPointerException e) {
                assert false;
            }
        }
    }

    /**
     * Retrieves the list of modules.
     *
     * @author janelleenqi
     * @return The ArrayList containing the modules.
     */
    public ArrayList<Module> getMainModuleList() {
        assert mainModuleList != null: "null mainModuleList";
        return mainModuleList;
    }

    public ArrayList<String> getModuleCodes() {
        ArrayList<String> moduleCodes = new ArrayList<>();
        for (Module module: mainModuleList){
            moduleCodes.add(module.getModuleCode());
        }
        return moduleCodes;
    }

    public ArrayList<String> getModulesCompleted(){
        ArrayList<String> completedModuleCodes = new ArrayList<>();
        for (Module module: mainModuleList){
            if (module.getCompletionStatus()) {
                completedModuleCodes.add(module.getModuleCode());
            }
        }
        return completedModuleCodes;
    }

    public void addModule (Module module) {
        mainModuleList.add(module);
    }

    public void addModule (int index, Module module) {
        mainModuleList.add(index, module);
    }


    public void deleteModule (Module module) {
        mainModuleList.remove(module);
    }

    public void deleteModulebyCode (String moduleCode) {
        for (Module module: mainModuleList){
            if (moduleCode.equals(module.getModuleCode())) {
                deleteModule(module);
                return;
            }
        }
    }

    public boolean exists(String moduleCodeA) throws InvalidObjectException {
        if (mainModuleList == null) {
            throw new InvalidObjectException("Null Module List");
        }

        if (moduleCodeA == null) {
            throw new InvalidObjectException("Null Module Code");
        }

        for (Module moduleB : mainModuleList) {
            if (moduleCodeA.equals(moduleB.getModuleCode())) {
                return true;
            }
        }
        return false;
    }


    public Module getModule(String moduleCode) throws InvalidObjectException {
        for (Module module: mainModuleList) {
            if (moduleCode.equals(module.getModuleCode())) {
                return module;
            }
        }
        throw new InvalidObjectException("Module does not exist.");
    }


    public int getIndex(String moduleCode) {
        int i = 0;
        for (Module module: mainModuleList){
            if (moduleCode.equals(module.getModuleCode())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void printMainModuleList(){
        printModuleArray(mainModuleList);
    }
}
