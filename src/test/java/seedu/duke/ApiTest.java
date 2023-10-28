package seedu.duke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.duke.models.logic.Api;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import seedu.duke.views.ModuleInfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {
    @Test
    void testGetModuleInfo() {
        String correctModuleInfo = "\"description\":\"This course introduces the necessary skills for systematic " +
                "and rigorous development of software systems. It covers";
        String moduleCode = "CS2113";
        String moduleInfo = null;
        try {
            moduleInfo = Api.getModuleInfo(moduleCode);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(moduleInfo, "Module info should not be null");
        assertTrue(moduleInfo.contains(correctModuleInfo), "Module info should contain relevant info");
    }

    @Test
    void testGetDescription() {
        String correctDescription = "This course introduces the necessary skills for systematic and " +
                "rigorous development of software systems. It covers requirements, design, implementation, " +
                "quality assurance, and project management aspects of small-to-medium size multi-person software" +
                " projects. The course uses the Object Oriented Programming paradigm. Students of this course will " +
                "receive hands-on practice of tools commonly used in the industry, such as test automation tools," +
                " build automation tools, and code revisioning tools will be covered.";
        String moduleCode = "CS2113";
        String description = Api.getDescription(moduleCode);
        assertEquals(correctDescription, description);
    }

    @Test
    void testGetWorkload() {
        JSONArray workload = Api.getWorkload("CS2113");
        System.out.println(workload);
    }

    @Test
    void testListAllModules() {
        Api.listAllModules();
    }
    @Test
    void testSearchModules() {
        JSONArray modulesToPrint = new JSONArray();
        modulesToPrint = Api.search("Machine Learning", Api.listAllModules());
        System.out.println(modulesToPrint);
    }

    @Test
    void testPrintJsonArray() {
        JSONArray modulesToPrint = Api.search("Machine Learning", Api.listAllModules());
        ModuleInfo.printJsonArray(modulesToPrint);
    }


}
