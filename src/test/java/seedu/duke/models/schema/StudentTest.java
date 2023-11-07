package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.FailPrereqException;
import seedu.duke.exceptions.MandatoryPrereqException;
import seedu.duke.exceptions.MissingModuleException;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    //success scenario: difference (not working)
    @Test
    void getModuleCodesLeftTest_majorSet_expectArrayList() {
        Student student = new Student();
        student.setMajor("CEG");
        //actual: student.getModuleCodesLeft();

        //expected:
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> majorModuleCodes = student.getMajorModuleCodes();
        ArrayList<String> modulesInPlanner = student.getModulesPlanned().getModulesCompleted();
        for (String moduleCode : majorModuleCodes) {
            if (!modulesInPlanner.contains(moduleCode)) {
                expected.add(moduleCode);
            }
        }

        assertEquals(expected, student.getModuleCodesLeft());
    }

    @Test
    void deleteModuleScheduleTest_moduleExists_expectDelete()
            throws InvalidObjectException, MandatoryPrereqException, MissingModuleException, FailPrereqException {
        Student student = new Student();
        student.addModuleSchedule("CS1010", 1);
        student.deleteModuleSchedule("CS1010");

        assertTrue(student.getModulesPlanned().getMainModuleList().isEmpty());
    }

    // getModuleCodesLeft_noMajorSet_expectException()
    // getDifferenceTest_twoModuleList_expectDifference()

    // success scenario 2: 1 empty ModuleList, 1 ModuleList --> difference which is empty
    // getDifferenceTest_oneEmptyModuleListAnotherModuleList_expectEmptyDifference()
    // failure scenario 1: null ModuleList input --> throw exception
    // getDifferenceTest_nullModuleListInput_expectException()
}
