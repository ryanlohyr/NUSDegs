package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModuleListTest {

    //success scenario 1: 1 input String, 1 ModuleList that contains input String --> true
    @Test
    void existsTest_moduleListContainsModule_expectTrue() throws InvalidObjectException {
        String inputString = "CS1231S";
        ModuleList ml = new ModuleList("CS1231S CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");

        //test
        boolean result = ml.exists(inputString);
        assertTrue(result);
    }

    //success scenario 2: 1 input String, 1 ModuleList that does not contain input String --> false
    @Test
    void existsTest_moduleListDoesNotContainModule_expectFalse() throws InvalidObjectException {
        String inputString = "CS1231S";
        ModuleList ml = new ModuleList("CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");

        //test
        boolean result = ml.exists(inputString);
        assertFalse(result);
    }

    //failure scenario 1: input null string, 1 ModuleList --> throw exception
    @Test
    void existsTest_nullInput_expectException() {
        ModuleList ml = new ModuleList("CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");

        //test
        assertThrows(InvalidObjectException.class, () -> ml.exists(null));
    }

    //success scenario 3: input string, 1 empty ModuleList --> false
    @Test
    void existsTest_nullMainModuleList_expectException() throws InvalidObjectException {
        String inputString = "CS1231S";
        ModuleList ml = new ModuleList();

        //test
        boolean result = ml.exists(inputString);
        assertFalse(result);
    }

    @Test
    void deleteModulebyCodeTest_moduleExists_expectDelete() {
        ModuleList moduleList = new ModuleList();
        moduleList.addModule(new Module("CS1231"));
        moduleList.deleteModulebyCode("CS1231");
        assertTrue(moduleList.getMainModuleList().isEmpty());
    }
}
