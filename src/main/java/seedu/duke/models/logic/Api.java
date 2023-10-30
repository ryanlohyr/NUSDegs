package seedu.duke.models.logic;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.views.UnknownCommandException;


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

    public static String getDescription(String moduleCode) {
        JSONObject moduleInfo = getModuleInfoJson(moduleCode);
        assert moduleInfo != null;
        return (String) moduleInfo.get("description");
    }

    public static JSONArray getWorkload(String moduleCode) {
        JSONObject moduleInfo = getModuleInfoJson(moduleCode);
        assert moduleInfo != null;
        return (JSONArray) moduleInfo.get("workload");
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
            //   ModuleInfo.printModule(moduleInfo);
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
        System.out.println("oops! Your module code is invalid. Please try again.");
        return null;
    }

    public static JSONArray listAllModules() {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/moduleList.json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            // Will refactor the variable later on, left it for easier readability
            JSONArray moduleList = (JSONArray) parser.parse(responseBody);
            // System.out.println(moduleList);
            return moduleList;
            // find a way to pretty print this
        } catch (URISyntaxException e) {
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, the JSON object could not be parsed");
        }
        return null;
    }

    // search keyword in module name, returns you module code(s) and module name (title)
    public static JSONArray search(String keyword, JSONArray moduleList) {
        JSONArray modulesContainingKeyword = new JSONArray();
        if (keyword.isEmpty()) {
            return new JSONArray();
        }
        for (Object moduleObject : moduleList) {
            JSONObject module = (JSONObject) moduleObject; // Cast to JSONObject
            String title = (String) module.get("title");
            if (title.contains(keyword)) {
                modulesContainingKeyword.add(module);
                //not sure how to resolve this yellow line
            }
        }
        return modulesContainingKeyword;
    }

    public static String getModuleName(JSONObject module) {
        return (String) module.get("moduleName");
    }

    public static void infoCommands(String command, String userInput) throws UnknownCommandException {
        // checks if command is even equal to any of these words, if equal nothing then return go fk yourself
        if (command.equals("description")) {
            String moduleCode = userInput.substring(userInput.indexOf("description") + 11).trim();
            // checks if moduleCode is moduleCode and not some random bs
            if (!Api.getDescription(moduleCode).isEmpty()) {
                String description = Api.getDescription(moduleCode);
                System.out.println(description);
                // it should be in this function because i might use the methods in other functions
                // and it may cause unintentional printing to the system
            }
        } else if (command.equals("workload")) {
            String moduleCode = userInput.substring(userInput.indexOf("workload") + 8).trim();
            // checks if moduleCode is moduleCode and not some random bs
            if (!Api.getWorkload(moduleCode).isEmpty()) {
                JSONArray workload = Api.getWorkload(moduleCode);
                System.out.println(workload);
            }
        } else if (command.equals("all")) {
            JSONArray allModules = listAllModules();
            System.out.println(allModules);
        } else {
            throw new UnknownCommandException(command);
        }
    }


}
