package seedu.duke;

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

	public CompletePreqs(HashMap<String, List<String>> inputMods) {
		modulesWithPreqs = new HashMap<>(inputMods);
		unlockedModulesSet = new HashSet<>();
		addToModulesCompleted = new ArrayList<>();
	}

	/**
	 * Create a list of completed mods, hardcoded from startup.
	 * @param list
	 */
	public void initializeCompletedMods(ModuleList list) {
		addToModulesCompleted.addAll(list.getMainModuleList());
		for (String mod : addToModulesCompleted) {
			processModuleForUnlocking(mod);
		}
	}

	/**
	 * Prints what mods have been unlocked after input
	 * @param moduleCompleted
	 */
	public void getUnlockedMods(String moduleCompleted) {
		System.out.println("Mod completed: " + moduleCompleted);

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
				System.out.println(moduleCompleted + " cannot be marked as completed because of uncompleted prerequisites: " + unmetPrerequisites);
				return;
			}
		}
		//If its not marked as completed, properly add it to the list
		if (!addToModulesCompleted.contains(moduleCompleted)) {
			addToModulesCompleted.add(moduleCompleted);
			processModuleForUnlocking(moduleCompleted);
		}
	}

	/**
	 *
	 * @param moduleCompleted
	 */
	private void processModuleForUnlocking(String moduleCompleted) {
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
}


