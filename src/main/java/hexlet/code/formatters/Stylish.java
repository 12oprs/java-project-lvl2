package hexlet.code.formatters;

import java.util.Map;

public class Stylish {

    public static String format(final Map<String, Object> diff) {
        StringBuilder result = new StringBuilder();
        diff.forEach((k, v) -> result.append("  " + k + ": " + v + "\n"));
        result.insert(0, "{\n").append("}\n");
        return result.toString();
    }
}
