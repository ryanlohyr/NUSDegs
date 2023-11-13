package seedu.duke.models.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.duke.models.schema.ModuleList;
import seedu.duke.utils.exceptions.InvalidPrereqException;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static seedu.duke.storage.StorageManager.getRequirements;

public class Prerequisite {

    //@@author SebasFok
    /**
     * Recursively checks if each branch of the prereq tree is satisfied by the student.
     *
     * @param modulePrereqArray The array of prerequisite modules or conditions to be checked.
     * @param currRequisite     The type of prerequisite condition ("or" or "and").
     * @param completedModules  The list of completed modules by the student.
     * @return `true` if the student satisfies all prerequisites, `false` otherwise.
     */
    private static boolean isPrereqSatisfied(
            ArrayList<Objects> modulePrereqArray,
            String currRequisite,
            ModuleList completedModules) {
        try {

            if (currRequisite.equals("or")) {
                return isOrBranchSatisfied(modulePrereqArray, completedModules);
            } else {
                return isAndBranchSatisfied(modulePrereqArray, completedModules);
            }
        } catch (ClassCastException e) {
            System.out.println("Error checking prereq for this module");
            return false;
        }

    }

    //@@author SebasFok
    /**
     * Checks if the AND branch of a module's prerequisites is satisfied based on completed modules.
     * Recursively checks the branch if there are nested prerequisite structures in the AND branch
     *
     * @param modulePrereqArray The array representing the AND branch of prerequisites.
     * @param completedModules  The list of modules that have been completed.
     * @return true if the AND branch is satisfied, false otherwise.
     * @throws RuntimeException If an unexpected exception occurs during prerequisite checking.
     */
    private static boolean isAndBranchSatisfied(ArrayList<Objects> modulePrereqArray, ModuleList completedModules) {
        for (Object module : modulePrereqArray) {
            if (module instanceof String) {
                String formattedModule = ((String) module).replace(":D", "");
                formattedModule = formattedModule.replace("%", "");
                try {
                    if (!completedModules.existsByCode(formattedModule)) {
                        return false;
                    }
                } catch (InvalidObjectException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JSONObject prereqBranch = (JSONObject) module;

                //for cs, some modules return pre req in this form {"nOf":[2,["MA1511:D","MA1512:D"]]}
                //have to convert first
                if (prereqBranch.containsKey("nOf")) {
                    String key = "and";
                    ArrayList<ArrayList<Objects>> initial =
                            (ArrayList<ArrayList<Objects>>) prereqBranch.get("nOf");
                    ArrayList<Objects> formattedInitial = initial.get(1);
                    JSONArray prereqBranchArray = (JSONArray) formattedInitial;
                    if (!isPrereqSatisfied(prereqBranchArray, key, completedModules)){
                        return false;
                    }
                } else {
                    String key = (String) prereqBranch.keySet().toArray()[0];
                    JSONArray prereqBranchArray = (JSONArray) prereqBranch.get(key);
                    if (!isPrereqSatisfied(prereqBranchArray, key, completedModules)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    //@@author SebasFok
    /**
     * Checks if the OR branch of a module's prerequisites is satisfied based on completed modules.
     * Recursively checks the branch if there are nested prerequisite structures in the OR branch
     *
     * @param modulePrereqArray The array representing the OR branch of prerequisites.
     * @param completedModules  The list of modules that have been completed.
     * @return true if the OR branch is satisfied, false otherwise.
     * @throws RuntimeException If an unexpected exception occurs during prerequisite checking.
     */
    private static boolean isOrBranchSatisfied(ArrayList<Objects> modulePrereqArray, ModuleList completedModules) {
        for (Object module : modulePrereqArray) {
            if (module instanceof String) {
                String formattedModule = ((String) module).replace(":D", "");
                formattedModule = formattedModule.replace("%", "");
                try {
                    if (completedModules.existsByCode(formattedModule)) {
                        return true;
                    }
                } catch (InvalidObjectException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JSONObject prereqBranch = (JSONObject) module;

                //for cs, some modules return pre req in this form {"nOf":[2,["MA1511:D","MA1512:D"]]}
                //have to convert first
                if (prereqBranch.containsKey("nOf")) {
                    String key = "and";
                    ArrayList<ArrayList<Objects>> initial =
                            (ArrayList<ArrayList<Objects>>) prereqBranch.get("nOf");
                    ArrayList<Objects> formattedInitial = initial.get(1);
                    JSONArray prereqBranchArray = (JSONArray) formattedInitial;
                    if (isPrereqSatisfied(prereqBranchArray, key, completedModules)){
                        return true;
                    }

                } else {
                    String key = (String) prereqBranch.keySet().toArray()[0];
                    JSONArray prereqBranchArray = (JSONArray) prereqBranch.get(key);
                    return isPrereqSatisfied(prereqBranchArray, key, completedModules);
                }


            }
        }
        return false;
    }

    //@@author ryanlohyr
    /**
     * Recursively flattens and processes a list of module prerequisites.
     * More info on the data structure being processed can be found in
     *  the prereqTree key in an example <a href="https://api.nusmods.com/v2/2023-2024/modules/EE2211.json">...</a>
     * @param major              The major or program for which prerequisites are being flattened.
     * @param prerequisites      An ArrayList to store the flattened prerequisites.
     * @param modulePrereqArray  An ArrayList containing the module prerequisites to be processed.
     * @param courseRequirements An ArrayList containing course requirements.
     * @param currRequisite      The type of the current prerequisite (e.g., "and" or "or").
     *
     */
    private static void flattenPrereq(
            String major,
            ArrayList<String> prerequisites,
            ArrayList<Objects> modulePrereqArray,
            ArrayList<String> courseRequirements,
            String currRequisite) throws ClassCastException {
        try {
            int lengthOfModulePreReqArray = modulePrereqArray.size();

            // we keep a counter as if no preclusion is a module requirement for the major
            // we will take the last module in the list of preclusions
            int counter = 0;

            for (Object module : modulePrereqArray) {
                if (module instanceof String) {
                    String formattedModule = ((String) module).split(":")[0];
                    formattedModule = formattedModule.replace("%", "");
                    if (courseRequirements.contains(formattedModule) || currRequisite.equals("and")) {
                        prerequisites.add(formattedModule);
                        if (currRequisite.equals("or")) {
                            return;
                        }
                    }

                    //if this is the last item and the module also part of the courseRequirements, we add it anw
                    if (currRequisite.equals("or") && counter == (lengthOfModulePreReqArray - 1)
                            && !courseRequirements.contains((formattedModule))) {
                        prerequisites.add(formattedModule);
                        return;
                    }
                } else {
                    //item is an object
                    //here, we determine if its 'or' or 'and'
                    JSONObject moduleJSON = (JSONObject) module;

                    if (moduleJSON.containsKey("nOf")) {
                        String key = "and";
                        ArrayList<ArrayList<Objects>> initial = (ArrayList<ArrayList<Objects>>) moduleJSON.get("nOf");
                        ArrayList<Objects> formattedInitial = initial.get(1);
                        flattenPrereq(major, prerequisites, formattedInitial, courseRequirements, key);
                    } else {
                        String key = (String) moduleJSON.keySet().toArray()[0];

                        ArrayList<Objects> initial = (ArrayList<Objects>) moduleJSON.get(key);

                        flattenPrereq(major, prerequisites, initial, courseRequirements, key);
                    }

                }
                counter += 1;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException();

        }
    }

    //@@author ryanlohyr
    /**
     * Retrieves the prerequisite array for a module specified by its code and also taking into account the degree
     * requirements of the course.
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSONObject representing the prerequisite tree for the module or NULL if no prerequisites are specified.
     *
     */
    public static ArrayList<String> getModulePrereqBasedOnCourse(String moduleCode, String major)
            throws InvalidPrereqException, IOException {

        //Modules that has prerequisites incorrectly identified by NUSMods
        if (isModuleException(moduleCode)) {
            return getExemptedPrerequisite(moduleCode);
        }

        JSONObject modulePrereqTree = getModulePrereqTree(moduleCode);

        if (modulePrereqTree == null) {
            return null;
        }

        String key = (String) modulePrereqTree.keySet().toArray()[0];

        ArrayList<String> prerequisites = new ArrayList<>();

        ArrayList<Objects> initial = (ArrayList<Objects>) modulePrereqTree.get(key);
        try {
            flattenPrereq(major, prerequisites, initial, getRequirements(major), key);
        } catch (ClassCastException e) {
            throw new InvalidPrereqException(moduleCode);
        }

        //As some modules in NUSMods return empty objects, we will return null to standardize
        if(prerequisites.isEmpty()){
            return null;
        }

        return prerequisites;
    }

    //@@author ryanlohyr
    /**
     * Retrieves the prerequisite tree for a module specified by its code.
     *
     * @param moduleCode The code of the module for which prerequisites are to be retrieved.
     * @return A JSON object representing the prerequisite tree for the module. The prerequisite tree can be in one of
     *
     */
    static JSONObject getModulePrereqTree(String moduleCode) throws IOException {
        JSONObject fullModuleInfo = Api.getFullModuleInfo(moduleCode);
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

    //@@author ryanlohyr
    /**
     * Checks if a given module code is exempted from certain requirements.
     *
     * @param moduleCode The module code to check.
     * @return True if the module is exempted, false otherwise.
     */
    static boolean isModuleException(String moduleCode) {
        ArrayList<String> exemptedModules = new ArrayList<>(List.of("CS1231", "CS1231S", "MA1508E", "EE4204",
                "MA1511", "MA1512", "MA1521", "MA1522", "CS2109S"));

        return exemptedModules.contains(moduleCode);
    }

    //@@author ryanlohyr
    /**
     * Retrieves a list of exempted prerequisites for a given module code.
     * @param moduleCode The module code to retrieve exempted prerequisites for.
     * @return An ArrayList of exempted prerequisite module codes.
     */
    static ArrayList<String> getExemptedPrerequisite(String moduleCode) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        map.put("CS1231", list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("MA1511");
        list2.add("MA1512");
        map.put("MA1508E", list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("ST2334");
        map.put("EE4204", list3);

        ArrayList<String> list4 = new ArrayList<>();
        list4.add("CS2040S");
        list4.add("CS1231S");
        list4.add("MA1521");
        map.put("CS2109S", list4);

        ArrayList<String> emptyList = new ArrayList<>();

        map.put("MA1511", emptyList);

        map.put("CS1231S", emptyList);

        map.put("MA1512", emptyList);

        map.put("MA1521", emptyList);

        map.put("MA1522", emptyList);


        return map.get(moduleCode);
    }

    //@@author SebasFok
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
        try {
            if (!Api.isValidModule(moduleCode)) {
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

            return isPrereqSatisfied(initial, key, completedModules);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
