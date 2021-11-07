package hexlet.code.formatters;

import java.util.Map;

public class Plain {

    public static String format(final Map<String, Object> diff) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Object> entry : diff.entrySet()) {
            String currentKey = entry.getKey().substring(2);
            String currentValue = formatValue(entry.getValue());
            String previosValue = formatValue(diff.get("-".concat(entry.getKey().substring(1))));
            Boolean updated = entry.getKey().startsWith("+")
                && diff.containsKey("-".concat(entry.getKey().substring(1)));
            Boolean added = entry.getKey().startsWith("+");
            Boolean removed = (entry.getKey().startsWith("-")
                && !diff.containsKey("+".concat(entry.getKey().substring(1))));

            if (updated) {
                result.append("Property '" + currentKey + "' was updated. From ")
                    .append(previosValue + " to " + currentValue + "\n");
                continue;
            }
            if (added) {
                result.append("Property '" + currentKey + "' was added with value: ")
                    .append(currentValue + "\n");
                continue;
            }
            if (removed) {
                result.append("Property '" + currentKey + "' was removed" + "\n");
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private static String formatValue(final Object o) {
        String result;
        if (o == null) {
            result = "null";
        } else if (o instanceof Number || o instanceof Boolean || o.equals("null")) {
            result = o.toString();
        } else if (o instanceof String) {
            result = "'" + o + "'";
        } else {
            result = "[complex value]";
        }
        return result;
    }

}
