package hexlet.code.formatters;

import java.util.Map;
import java.util.List;

public class Plain {

    private static final String TEMPLATE_ADDED = "Property '%s' was added with value: %s";
    private static final String TEMPLATE_REMOVED = "Property '%s' was removed";
    private static final String TEMPLATE_UPDATED = "Property '%s' was updated. From %s to %s";

    public static String format(final List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();

        for (Map<String, Object> map : diff) {
            String status = map.get("status").toString();
            String name = map.get("fieldName").toString();
            String value = formatValue(map.get("value"));
            String oldValue = map.containsKey("oldValue") ? formatValue(map.get("oldValue")) : "";
            switch (status) {
                case "unchanged":
                    continue;
                case "added":
                    result.append(TEMPLATE_ADDED.formatted(name, value));
                    break;
                case "updated":
                    result.append(TEMPLATE_UPDATED.formatted(name, oldValue, value));
                    break;
                case "removed":
                    result.append(TEMPLATE_REMOVED.formatted(name));
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
