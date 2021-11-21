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
            case "stylish":
                return Stylish.format(diff);
            case "plain":
                return Plain.format(diff);
            case "no-format":
                return diff.toString();
            case "json":
                return Json.format(diff);
            default:
                throw new Exception("Unknown format type");
        }
    }
}
