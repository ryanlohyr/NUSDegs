package seedu.duke.models;

public class JsonFormatter {
    public static String formatJson(String jsonString) {
        int indentLevel = 0;
        StringBuilder formattedJson = new StringBuilder();

        for (char charFromJson : jsonString.toCharArray()) {
            if (charFromJson == '{' || charFromJson == '[') {
                indentLevel++;
                formattedJson.append(charFromJson).append("\n").append(getIndentString(indentLevel));
            } else if (charFromJson == '}' || charFromJson == ']') {
                indentLevel--;
                formattedJson.append("\n").append(getIndentString(indentLevel)).append(charFromJson);
            } else if (charFromJson == ',') {
                formattedJson.append(charFromJson).append("\n").append(getIndentString(indentLevel));
            } else {
                formattedJson.append(charFromJson);
            }
        }

        return formattedJson.toString();
    }

    private static String getIndentString(int indentLevel) {
        StringBuilder indentString = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indentString.append("\t"); // Use tabs for indentation (you can change this to spaces if desired)
        }
        return indentString.toString();
    }
}







