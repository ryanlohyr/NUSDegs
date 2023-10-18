package seedu.duke.models;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.views.ModuleInfo;


public class Api {
    public static JSONObject getModuleInfo(String moduleCode) throws URISyntaxException {
        String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            JSONObject moduleInfo = (JSONObject) parser.parse(responseBody);
            ModuleInfo.printModule(moduleInfo);
            return moduleInfo;
            // Now you can access individual properties in the JSON
            //           String preclusionRule = (String) jsonObject.get("preclusionRule");
            //           String additionalInformation = (String) jsonObject.get("additionalInformation");
            //           JSONArray aliasesArray = (JSONArray) jsonObject.get("aliases");
            //           System.out.println(JsonFormatter.formatJson(responseBody));
            //      System.out.println(preclusionRule);
            //      System.out.println(additionalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
 //   public static String getModuleName(JSONObject module) {
//        return (String) module.get("moduleName");
//    }


