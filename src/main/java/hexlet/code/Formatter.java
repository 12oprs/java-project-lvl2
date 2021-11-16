package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Json;
import java.util.Map;
import java.util.List;

class Formatter {

    public static String format(final List<Map<String, Object>> diff, String formatName) throws Exception {
        String result = new String();
        switch (formatName) {
            case "plain":
                result = Plain.format(diff);
                break;
            case "no-format":
                result = diff.toString();
                break;
            case "json":
                result = Json.format(diff);
                break;
            default:
                result = Stylish.format(diff);
        }
        return result;
    }
}
