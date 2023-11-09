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
    private boolean isCompleted;

    /**
     * Represents a module with the specified module code. This class fetches module information
     * using the NUSMods API and stores details such as the module description, name, credits, and completion status.
     *
     * @param moduleCode The code of the module.
     */
    public Module(String moduleCode) throws NullPointerException, RuntimeException{
        //add wtv info u want...
        if (moduleCode.isEmpty()) {
            throw new NullPointerException();
        }
        this.moduleCode = moduleCode;
        this.moduleCredits = 4;
    }

    /**
     * Loads module information from the NUSMods API and populates the fields of this object.
     *
     * This method sends a request to the NUSMods API to retrieve module information and
     * then populates the fields of the current object with the received data. The fields
     * include module description, module name, module credits, and completion status.
     *
     *
     */
    public void loadNUSModsAPI(){
        try {
            JSONObject response = getFullModuleInfo(moduleCode);
            assert response != null: "Response from NUSMods API is null";
            assert !response.isEmpty(): "Response Object is empty";

            this.isCompleted = false;
            this.moduleDescription = (String) response.get("description");
            this.moduleName = (String) response.get("title");
            this.moduleCredits = (Integer) response.get("moduleCredit");
        }catch (ClassCastException e){
            this.moduleCredits = 4;
        }catch (IOException e){
            displaySocketError();
        }
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

    /**
     * Gets the credits of this module.
     *
     * @return The number of credits for this module.
     */
    public int getModuleCredits() {
        return this.moduleCredits;
    }

    /**
     * Gets the module code.
     *
     * @return The code of this module.
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    public boolean equals(Module module) {
        return this.moduleCode.equals(module.moduleCode);
    }


    @Override
    public String toString() {
        return this.moduleCode;
    }


}
