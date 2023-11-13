package seedu.duke.models.schema;

import org.json.simple.JSONObject;

import java.io.IOException;

import static seedu.duke.models.logic.Api.getFullModuleInfo;
import static seedu.duke.utils.errors.HttpError.displaySocketError;

public class Module {
    //module class requires Name
    private String moduleName;
    private String moduleDescription;
    private String moduleCode;
    private int moduleCredits;

    private boolean isModularCreditsLoaded;
    private boolean isCompleted;

    /**
     * Represents a module with the specified module code. This class fetches module information
     * using the NUSMods API and stores details such as the module description, name, credits, and completion status.
     *
     * @param moduleCode The code of the module.
     */
    public Module(String moduleCode) throws RuntimeException{
        if (moduleCode.isEmpty()) {
            throw new NullPointerException();
        }
        this.moduleCode = moduleCode;
        this.moduleCredits = 4;
        this.isModularCreditsLoaded = false;
    }

    /**
     * Marks this module as completed.
     */
    public void markModuleAsCompleted() {
        this.isCompleted = true;
    }

    public boolean getCompletionStatus() {
        return isCompleted;
    }

    //@@author ryanlohyr
    /**
     * Retrieves the modular credits for a module.
     * This method fetches the modular credits for a module by calling the NUSMods API.
     * If the modular credits have already been loaded, it returns the cached value.
     * If not, it makes an API call to get the full module information and extracts the modular credits.
     * In case of a ClassCastException (edge case when module has no credits), a default value of 4 credits is set.
     * Handles IOException by displaying a socket error message.
     *
     * @return The number of modular credits for the module.
     *
     */
    public int getModuleCredits() {
        if(this.isModularCreditsLoaded){
            return this.moduleCredits;
        }
        try {
            JSONObject response = getFullModuleInfo(moduleCode);
            assert response != null: "Response from NUSMods API is null";
            assert !response.isEmpty(): "Response Object is empty";
            this.moduleCredits = Integer.parseInt((String) response.get("moduleCredit"));
            this.isModularCreditsLoaded = true;
        }catch (ClassCastException e){
            System.out.println("Sorry there was issue retrieving the MCs");
            return -1;
        }catch (IOException e){
            displaySocketError();
            return -1;
        }
        return this.moduleCredits;
    }

    //@@author ryanlohyr
    /**
     * Gets the module code.
     *
     * @return The code of this module.
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    //@@author janelleenqi
    /**
     * Checks if this module is equal to another module by comparing their module codes.
     *
     * @param module The module to compare with.
     * @return true if the modules have the same module code, false otherwise.
     */
    public boolean equals(Module module) {
        return this.moduleCode.equals(module.moduleCode);
    }

    //@@author janelleenqi
    /**
     * Generates a string representation of this module, which is its module code.
     *
     * @return The string representation of this module.
     */
    @Override
    public String toString() {
        return this.moduleCode;
    }
}
