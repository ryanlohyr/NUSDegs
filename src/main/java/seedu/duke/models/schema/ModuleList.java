package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ModuleList newModuleListOfCompleted(){
        ModuleList newModuleList = new ModuleList();
        for (Module module: mainModuleList){
            if (module.getCompletionStatus()) {
                newModuleList.addModule(module);
            }
        }
        return newModuleList;
    }

    public HashMap<String, Module> newHashMapOfCompleted(){
        HashMap<String, Module> completedModules = new HashMap<String, Module>();
        for (Module module: mainModuleList){
            if (module.getCompletionStatus()) {
                completedModules.put(module.getModuleCode(), module);
            }
        }
        return completedModules;
    }

    public void addModule (Module module) {
        mainModuleList.add(module);
    }

    public void addModule (int index, Module module) {
        mainModuleList.add(index, module);
    }

    public void replaceModule (int index, Module module) {
        mainModuleList.set(index, module);
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

    public boolean exists(Module module) {
        if (mainModuleList == null) {
            return false;
        }

        assert module != null;

        for (Module moduleB : mainModuleList) {
            if (module.equals(moduleB)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a module with the specified module code exists in the main module list.
     *
     * This method verifies the existence of a module in the main module list based on its module code.
     * It returns true if a module with the provided module code is found in the list, and false if it
     * does not exist. The method also throws an InvalidObjectException if the main module list or the
     * module code is null.
     *
     * @param moduleCodeA The module code to check for in the main module list.
     * @return true if a module with the specified module code exists, false otherwise.
     * @throws InvalidObjectException If the main module list is null or the provided module code is null.
     */
    public boolean existsByCode(String moduleCodeA) throws InvalidObjectException {
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

    public Module getModule(Module module) throws InvalidObjectException {
        for (Module currentModule: mainModuleList) {
            if (currentModule.equals(module)) {
                return currentModule;
            }
        }
        throw new InvalidObjectException("Module does not exist.");
    }

    public int size() {
        return mainModuleList.size();
    }

    public int getIndex(Module module) {
        return mainModuleList.indexOf(module);
    }

    /**
     * Finds the index of a module in the main module list by its module code.
     *
     * This method searches for a module in the main module list based on its module code.
     * If the module code is found in the list, it returns the index at which the module
     * is located. If the module code is not found, it returns -1 to indicate that the module
     * is not present in the list.
     *
     * @param moduleCode The module code to search for.
     * @return The index of the module in the list if found, or -1 if not found.
     */
    public int getIndexByString(String moduleCode) {
        int i = 0;
        for (Module module: mainModuleList){
            if (moduleCode.equals(module.getModuleCode())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Module getModuleByIndex(int index) {
        return this.mainModuleList.get(index);
    }

    public void printMainModuleList(){
        printModuleArray(mainModuleList);
    }
}
