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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.models.schema.ModuleList;

import static seedu.duke.models.logic.DataRepository.getRequirements;

import seedu.duke.utils.Parser;
import seedu.duke.views.ErrorHandler;
import seedu.duke.views.ModuleInfo;
import seedu.duke.views.UnknownCommandException;


public class Api {

    /**
     * Retrieves the prerequisite tree for a module specified by its code.
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSON object representing the prerequisite tree for the module. The prerequisite tree can be in one of
     */
    private static JSONObject getModulePrereqTree(String moduleCode) {
        JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
        if (fullModuleInfo == null) {
            return null;
        }
        //prereqTree can be returned as a string(single pre requisite), null(No pre requisites) or object
        Object prereqTree = fullModuleInfo.get("prereqTree");
        if (prereqTree == null) {
            return null;
        } else if (prereqTree instanceof String) {
            JSONObject jsonObject = new JSONObject();
            ArrayList<String> requirementList = new ArrayList<>();
            requirementList.add((String) prereqTree);
            jsonObject.put("or", requirementList);

            return jsonObject;
        }

        return (JSONObject) fullModuleInfo.get("prereqTree");
    }

    /**
     * Checks if a given module code is exempted from certain requirements.
     *
     * @param moduleCode The module code to check.
     * @return True if the module is exempted, false otherwise.
     */
    private static boolean isModuleException(String moduleCode) {
        ArrayList<String> exemptedModules = new ArrayList<>();
        exemptedModules.add("CS1231");
        exemptedModules.add("MA1508E");
        exemptedModules.add("EE4204");
        exemptedModules.add("MA1511");
        exemptedModules.add("MA1512");
        return exemptedModules.contains(moduleCode);
    }

