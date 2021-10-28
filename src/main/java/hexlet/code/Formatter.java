package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import java.util.Map;

class Formatter {

    public static String format(Map<String, Object> diff, String formatName) {
        String result = new String();
        switch (formatName) {
            case "plain":
                result = Plain.format(diff);
                break;
            case "no-format":
                result = diff.toString();
                break;
            default:
                result = Stylish.format(diff);
        }
        return result;
    }
}