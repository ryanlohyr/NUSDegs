package seedu.duke;
import org.json.simple.JSONObject;
import seedu.duke.models.Api;
import seedu.duke.views.ModuleInfo;

public class ApiTest {
        public static void main(String[] args) throws Exception {
            // test setup
            String moduleCode = "CS2113";
            JSONObject moduleInfo = Api.getModuleInfo(moduleCode);
            ModuleInfo.printModule(moduleInfo);
            // more tests...
            System.out.println("All tests passed");
        }
}
