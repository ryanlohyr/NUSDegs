package seedu.duke.models.logic;

import seedu.duke.models.schema.ModuleList;
import seedu.duke.views.CommandLineView;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class ModulesLeft {
    private CommandLineView view;
    private ModuleList modulesMajor;
    private ModuleList modulesTaken;

    public ModulesLeft(ModuleList modulesMajor, ModuleList modulesTaken) {
        this.view = new CommandLineView();
        this.modulesMajor = modulesMajor;
        this.modulesTaken = modulesTaken;
    }


    /**
     * Computes and returns the list of modules that are left in the ModuleList modulesMajor
     * after subtracting the modules in the ModuleList modulesTaken.
     *
     * @author janelleenqi
     * @return An ArrayList of module codes representing the modules left after the subtraction.
     *
     */
    public ArrayList<String> listModulesLeft() {
        ModuleList modulesLeft = new ModuleList();
        try {
            modulesLeft.getDifference(modulesMajor, modulesTaken);
            return modulesLeft.getMainModuleList();
        } catch (InvalidObjectException e) {
            view.displayMessage("Error: " + e.getMessage());
        }
        return null;
    }
}
