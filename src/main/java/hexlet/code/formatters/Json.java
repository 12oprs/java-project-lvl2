package hexlet.code.formatters;

import java.util.Map;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TreeMap;
import java.util.Comparator;

public class Json {

    private static final String PATTERN =
        "((?![-+]?[0-9]*\\.?[0-9]|null|true|false)\\b[^:=\\n\\t,]+\\b)";

    private static Map<String, Object> nochangedMap;
    private static Map<String, Object> addedMap;
    private static Map<String, Object> removedMap;

    public static String format(final List<Map<String, Object>> diff) throws Exception {
        extractData(diff);

        StringBuilder sb = new StringBuilder();
        sb.append("\tadded:{\n");
        addedMap.forEach((k, v) -> sb.append("\t\t" + k + ":" + v + "\n"));
        sb.append("\t}\n");
        sb.append("\tnochanged:{\n");
        nochangedMap.forEach((k, v) -> sb.append("\t\t" + k + ":" + v + "\n"));
        sb.append("\t}\n");
        sb.append("\tremoved:{\n");
        removedMap.forEach((k, v) -> sb.append("\t\t" + k + ":" + v + "\n"));
        sb.append("\t}\n");
        sb.insert(0, "{\n").append("}");
        String result = sb.toString();
        result = strInBraces(result);
        return result;
    }

    private static void extractData(final List<Map<String, Object>> diff) {
        nochangedMap = new TreeMap<>(Comparator.naturalOrder());
        addedMap = new TreeMap<>(Comparator.naturalOrder());
        removedMap = new TreeMap<>(Comparator.naturalOrder());
        for (Map<String, Object> map : diff) {
            String status = map.get("status").toString();
            String name = map.get("fieldName").toString();
            String value = map.get("value").toString();
            String oldValue = map.containsKey("oldValue") ? map.get("oldValue").toString() : "";
            switch (status) {
                case "nochanged":
                    nochangedMap.put(name, value);
                    break;
                case "added":
                    addedMap.put(name, value);
                    break;
                case "updated":
                    addedMap.put(name, value);
                    removedMap.put(name, oldValue);
                    break;
                case "removed":
                    removedMap.put(name, value);
                    break;
                default:
                    break;
            }
        }
    }

    private static String strInBraces(final String target) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(target);
        while (m.find()) {
            m.appendReplacement(result, "\"" + m.group() + "\"");
        }
        m.appendTail(result);
        return result.toString();
    }

}
