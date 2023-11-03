package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/*
class ModuleListTest {
    //success scenario 1: 2 ModuleList --> difference
    @Test
    void getDifferenceTest_twoModuleList_expectDifference() throws InvalidObjectException {
        ModuleList first = new ModuleList("CS1231S CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");
        ModuleList second = new ModuleList("CS1231S CS2030S CS2040S MA1511");
        ModuleList difference = new ModuleList();
        ModuleList actualDifference = new ModuleList("CS2100 CS2101 CS2106 CS2109S CS3230");

        difference.getDifference(first, second);

        //test
        int numberOfModules = difference.getNumberOfModules();

        for (int i = 0; i < numberOfModules; i += 1) {
            assertEquals(difference.getMainModuleList().get(i), actualDifference.getMainModuleList().get(i));
        }
    }

    //success scenario 2: 1 empty ModuleList, 1 ModuleList --> difference which is empty
    @Test
    void getDifferenceTest_oneEmptyModuleListAnotherModuleList_expectEmptyDifference() throws InvalidObjectException {
        ModuleList first = new ModuleList();
        ModuleList second = new ModuleList("CS1231S CS2030S CS2040S MA1511");
        ModuleList difference = new ModuleList();
        ModuleList actualDifference = new ModuleList();

        difference.getDifference(first, second);

        //test
        int numberOfModules = difference.getNumberOfModules();

        for (int i = 0; i < numberOfModules; i += 1) {
            assertEquals(difference.getMainModuleList().get(i), actualDifference.getMainModuleList().get(i));
        }
    }

    //failure scenario 1: null ModuleList input --> throw exception
    @Test
    void getDifferenceTest_nullModuleListInput_expectException() {
        ModuleList second = new ModuleList("CS1231S CS2030S CS2040S MA1511");
        ModuleList difference = new ModuleList();

        //test
        assertThrows(InvalidObjectException.class, () -> difference.getDifference(null, second));
    }

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
}

 */
