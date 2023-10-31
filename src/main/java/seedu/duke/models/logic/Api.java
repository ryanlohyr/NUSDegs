package seedu.duke.models.logic;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.models.schema.ModuleList;

import static seedu.duke.models.logic.DataRepository.getRequirements;


public class Api {

    /**
     * Retrieves the prerequisite tree for a module specified by its code.
     * @author ryanlohyr
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSON object representing the prerequisite tree for the module. The prerequisite tree can be in one of
     *         the following formats:
     *         - If there are no prerequisites, it returns null.
     *         - If there is a single prerequisite, it returns a JSON object with the key "or" containing a list with
     *         the single prerequisite.
     *         - If there are multiple prerequisites, it returns a JSON object representing the full prerequisite tree.
     */
    private static JSONObject getModulePrereqTree(String moduleCode) {
        JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
        if(fullModuleInfo == null){
            return null;
        }
        //prereqTree can be returned as a string(single pre requisite), null(No pre requisites) or object
        Object prereqTree = fullModuleInfo.get("prereqTree");
        if(prereqTree == null){
            return null;
        }else if(prereqTree instanceof String){
            JSONObject jsonObject = new JSONObject();
            ArrayList<String> requirementList = new ArrayList<>();
            requirementList.add((String) prereqTree);
            jsonObject.put("or", requirementList);

            return jsonObject;
        }

        return (JSONObject) fullModuleInfo.get("prereqTree");
    }

    private static boolean isModuleException(String moduleCode){
        ArrayList<String> exemptedModules = new ArrayList<>();
        exemptedModules.add("CS1231");
        exemptedModules.add("MA1508E");
        exemptedModules.add("EE4204");
        exemptedModules.add("MA1511");
        exemptedModules.add("MA1512");
        return exemptedModules.contains(moduleCode);
    }

