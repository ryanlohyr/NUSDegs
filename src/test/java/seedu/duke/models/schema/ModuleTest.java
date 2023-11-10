package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleTest {

    @Test
    void testModularCreditsGetsUpdated() {
        // Arrange
        Module module = new Module("CS1010");

        //Act
        int credits = module.getModuleCredits();

        //Assert
        int expectedCredits = 4;
        assertEquals(expectedCredits,credits);
    }

    @Test
    void testTwelveModularCreditsGetsUpdated() {
        // Arrange
        Module module = new Module("CP3880");

        //Act
        int credits = module.getModuleCredits();

        //Assert
        int expectedCredits = 12;
        assertEquals(expectedCredits,credits);
    }
    @Test
    void testTw0ModularCreditsGetsUpdated() {
        // Arrange
        Module module = new Module("CFG1002");

        //Act
        int credits = module.getModuleCredits();

        //Assert
        int expectedCredits = 2;
        assertEquals(expectedCredits,credits);
    }


}
