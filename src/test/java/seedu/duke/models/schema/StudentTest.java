package seedu.duke.models.schema;

import org.junit.jupiter.api.Test;

import seedu.duke.utils.exceptions.FailPrereqException;
import seedu.duke.utils.exceptions.MandatoryPrereqException;
import seedu.duke.utils.exceptions.MissingModuleException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    @Test
    void clearAllModulesFromScheduleTest_expectEmptySchedule() {
        Student student = new Student();
        Schedule schedule = new Schedule("CS1231S MA1511", new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        student.setSchedule(schedule);
        student.clearAllModulesFromSchedule();
        assertTrue(student.getSchedule().getModulesPlanned().getMainModuleList().isEmpty());
    }

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
            throws IOException, MandatoryPrereqException, MissingModuleException, FailPrereqException {
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
