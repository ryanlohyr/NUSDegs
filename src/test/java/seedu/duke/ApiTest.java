package seedu.duke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.duke.models.logic.Api;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import seedu.duke.views.ModuleInfo;
import seedu.duke.views.UnknownCommandException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ApiTest {
    @Test
    void testGetModuleInfo_shouldReturnTrueForCS2113() {
        String correctModuleInfo = "\"description\":\"This course introduces the necessary skills for systematic " +
                "and rigorous development of software systems. It covers";
        String moduleCode = "CS2113";
        String moduleInfo = null;
        moduleInfo = Objects.requireNonNull(Api.getFullModuleInfo(moduleCode)).toJSONString();
        assertNotNull(moduleInfo, "Module info should not be null");
        assertTrue(moduleInfo.contains(correctModuleInfo), "Module info should contain relevant info");
    }

    @Test
    void testGetDescription_shouldReturnEquals() {
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
    void testGetWorkload_shouldReturnCorrectValue() {
        // uses unchecked or unsafe operations, Note: Recompile with -Xlint:unchecked for details.
        JSONArray workload = Api.getWorkload("CS2113");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(2);
        jsonArray.add(1);
        jsonArray.add(0);
        jsonArray.add(3);
        jsonArray.add(4);
        String jsonString1 = jsonArray.toJSONString();
        String jsonString2 = workload.toJSONString();
        assertEquals(jsonString2, jsonString1);
    }

    @Test
    void testListAllModules() {
        Api.listAllModules();
    }

    @Test
    void testSearchModules_emptyInput_expectedEmptyJsonArray() {
        JSONArray modulesToPrint;
        modulesToPrint = Api.search("     ", Api.listAllModules());
        assertEquals(0, modulesToPrint.size(), "The JSON array should be empty.");
    }

    @Test
    void testSearchModules_invalidInput_expectedEmptyJsonArray() {
        JSONArray modulesToPrint;
        modulesToPrint = Api.search("bs#4%ggh", Api.listAllModules());
        assertEquals(0, modulesToPrint.size(), "The JSON array should be empty.");
    }

    @Test
    void testSearchModules_validInput_expectedJsonArray() {
        JSONArray modulesToPrint;
        modulesToPrint = Api.search("Trustworthy Machine Learning", Api.listAllModules());
        JSONArray expectedArray = new JSONArray();
        JSONObject expectedObject = new JSONObject();
        expectedObject.put("moduleCode", "CS5562");
        JSONArray semester1 = new JSONArray();
        semester1.add(1);
        expectedObject.put("semesters", semester1);
        expectedObject.put("title", "Trustworthy Machine Learning");
        expectedArray.add(expectedObject);
        String expectedOutput = expectedArray.toJSONString();
        String output = modulesToPrint.toJSONString();
        assertEquals(expectedOutput, output, "The string should be equal");
    }

    @Test
    void testPrintJsonArray() {
        JSONArray modulesToPrint = Api.search("Machine Learning", Api.listAllModules());
        ModuleInfo.printJsonArray(modulesToPrint);
    }

    //invalid command, valid input
    @Test
    void testInfoCommands_invalidCommand_expectError() {
        assertThrows(UnknownCommandException.class, () -> Api.infoCommands("dbfjsdhf", "CS2113"));
    }

    @Test
    void testInfoCommands_invalidModule_expectError() {
        assertThrows(AssertionError.class, () -> Api.getDescription("CS2w3e113"));
    }


}
