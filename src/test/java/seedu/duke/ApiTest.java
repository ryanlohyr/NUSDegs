package seedu.duke;

import seedu.duke.models.logic.Api;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String moduleInfo = "\"CS2040C:D\",{\"and\":[\"CS2030:D\",{\"or\":[\"CS2040S:D\",\"CS204" +
                "0:D\"]}]}]},\"description\":\"This course introduces the necessary skills for systemati" +
                "c and rigorous development of software systems. It covers requirements, design, implementat" +
                "ion, quality assurance, and project management aspects of small-to-medium size multi-person " +
                "software projects. The course uses the Object Oriented Programming paradigm. Students of thi" +
                "s course will receive hands-on practice of tools commonly used in the industry, such as te" +
                "st automation tools, build automation tools, and code revisioning tools will be covered.\",\"w" +
                "orkload\":[2,1,0,3,4],\"title\":\"Software Engineering & Object-Oriented Programming\",\"aca" +
                "dYear\":\"2023\\/2024\",\"faculty\":\"Computing\",\"gradingBa";
        String description = Api.getDescription(moduleInfo);
        assertTrue(description.contains(correctDescription), "Module info should contain relevant info");
    }

}
