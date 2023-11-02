package seedu.duke.models.schema;

import org.json.simple.JSONObject;

import static seedu.duke.models.logic.Api.getFullModuleInfo;

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
    public Module(String moduleCode){
        //add wtv info u want...
        JSONObject response = getFullModuleInfo(moduleCode);
        assert response != null: "Response from NUSMods API is null";
        this.moduleCode = moduleCode;
        this.isCompleted = false;
        this.moduleDescription = (String) response.get("description");
        this.moduleName = (String) response.get("title");
        this.moduleCredits = (int) response.get("moduleCredit");
    }

    /**
     * Marks this module as completed.
     */
    public void markModuleAsCompleted() {
        this.isCompleted = true;
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






}
