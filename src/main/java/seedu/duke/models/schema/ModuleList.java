package seedu.duke.models.schema;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static seedu.duke.views.ModuleInfoView.printModuleArray;


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
        mainModuleList = new ArrayList<Module>();
        if (modules == null || modules.isEmpty()) {
            return;
        }
        try {
            String[] moduleArray = modules.split(" ");
            //ArrayList<String> moduleCodes = new ArrayList<String>();

            //numberOfModules = 0;
            for (String module : moduleArray) {
                try {
                    mainModuleList.add(new Module(module));
                    numberOfModules += 1;

                } catch (NullPointerException e) {
                    System.out.println("null pointer");
                    //fail
                }

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

    public ArrayList<String> getModulesPlanned(){
        ArrayList<String> completedModuleCodes = new ArrayList<>();
        for (Module module: mainModuleList){
            completedModuleCodes.add(module.getModuleCode());
        }
        return completedModuleCodes;
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
        printModuleArray(mainModuleList);
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


    public ArrayList<String> getModuleCodes() {
        ArrayList<String> moduleCodes = new ArrayList<>();
        for (Module module: mainModuleList){
            moduleCodes.add(module.getModuleCode());
        }
        return moduleCodes;
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
}
