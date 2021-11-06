package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Json;

class Formatter {

    public static String format(final String diff, String formatName) throws Exception {
        String result;
        switch (formatName) {
            case "plain":
                result = Plain.format(diff);
                break;
            case "no-format":
                result = diff;
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
