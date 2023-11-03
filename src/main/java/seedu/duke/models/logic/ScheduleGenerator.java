package seedu.duke.models.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static seedu.duke.models.logic.Api.getModulePrereqBasedOnCourse;
import static seedu.duke.models.logic.DataRepository.getRequirements;

public class ScheduleGenerator {

    /**
     * Generates a recommended schedule for a given course based on its requirements and prerequisites.
     *
     * @author ryanlohyr
     * @param course The course for which to generate a recommended schedule.
     * @return An ArrayList of strings representing the recommended schedule in order of completion.
     */
    public static ArrayList<String> generateRecommendedSchedule(String course){
        ArrayList<String> requirements = getRequirements(course);
        HashMap<String, Integer> degreeMap = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        ArrayList<String> schedule = new ArrayList<>();
        HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();

        //initialisation
        for(String requirement: requirements) {
            adjacencyList.put(requirement, new ArrayList<>());
            degreeMap.put(requirement, 0);
        }

        for (String requirement : requirements) {
            ArrayList<String> prereqArray = getModulePrereqBasedOnCourse(requirement, course);

            if (prereqArray == null) {
                prereqArray = new ArrayList<>();
            }

            //we need to create an adjacency list to add all the connections
            // from pre req -> item
            for (String s : prereqArray) {
                adjacencyList.get(s).add(requirement);
                Integer value = degreeMap.get(requirement) + 1;
                degreeMap.put(requirement, value);
            }
        }

        for (String key : degreeMap.keySet()) {
            Integer value = degreeMap.get(key);
            if(value == 0){
                q.offer(key);
            }
        }

        while(!q.isEmpty()){
            String curr = q.poll();
            schedule.add(curr);
            ArrayList<String> currReq = adjacencyList.get(curr);
            for (String s : currReq) {
                int num = degreeMap.get(s) - 1;
                degreeMap.put(s, num);
                if (num == 0) {
                    q.offer(s);
                }
            }
        }

        return schedule;
    }

}


