package seedu.duke.models.logic;
import seedu.duke.models.schema.ModuleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CompletePreqs checks which mods are unlocked once you finish a specific module
 */
public class CompletePreqs {
    //Full list of mods with modulesWithPreqs
    private HashMap<String, List<String>> modulesWithPreqs;
    private ArrayList<String> addToModulesCompleted;

    // To track modules that are already unlocked
    private Set<String> unlockedModulesSet;

    public CompletePreqs() {
        modulesWithPreqs = new HashMap<>();
        unlockedModulesSet = new HashSet<>();
        addToModulesCompleted = new ArrayList<>();
    }

    public CompletePreqs(HashMap<String, List<String>> inputMods) {
        modulesWithPreqs = new HashMap<>(inputMods);
        unlockedModulesSet = new HashSet<>();
        addToModulesCompleted = new ArrayList<>();
    }

    /**
     * Create a list of completed mods, hardcoded from startup.
     *
     * @param list
     */

    public void initializeCompletedMods(ModuleList list) {
        addToModulesCompleted.addAll(list.getModuleCodes());
        for (String mod : addToModulesCompleted) {
            processModuleForUnlockingWithoutPrint(mod);
        }
    }



    /**
     * Prints what mods have been unlocked after input
     *
     * @param moduleCompleted
     */
    public void getUnlockedMods(String moduleCompleted) {


        // Check prerequisites of the moduleCompleted
        if (modulesWithPreqs.containsKey(moduleCompleted)) {
            List<String> unmetPrerequisites = new ArrayList<>();
            for (String preq : modulesWithPreqs.get(moduleCompleted)) {
                if (!addToModulesCompleted.contains(preq)) {
                    unmetPrerequisites.add(preq);
                }
            }
            //Stops if a completedMod shouldn't be able to be completed with proper preqs
            if (!unmetPrerequisites.isEmpty()) {
                System.out.println(moduleCompleted +
                        " cannot be marked as completed because of uncompleted prerequisites: "
                        + unmetPrerequisites);
                return;
            }
        }
        //If its not marked as completed, properly add it to the list
        if (!addToModulesCompleted.contains(moduleCompleted)) {
            addToModulesCompleted.add(moduleCompleted);
        }

        System.out.println("Mod completed: " + moduleCompleted);
        printUnlockedMods(moduleCompleted);
    }

    /**
     * This is only used for the first initalization of the mods.
     * @param moduleCompleted
     */
    private void processModuleForUnlockingWithoutPrint(String moduleCompleted) {
        ArrayList<String> newMods = new ArrayList<>();


        for (String key : modulesWithPreqs.keySet()) {
            //If new unlocked mod isn't marked as complete or unlocked already
            if (!unlockedModulesSet.contains(key) && !addToModulesCompleted.contains(key)) {
                boolean allPrerequisitesMet = true;
                for (String preq : modulesWithPreqs.get(key)) {
                    if (!addToModulesCompleted.contains(preq)) {
                        //Make sure preq isn't already marked as done
                        allPrerequisitesMet = false;
                        break;
                    }
                }
                if (allPrerequisitesMet) {
                    newMods.add(key);
                    unlockedModulesSet.add(key);
                }
            }
        }
    }



    /**
     * @param moduleCompleted
     */
    public void printUnlockedMods(String moduleCompleted) {
        ArrayList<String> newlyUnlockedMods = new ArrayList<>();

        for (String key : modulesWithPreqs.keySet()) {
            //If new unlocked mod isn't marked as complete or unlocked already
            if (!unlockedModulesSet.contains(key) && !addToModulesCompleted.contains(key)) {
                boolean allPrerequisitesMet = true;
                for (String preq : modulesWithPreqs.get(key)) {
                    if (!addToModulesCompleted.contains(preq)) {
                        //Make sure preq isn't already marked as done
                        allPrerequisitesMet = false;
                        break;
                    }
                }
                if (allPrerequisitesMet) {
                    newlyUnlockedMods.add(key);
                    unlockedModulesSet.add(key);
                }
            }
        }
        for (String mod : newlyUnlockedMods) {
            System.out.println(mod + " has been unlocked!");
        }

    }

    public void printModsCompleted(){
        for (String mod: addToModulesCompleted){
            System.out.println(mod + "has been completed");
        }
    }

    public boolean checkModInput(String[] words, ArrayList<String> majorModuleCodes){
        if (words.length == 1){
            if (majorModuleCodes.contains(words[0].toUpperCase())){
                return true;
            }
            System.out.println("Please enter a available mod: ");
            System.out.println(majorModuleCodes);
            return false;
        }
        System.out.println("Please enter a available mod after the complete keyword");
        System.out.println(majorModuleCodes);
        return false;
    }
}


