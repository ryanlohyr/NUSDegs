package seedu.duke.models.logic;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.utils.exceptions.InvalidModuleCodeException;
import seedu.duke.utils.exceptions.InvalidModuleException;

import static seedu.duke.utils.errors.HttpError.displaySocketError;

import seedu.duke.utils.Parser;
import seedu.duke.utils.errors.UserError;
import seedu.duke.views.ModuleInfoView;


public class Api {

    private static String sendHttpRequestAndGetResponseBody(String url) throws ParseException,
            IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    /**
     * Retrieves detailed module information from an external API based on the module code.
     *
     * @author rohitcube
     * @param moduleCode The module code to retrieve information for.
     * @return A JSONObject containing module information.
     *
     */
    public static JSONObject getFullModuleInfo(String moduleCode) throws RuntimeException, IOException {
        try {
            // Regex pattern to match only letters and numbers
            String regexPattern = "^[a-zA-Z0-9]+$";
            if (!moduleCode.matches(regexPattern)) {
                throw new InvalidModuleException();
            }
            String url = "https://api.nusmods.com/v2/2023-2024/modules/" + moduleCode + ".json";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new InvalidModuleCodeException();
            }
            String responseBody = sendHttpRequestAndGetResponseBody(url);
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            //System.out.println("Invalid Module Name");
        } catch (IOException | InterruptedException e) {
            throw new IOException(e);
        } catch (URISyntaxException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
        } catch (NullPointerException e) {
            //System.out.println("Invalid Module Name");
        } catch (InvalidModuleException e) {
            System.out.println("Invalid Module Code: " + e.getMessage());
        } catch (InvalidModuleCodeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the name of a module based on its module code.
     *
     * @param moduleCode The module code to retrieve the name for.
     * @return The name of the module.
     */
    public static String getModuleName(String moduleCode) {
        try {
            JSONObject fullModuleInfo = getFullModuleInfo(moduleCode);
            assert fullModuleInfo != null;
            return (String) fullModuleInfo.get("title");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the description of a module based on its module code.
     *
     * @author rohitcube
     * @param moduleCode The module code to retrieve the description for.
     * @return The description of the module.
     *
     */
    public static String getDescription(String moduleCode) throws InvalidModuleException, InvalidModuleCodeException {
        try {
            JSONObject moduleInfo = getFullModuleInfo(moduleCode);
            if (moduleInfo == null) {
                throw new InvalidModuleCodeException();
            }
            return (String) moduleInfo.get("description");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Wraps a long input string into multiple lines at a specified wrap index.
     * <p>
     * This method takes an input string and wraps it into multiple lines by inserting newline
     * characters at or before the specified wrap index. It ensures that the words are not split,
     * and the text remains readable.
     *
     * @param input     The input string to be wrapped.
     * @param wrapIndex The wrap index, indicating the maximum number of characters per line.
     * @return A new string with newline characters added for wrapping.
     */
    public static String wrapText(String input, int wrapIndex) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        StringBuilder description = new StringBuilder(input);
        int currIndex = 0;
        int markerIndex = 0;
        while (currIndex < description.length()) {
            if (markerIndex >= wrapIndex) { //index where string is wrapped
                if (description.charAt(currIndex) == ' ') {
                    description.insert(currIndex, '\n');
                    markerIndex = 0;
                    continue;
                }
                currIndex--;
                continue;
            }
            currIndex++;
            markerIndex++;
        }
        return description.toString();
    }

    /**
     * Retrieves the workload information for a module based on its module code.
     * @author rohitcube
     * @param moduleCode The module code to retrieve workload information for.
     * @return A JSONArray containing workload details.
     *
     */
    public static JSONArray getWorkload(String moduleCode) throws InvalidModuleCodeException {
        try {
            JSONObject moduleInfo = getFullModuleInfo(moduleCode);
            if (moduleInfo == null) {
                throw new InvalidModuleCodeException();
            }
            return (JSONArray) moduleInfo.get("workload");
        } catch (NullPointerException e) {
            throw new InvalidModuleCodeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the requirements the module fulfills
     * @author rohitcube
     * @param moduleCode The module code to retrieve workload information for.
     * @return A JSONArray containing workload details.
     *
     */
    public static ArrayList<String> getModuleFulfilledRequirements(String moduleCode) throws IOException {
        try {
            JSONObject moduleInfo = getFullModuleInfo(moduleCode);
            ArrayList<String> fulfilledArray = new ArrayList<>();
            ArrayList<String> response = (ArrayList<String>) moduleInfo.get("fulfillRequirements");
            if (response != null) {
                fulfilledArray = response;
            }

            return fulfilledArray;
        } catch (ClassCastException | NullPointerException e) {
            return new ArrayList<String>();
        }
    }

    /**
     * Checks if a module with the given module code exists in the NUSMods database.
     *
     * @param moduleCode The module code to check for existence.
     * @return `true` if the module exists, `false` if the module does not exist.
     */
    public static boolean isValidModule(String moduleCode) {
        try {
            JSONObject moduleInfo = getFullModuleInfo(moduleCode);
            return (!(moduleInfo == null));
        } catch (IOException e) {
            displaySocketError();
            return false;
        }
    }

    /**
     * Retrieves a list of modules from an external API and returns it as a JSONArray.
     * @author rohitcube
     * @return A JSONArray containing module information.
     * @throws RuntimeException If there is an issue with the HTTP request or JSON parsing.
     *
     */
    public static JSONArray listAllModules() {
        try {
            String url = "https://api.nusmods.com/v2/2023-2024/moduleList.json";
            String responseBody = sendHttpRequestAndGetResponseBody(url);
            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(responseBody);
        } catch (URISyntaxException e) {
            System.out.println("Sorry, there was an error with" +
                    " the provided URL: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            displaySocketError();
        } catch (ParseException e) {
            //to be replaced with more robust error class in the future
            System.out.println("Sorry, the JSON object could not be parsed");
        }
        return null;
    }


    /**
     * Executes commands based on user input for module information retrieval.
     * Supports commands: "description", "workload", "all".
     * @author rohitcube
     * @param command   The command provided by the user.
     * @param userInput The user input string containing the command and module code (if applicable).
     *
     */
    public static void infoCommands(String command, String userInput) {
        try {
            if (command.equals("description")) {
                String moduleCode =
                        userInput.substring(userInput.indexOf("description") + 11).trim().toUpperCase();
                if (!Api.getDescription(moduleCode).isEmpty()) {
                    String description = Api.getDescription(moduleCode);
                    System.out.println(Api.wrapText(description, 100));
                }
                //UserError.emptyModuleForInfoCommand(command);
            } else if (command.equals("workload")) {
                String moduleCode = userInput.substring(userInput.indexOf("workload") + 8).trim().toUpperCase();
                if (!Api.getWorkload(moduleCode).isEmpty()) {
                    JSONArray workload = Api.getWorkload(moduleCode);
                    System.out.println(workload);
                }
                //UserError.emptyModuleForInfoCommand(command);
            } else {
                UserError.invalidCommandforInfoCommand();
            }
        } catch (InvalidModuleException e) {
            //  System.out.println("Invalid entry" + e.getMessage());
        } catch (InvalidModuleCodeException e) {
            //  System.out.println(e.getMessage());
        }
    }

    /**
     * Searches for modules containing a specified keyword in their title within a given module list.
     * @author rohitcube
     * @param keyword    The keyword to search for.
     * @param moduleList The list of modules to search within.
     * @return A JSONArray containing modules matching the keyword.
     *
     */
    public static JSONArray search(String keyword, JSONArray moduleList) {
        JSONArray modulesContainingKeyword = new JSONArray();
        if (keyword.isEmpty()) {
            return new JSONArray();
        }
        String[] wordsInKeyword = keyword.split(" ");
        for (int i = 0; i < wordsInKeyword.length; i++) {
            wordsInKeyword[i] = capitalizeFirstLetter(wordsInKeyword[i]);
        }
        String keywordToSearch = String.join(" ", wordsInKeyword);
        for (Object moduleObject : moduleList) {
            JSONObject module = (JSONObject) moduleObject; // Cast to JSONObject
            String title = (String) module.get("title");
            if (title.contains(keywordToSearch)) {
                modulesContainingKeyword.add(module);
                //not sure how to resolve this yellow line
            }
        }
        return modulesContainingKeyword;
    }

    /**
     * Capitalizes the first letter of a given string.
     * @author rohitcube
     * @param input The input string.
     * @return A new string with the first letter capitalized, or the original string if it is null or empty.
     *
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder result = new StringBuilder(input.length());
        result.append(Character.toUpperCase(input.charAt(0)));
        result.append(input.substring(1));
        return result.toString();
    }

    /**
     * Performs a module search and displays the results.
     * <p>
     * This method takes a user input string, extracts keywords from it, performs a search using
     * the API, and displays the results in a structured format.
     *
     * @param userInput The user input string for searching modules.
     */
    public static void searchCommand(String userInput) {
        if (!Parser.isValidKeywordInput(userInput)) {
            UserError.emptyKeywordforSearchCommand();
            return;
        }
        String keywords = userInput.substring(userInput.indexOf("search") + 6);
        JSONArray modulesToPrint = Api.search(keywords, Api.listAllModules());
        if (modulesToPrint.isEmpty()) {
            UserError.emptyArrayforSearchCommand();
            return;
        }
        ModuleInfoView.searchHeader();
        ModuleInfoView.printJsonArray(modulesToPrint);
    }
}
