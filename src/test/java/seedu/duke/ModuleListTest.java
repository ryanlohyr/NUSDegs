package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModuleListTest {

    //success scenario: 2 ModuleList --> difference
    @Test
    void getDifference_twoModuleList_expectDifference() {
        ModuleList first = new ModuleList("CS1231S CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230");
        ModuleList second = new ModuleList("CS1231S CS2030S CS2040S MA1511");
        ModuleList difference = new ModuleList();
        ModuleList actualDifference = new ModuleList("CS2100 CS2101 CS2106 CS2109S CS3230");

        difference.getDifference(first, second);

        //test
        int numberOfModules = difference.numberOfModules;

        for (int i = 0; i < numberOfModules; i += 1) {
            assertEquals(difference.getMainModuleList().get(i), actualDifference.getMainModuleList().get(i));
        }
    }

    //success scenario 2: 2 ModuleList where the first is empty --> difference which is empty
    @Test
    void getDifference_oneEmptyModuleListAnotherModuleList_expectEmptyDifference() {
        ModuleList first = new ModuleList();
        ModuleList second = new ModuleList("CS1231S CS2030S CS2040S MA1511");
        ModuleList difference = new ModuleList();
        ModuleList actualDifference = new ModuleList();

        difference.getDifference(first, second);

        //test
        int numberOfModules = difference.numberOfModules;

        for (int i = 0; i < numberOfModules; i += 1) {
            assertEquals(difference.getMainModuleList().get(i), actualDifference.getMainModuleList().get(i));
        }
    }
}