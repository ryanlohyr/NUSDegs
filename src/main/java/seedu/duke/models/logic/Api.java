package seedu.duke.models.logic;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static seedu.duke.models.logic.DataRepository.getRequirements;
import seedu.duke.views.UnknownCommandException;


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

    public static String getDescription(String moduleCode) {
        JSONObject moduleInfo = getFullModuleInfo(moduleCode);
        assert moduleInfo != null;
        return (String) moduleInfo.get("description");
    }

    public static JSONArray getWorkload(String moduleCode) {
        JSONObject moduleInfo = getFullModuleInfo(moduleCode);
        assert moduleInfo != null;
        return (JSONArray) moduleInfo.get("workload");
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

    public static JSONArray listAllModules() {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/moduleList.json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            // Will refactor the variable later on, left it for easier readability
            JSONArray moduleList = (JSONArray) parser.parse(responseBody);
            // System.out.println(moduleList);
            return moduleList;
            // find a way to pretty print this
        } catch (URISyntaxException e) {
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, the JSON object could not be parsed");
        }
        return null;
    }
    public static JSONArray search(String keyword, JSONArray moduleList) {
        JSONArray modulesContainingKeyword = new JSONArray();
        if (keyword.isEmpty()) {
            return new JSONArray();
        }
        for (Object moduleObject : moduleList) {
            JSONObject module = (JSONObject) moduleObject; // Cast to JSONObject
            String title = (String) module.get("title");
            if (title.contains(keyword)) {
                modulesContainingKeyword.add(module);
                //not sure how to resolve this yellow line
            }
        }
        return modulesContainingKeyword;
    }

    public static void infoCommands(String command, String userInput) throws UnknownCommandException {
        // checks if command is even equal to any of these words, if equal nothing then return go fk yourself
        if (command.equals("description")) {
            String moduleCode = userInput.substring(userInput.indexOf("description") + 11).trim();
            // checks if moduleCode is moduleCode and not some random bs
            if (!Api.getDescription(moduleCode).isEmpty()) {
                String description = Api.getDescription(moduleCode);
                System.out.println(description);
                // it should be in this function because i might use the methods in other functions
                // and it may cause unintentional printing to the system
            }
        } else if (command.equals("workload")) {
            String moduleCode = userInput.substring(userInput.indexOf("workload") + 8).trim();
            // checks if moduleCode is moduleCode and not some random bs
            if (!Api.getWorkload(moduleCode).isEmpty()) {
                JSONArray workload = Api.getWorkload(moduleCode);
                System.out.println(workload);
            }
        } else if (command.equals("all")) {
            JSONArray allModules = listAllModules();
            System.out.println(allModules);
        } else {
            throw new UnknownCommandException(command);
        }
    }


}
