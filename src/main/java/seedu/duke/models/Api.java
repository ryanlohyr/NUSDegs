package seedu.duke.models;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.views.ModuleInfo;


public class Api {

    public static String getModuleInfo(String moduleCode) throws URISyntaxException {
        String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDescription(String moduleInfo) {
        int indexOfDescriptionStart = moduleInfo.indexOf("description");
        int indexOfDescriptionEnd = moduleInfo.indexOf("workload");
        if (indexOfDescriptionEnd != -1 | (indexOfDescriptionEnd != -1)) {
            System.out.println("Index of 'description': " + indexOfDescriptionStart);
            System.out.println("Index of 'description' end: " + indexOfDescriptionEnd);
        } else {
            System.out.println("'description' not found in the string.");
        }
        //   assuming that workload is always the next object after description
        String description = moduleInfo.substring(indexOfDescriptionStart + 8, indexOfDescriptionEnd - 2);
        System.out.println(description);
        return description;
    }

    public static JSONObject getModuleInfoJson(String moduleCode) {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            // Will refactor the variable later on, left it for easier readability
            JSONObject moduleInfo = (JSONObject) parser.parse(responseBody);
            ModuleInfo.printModule(moduleInfo);
            return moduleInfo;
        } catch (ParseException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, the JSON object could not be parsed");
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, the JSON object could not be parsed");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
        }
        return null;
    }

    public static String getModuleName(JSONObject module) {
        return (String) module.get("moduleName");
    }

}
