package hexlet.code.formatters;

import java.util.Map;
import java.util.List;

public class Stylish {

    public static String format(final List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();
        for (Map<String, Object> map : diff) {
            String status = map.get("status").toString();
            String name = map.get("fieldName").toString();
            Object value = map.get("value");
            Object oldValue = map.get("oldValue");
            result.append("  ");
            switch (status) {
                case "unchanged":
                    result.append(" ");
                    break;
                case "added":
                    result.append("+");
                    break;
                case "updated":
                    result.append("- " + name + ": " + oldValue + "\n");
                    result.append("  +");
                    break;
                case "removed":
                    result.append("-");
                    break;
                default:
                    break;
            }
            result.append(" " + name)
                .append(": ")
                .append(value)
                .append("\n");
        }
        result.insert(0, "{\n").append("}");
        return result.toString();
    }
}