    private static ArrayList<String> getExemptedPrerequisite(String moduleCode){
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("MA1511");
        list1.add("MA1512");
        map.put("CS1231", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("MA1511");
        list2.add("MA1512");
        map.put("MA1508E", list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("ST2334");
        map.put("EE4204", list3);

        ArrayList<String> list4 = new ArrayList<>();
        map.put("MA1511", list4);

        ArrayList<String> list5 = new ArrayList<>();
        map.put("MA1512", list5);

        return map.get(moduleCode);
    }

    public static JSONObject getFullModuleInfo(String moduleCode) {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            // Will refactor the variable later on, left it for easier readability
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Invalid Module Name");
        } catch (IOException | InterruptedException e) {
            System.out.println("Invalid Module Name");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
        }
        return null;
    }

    public static String getModuleInfoDescription(String moduleCode) {
        JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
        assert fullModuleInfo != null;
        return (String) fullModuleInfo.get("description");
    }

    public static String getModuleName(String moduleCode) {
        JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
        assert fullModuleInfo != null;
        return (String) fullModuleInfo.get("title");
    }

    /**
     * Recursively flattens and processes a list of module prerequisites.
     * @author ryanlohyr
     * @param major The major or program for which prerequisites are being flattened.
     * @param prerequisites An ArrayList to store the flattened prerequisites.
     * @param modulePrereqArray An ArrayList containing the module prerequisites to be processed.
     * @param courseRequirements An ArrayList containing course requirements.
     * @param currRequisite The type of the current prerequisite (e.g., "and" or "or").
     */
    private static void flattenPrereq(
            String major,
            ArrayList<String> prerequisites,
            ArrayList<Objects> modulePrereqArray,
            ArrayList<String> courseRequirements,
            String currRequisite) {
        //base case
        for(Object module: modulePrereqArray){
            if(module instanceof String){
                String formattedModule = ((String) module).replace(":D", "");
                formattedModule = formattedModule.replace("%","");

                if(courseRequirements.contains(formattedModule) ){
                    prerequisites.add(formattedModule);
                    if(currRequisite.equals("or")){
                        return;
                    }
                }
            }else{
                //item is an object
                //here, we determine if its 'or' or 'and'
                JSONObject moduleJSON = (JSONObject) module;
                String key = (String) moduleJSON.keySet().toArray()[0];

                ArrayList<Objects> initial = (ArrayList<Objects>) moduleJSON.get(key);

                flattenPrereq(major, prerequisites, initial, getRequirements(major), key);

            }
        }
    }

    /**
     * Retrieves the prerequisite array for a module specified by its code and also taking into account the degree
     * requirements of the course.
     * @author ryanlohyr
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSONObject representing the prerequisite tree for the module,
     *         or NULL if no prerequisites are specified.
     */
    public static ArrayList<String> getModulePrereqBasedOnCourse(String moduleCode, String major) {
        // Only accepts CEG requirements now
        if(!Objects.equals(major, "CEG")){
            return null;
        }

        //Modules that has prerequisites incorrectly identified by NUSMods
        if(isModuleException(moduleCode)){
            return getExemptedPrerequisite(moduleCode);
        }

        ArrayList<String> prerequisites = new ArrayList<>();

        JSONObject modulePrereqTree = getModulePrereqTree(moduleCode);

        if(modulePrereqTree == null){
            return null;
        }
        String key = (String) modulePrereqTree.keySet().toArray()[0];

        //settle this warning
        ArrayList<Objects> initial = (ArrayList<Objects>) modulePrereqTree.get(key);

        flattenPrereq(major, prerequisites, initial, getRequirements(major), key);

        return prerequisites;

    }

    public static boolean doesModuleExist(String moduleCode) {
        JSONObject moduleInfo = getFullModuleInfo(moduleCode);
        return (!(moduleInfo == null));
    }

    /**
     * Checks if a student satisfies all prerequisites for a given module.
     *
     * @param moduleCode       The code of the module for which prerequisites need to be checked.
     * @param completedModules The list of completed modules by the student.
     * @return `true` if the student satisfies all prerequisites for the module, `false` otherwise.
     * @throws IllegalArgumentException If the module code is invalid.
     */
    public static boolean satisfiesAllPrereq(String moduleCode, ModuleList completedModules)
            throws IllegalArgumentException {

        if (!doesModuleExist(moduleCode)) {
            throw new IllegalArgumentException("Invalid module code");
        }

        JSONObject modulePrereqTree = getModulePrereqTree(moduleCode);

        if(modulePrereqTree == null){
            return true;
        }

        String key = (String) modulePrereqTree.keySet().toArray()[0];
        ArrayList<Objects> initial = (ArrayList<Objects>) modulePrereqTree.get(key);

        //Modules that has prerequisites incorrectly identified by NUSMods
        if(isModuleException(moduleCode)){
            JSONObject exceptionPrereqTree = new JSONObject();
            ArrayList<String> requirementList = getExemptedPrerequisite(moduleCode);
            exceptionPrereqTree.put("and", requirementList);

            key = (String) exceptionPrereqTree.keySet().toArray()[0];
            initial = (ArrayList<Objects>) exceptionPrereqTree.get(key);
        }

        return checkPrereq(initial, key, completedModules);

    }

    /**
     * Recursively checks if each branch of the prereq tree is satisfied by the student.
     *
     * @param modulePrereqArray  The array of prerequisite modules or conditions to be checked.
     * @param currRequisite      The type of prerequisite condition ("or" or "and").
     * @param completedModules   The list of completed modules by the student.
     * @return `true` if the student satisfies all prerequisites, `false` otherwise.
     * @throws InvalidObjectException If the prerequisite information is invalid.
     */
    private static boolean checkPrereq(
            ArrayList<Objects> modulePrereqArray,
            String currRequisite,
            ModuleList completedModules) {

        if (currRequisite.equals("or")) {
            for(Object module: modulePrereqArray) {
                if (module instanceof String) {
                    String formattedModule = ((String) module).replace(":D", "");
                    formattedModule = formattedModule.replace("%", "");
                    try {
                        if (completedModules.exists(formattedModule)) {
                            return true;
                        }
                    } catch (InvalidObjectException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    JSONObject prereqBranch = (JSONObject) module;
                    String key = (String) prereqBranch.keySet().toArray()[0];


                    JSONArray prereqBranchArray = (JSONArray) prereqBranch.get(key);
                    return checkPrereq(prereqBranchArray, currRequisite, completedModules);
                }
            }
            return false;
        } else {
            for(Object module: modulePrereqArray) {
                if (module instanceof String) {
                    String formattedModule = ((String) module).replace(":D", "");
                    formattedModule = formattedModule.replace("%", "");
                    try {
                        if (!completedModules.exists(formattedModule)) {
                            return false;
                        }
                    } catch (InvalidObjectException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    JSONObject prereqBranch = (JSONObject) module;
                    String key = (String) prereqBranch.keySet().toArray()[0];


                    JSONArray prereqBranchArray = (JSONArray) prereqBranch.get(key);
                    if (!checkPrereq(prereqBranchArray, currRequisite, completedModules)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
