package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.models.logic.DataRepository.getRequirements;

/**
 * A class representing a list of modules and providing operations to manage them.
 */
public class ModuleList {

    private ArrayList<Module> mainModuleList;
    private int numberOfModules;



    /**
     * Constructs a ModuleList from a space-separated string of modules.
     *
     * @param modules A space-separated string of module codes.
     */

    public ModuleList(String modules) {
        try {
            String[] moduleArray = modules.split(" ");
            //ArrayList<String> moduleCodes = new ArrayList<String>();

            //numberOfModules = 0;
            for (String module : moduleArray) {
                mainModuleList.add(module);
                numberOfModules += 1;
            }
        } catch (NullPointerException e) {
            new ModuleList();
        }
    }



    /**
     * Constructs an empty ModuleList.
     */
    public ModuleList() {
        mainModuleList = new ArrayList<Module>();
        numberOfModules = 0;
    }

    public void addModule (Module module) {
        mainModuleList.add(module);
    }

    public void deleteModule (Module module) {
        mainModuleList.remove(module);
    }

    /**
     * Checks if a module exists in the list.
     *
     * @author janelleenqi
     * @param moduleA The module to check for existence.
     * @return true if the module exists in the list; false otherwise.
     * @throws InvalidObjectException If moduleA is null.
     */
    /*
    public boolean exists(Module moduleA) throws InvalidObjectException {
        if (moduleA == null || mainModuleList == null) {
            throw new InvalidObjectException("Null Inputs");
        }

        for (Module moduleB : mainModuleList) {
            if (moduleA.equals(moduleB)) {
                return true;
            }
        }
        return false;
    }
     */

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

    public Module getModule(String moduleCode) throws InvalidObjectException {
        for (Module module: mainModuleList) {
            if (moduleCode.equals(module.getModuleCode())) {
                return module;
            }
        }
        throw new InvalidObjectException("Module does not exist.");
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

    public void printMainModuleList(){
        for (Module module: mainModuleList){
            System.out.print(module + " ");
        }
        System.out.println();
    }

    /**
     * Retrieves the number of modules.
     *
     * @return The number of modules.
     */
    public int getNumberOfModules() {
        assert numberOfModules >= 0: "negative numberOfModules";
        return numberOfModules;
    }

    /**
     * Changes the number of modules by the specified difference.
     *
     * @param difference The difference by which to change the number of modules.
     *                   A positive value increases the number, while a negative value decreases it.
     */
    public void changeNumberOfModules(int difference) {
        numberOfModules += difference;
    }


}
