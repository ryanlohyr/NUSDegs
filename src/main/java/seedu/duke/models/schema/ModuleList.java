package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;

import static seedu.duke.views.ModuleInfoView.printModuleArray;

/**
 * The ModuleList class represents a list of modules and provides various methods for managing modules.
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
     * Constructs a ModuleList from a space-separated string of moduleCodes.
     *
     * @param moduleCodes A space-separated string of module codes.
     */

    public ModuleList(String moduleCodes) {
        this();
        if (moduleCodes == null || moduleCodes.isEmpty()) {
            return;
        }

        String[] moduleArray = moduleCodes.split(" ");

        for (String moduleCode : moduleArray) {
            try {
                mainModuleList.add(new Module(moduleCode));

            } catch (NullPointerException e) {
                //fail
                System.out.println("null pointer");
            }
        }
    }

    /**
     * Retrieves the list of modules.
     *
     * @return The ArrayList containing the modules.
     */
    public ArrayList<Module> getMainModuleList() {
        assert mainModuleList != null: "null mainModuleList";
        return mainModuleList;
    }

    /**
     * Retrieves the list of module codes from the modules in the mainModuleList.
     *
     * @return The ArrayList containing module codes.
     */
    public ArrayList<String> getModuleCodes() {
        ArrayList<String> moduleCodes = new ArrayList<>();
        for (Module module: mainModuleList){
            moduleCodes.add(module.getModuleCode());
        }
        return moduleCodes;
    }

    /**
     * Retrieves the list of module codes for completed modules from the main module list.
     *
     * @return The ArrayList containing module codes of completed modules.
     */
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

    /**
     * Creates and returns a new HashMap containing completed modules from the main module list, indexed by module code.
     *
     * @return A HashMap containing completed modules indexed by module code.
     */
    public HashMap<String, Module> newHashMapOfCompleted(){
        HashMap<String, Module> completedModules = new HashMap<String, Module>();

        // add modules to HashMap<String, Module> for easy retrieval
        for (Module module: mainModuleList){
            if (module.getCompletionStatus()) {
                completedModules.put(module.getModuleCode(), module);
            }
        }
        return completedModules;
    }

    /**
     * Adds a module to the main module list.
     *
     * @param module The module to be added.
     */
    public void addModule (Module module) {
        mainModuleList.add(module);
    }

    /**
     * Adds a module to the main module list at the specified index.
     *
     * @param index  The index at which the module should be added.
     * @param module The module to be added.
     */
    public void addModule (int index, Module module) {
        mainModuleList.add(index, module);
    }

    public void replaceModule (int index, Module module) {
        mainModuleList.set(index, module);
    }

    /**
     * Deletes a module from the main module list.
     *
     * @param module The module to be deleted.
     */
    public void deleteModule (Module module) {
        mainModuleList.remove(module);
    }

    /**
     * Deletes a module from the main module list by its module code.
     *
     * This method attempts to retrieve the module with the specified module code and then deletes
     * it from the main module list if found. If the module does not exist or if an exception is
     * thrown during the process, the method returns without making any changes to the ModuleList.
     *
     * @param moduleCode The module code of the module to be deleted.
     */
    public void deleteModuleByCode (String moduleCode) {
        try {
            Module moduleToBeDeleted = getModule(moduleCode);
            deleteModule(moduleToBeDeleted);
        } catch (InvalidObjectException e) {
            return;
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

    /**
     * Retrieves a module from the main module list by its module code.
     *
     * @param moduleCode The module code to search for.
     * @return The module with the specified module code.
     * @throws InvalidObjectException If the module does not exist.
     */
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

    /**
     * Retrieves a module from the main module list by its index.
     *
     * @param index The index of the module to retrieve.
     * @return The module at the specified index.
     */
    public Module getModuleByIndex(int index) {
        return this.mainModuleList.get(index);
    }

    public void printMainModuleList(){
        printModuleArray(mainModuleList);
    }
}
