package hexlet.code.formatters;

import java.util.Map;
import java.util.List;

public class Plain {

    public static String format(final List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();

        for (Map<String, Object> map : diff) {
            String status = map.get("status").toString();
            String name = map.get("fieldName").toString();
            String value = formatValue(map.get("value"));
            String oldValue = map.containsKey("oldValue") ? formatValue(map.get("oldValue")) : "";
            switch (status) {
                case "nochanged":
                    continue;
                case "added":
                    result.append("Property '" + name + "' was added with value: " + value);
                    break;
                case "updated":
                    result.append("Property '" + name + "' was updated. From " + oldValue + " to " + value);
                    break;
                case "removed":
                    result.append("Property '" + name + "' was removed");
                    break;
                default:
                    break;
            }
            result.append("\n");
        }
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    private static String formatValue(final Object o) {
        String result;
        if (o instanceof Number || o instanceof Boolean || o.equals("null")) {
            result = o.toString();
        } else if (o instanceof String) {
            result = "'" + o + "'";
        } else {
            result = "[complex value]";
        }
        return result;
    }
}