    /**
     * Retrieves a list of exempted prerequisites for a given module code.
     *
     * @param moduleCode The module code to retrieve exempted prerequisites for.
     * @return An ArrayList of exempted prerequisite module codes.
     */
    private static ArrayList<String> getExemptedPrerequisite(String moduleCode) {
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

    private static String sendHttpRequestAndGetResponseBody(String url) throws ParseException,
            IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }
    /**
     * Retrieves detailed module information from an external API based on the module code.
     *
     * @author rohitcube
     * @param moduleCode The module code to retrieve information for.
     * @return A JSONObject containing module information.
     */
    public static JSONObject getFullModuleInfo(String moduleCode) {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";

            String responseBody = sendHttpRequestAndGetResponseBody(url);
            if (responseBody.isEmpty()) {
                return new JSONObject();
            }
            JSONParser parser = new JSONParser();
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
        } catch (NullPointerException e) {
            //System.out.println("Invalid Module Name");
        }
        return null;
    }

    /**
     * Retrieves the name of a module based on its module code.
     *
     * @param moduleCode The module code to retrieve the name for.
     * @return The name of the module.
     */
    public static String getModuleName(String moduleCode) {
        JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
        assert fullModuleInfo != null;
        return (String) fullModuleInfo.get("title");
    }

    /**
     * Retrieves the description of a module based on its module code.
     * @author rohitcube
     * @param moduleCode The module code to retrieve the description for.
     * @return The description of the module.
     */
    public static String getDescription(String moduleCode) {
        JSONObject moduleInfo = getFullModuleInfo(moduleCode);
        String error = " ";
        try {
            String descr = (String) moduleInfo.get("description");
            return descr;
        } catch (NullPointerException e) {
            System.out.println(" ");
        }
        return error;
    }

    /**
     * Retrieves the workload information for a module based on its module code.
     *
     * @author rohitcube
     * @param moduleCode The module code to retrieve workload information for.
     * @return A JSONArray containing workload details.
     */
    public static JSONArray getWorkload(String moduleCode) {
        JSONObject moduleInfo = getFullModuleInfo(moduleCode);
        JSONArray emptyArray = new JSONArray();
        assert moduleInfo != null;
        try {
            return (JSONArray) moduleInfo.get("workload");
        } catch (NullPointerException e) {
            System.out.println(" ");
        }
        return emptyArray;
    }

    /**
     * Recursively flattens and processes a list of module prerequisites.
     *
     * @author ryanlohyr
     * @param major              The major or program for which prerequisites are being flattened.
     * @param prerequisites      An ArrayList to store the flattened prerequisites.
     * @param modulePrereqArray  An ArrayList containing the module prerequisites to be processed.
     * @param courseRequirements An ArrayList containing course requirements.
     * @param currRequisite      The type of the current prerequisite (e.g., "and" or "or").
     */
    private static void flattenPrereq(
            String major,
            ArrayList<String> prerequisites,
            ArrayList<Objects> modulePrereqArray,
            ArrayList<String> courseRequirements,
            String currRequisite) {
        //base case
        for (Object module : modulePrereqArray) {
            if (module instanceof String) {
                String formattedModule = ((String) module).replace(":D", "");
                formattedModule = formattedModule.replace("%", "");

                if (courseRequirements.contains(formattedModule)) {
                    prerequisites.add(formattedModule);
                    if (currRequisite.equals("or")) {
                        return;
                    }
                }
            } else {
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
     *
     * @author ryanlohyr
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSONObject representing the prerequisite tree for the module or NULL if no prerequisites are specified.
     */
    public static ArrayList<String> getModulePrereqBasedOnCourse(String moduleCode, String major) {
        // Only accepts CEG requirements now
        if (!Objects.equals(major, "CEG")) {
            return null;
        }

        //Modules that has prerequisites incorrectly identified by NUSMods
        if (isModuleException(moduleCode)) {
            return getExemptedPrerequisite(moduleCode);
        }

        ArrayList<String> prerequisites = new ArrayList<>();

        JSONObject modulePrereqTree = getModulePrereqTree(moduleCode);

        if (modulePrereqTree == null) {
            return null;
        }
        String key = (String) modulePrereqTree.keySet().toArray()[0];

        //settle this warning
        ArrayList<Objects> initial = (ArrayList<Objects>) modulePrereqTree.get(key);

        flattenPrereq(major, prerequisites, initial, getRequirements(major), key);

        return prerequisites;

    }

    /**
     * Checks if a module with the given module code exists in the NUSMods database.
     *
     * @param moduleCode The module code to check for existence.
     * @return `true` if the module exists, `false` if the module does not exist.
     */
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

        if (modulePrereqTree == null) {
            return true;
        }

        String key = (String) modulePrereqTree.keySet().toArray()[0];
        ArrayList<Objects> initial = (ArrayList<Objects>) modulePrereqTree.get(key);

        //Modules that has prerequisites incorrectly identified by NUSMods
        if (isModuleException(moduleCode)) {
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
     * @param modulePrereqArray The array of prerequisite modules or conditions to be checked.
     * @param currRequisite     The type of prerequisite condition ("or" or "and").
     * @param completedModules  The list of completed modules by the student.
     * @return `true` if the student satisfies all prerequisites, `false` otherwise.
     */
    private static boolean checkPrereq(
            ArrayList<Objects> modulePrereqArray,
            String currRequisite,
            ModuleList completedModules) {

        if (currRequisite.equals("or")) {
            for (Object module : modulePrereqArray) {
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
                    return checkPrereq(prereqBranchArray, key, completedModules);
                }
            }
            return false;
        } else {
            for (Object module : modulePrereqArray) {
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
                    if (!checkPrereq(prereqBranchArray, key, completedModules)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Retrieves a list of modules from an external API and returns it as a JSONArray.
     *
     * @author rohitcube
     * @return A JSONArray containing module information.
     * @throws RuntimeException If there is an issue with the HTTP request or JSON parsing.
     */
    public static JSONArray listAllModules() {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/moduleList.json";
            String responseBody = sendHttpRequestAndGetResponseBody(url);
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(responseBody);
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

    /**
     * Searches for modules containing a specified keyword in their title within a given module list.
     *
     * @author rohitcube
     * @param keyword    The keyword to search for.
     * @param moduleList The list of modules to search within.
     * @return A JSONArray containing modules matching the keyword.
     */
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

    /**
     * Executes commands based on user input for module information retrieval.
     * Supports commands: "description", "workload", "all".
     *
     * @author rohitcube
     * @param command   The command provided by the user.
     * @param userInput The user input string containing the command and module code (if applicable).
     * @throws UnknownCommandException If an unknown command is provided.
     */
    public static void infoCommands(String command, String userInput) {
        if (command.equals("description")) {
            String moduleCode =
                    userInput.substring(userInput.indexOf("description") + 11).trim().toUpperCase();
            if (!Api.getDescription(moduleCode).isEmpty()) {
                String description = Api.getDescription(moduleCode);
                System.out.println(description);
            }
        } else if (command.equals("workload")) {
            String moduleCode = userInput.substring(userInput.indexOf("workload") + 8).trim().toUpperCase();
            if (!Api.getWorkload(moduleCode).isEmpty()) {
                JSONArray workload = Api.getWorkload(moduleCode);
                System.out.println(workload);
            }
        } else if (command.equals("all")) {
            JSONArray allModules = listAllModules();
            assert allModules != null;
            ModuleInfo.printJsonArray(allModules);
        } else {
            System.out.println("man");
            ErrorHandler.invalidCommandforInfoCommand();
        }
    }

    public static void searchCommand(String userInput) {
        if (!Parser.isValidKeywordInput(userInput)) {
            ErrorHandler.emptyKeywordforSearchCommand();
            return;
        }
        String keywords = userInput.substring(userInput.indexOf("search") + 6);
        JSONArray modulesToPrint = Api.search(keywords, Api.listAllModules());
        if (modulesToPrint.isEmpty()) {
            ErrorHandler.emptyArrayforSearchCommand();
            return;
        }
        ModuleInfo.searchHeader();
        ModuleInfo.printJsonArray(modulesToPrint);
    }
}
