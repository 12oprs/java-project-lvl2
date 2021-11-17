package hexlet.code.formatters;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.Comparator;

public class Json {

    private static Map<String, Object> nochangedMap;
    private static Map<String, Object> addedMap;
    private static Map<String, Object> removedMap;

    public static String format(final List<Map<String, Object>> diff) throws Exception {
        extractData(diff);

        StringBuilder sb = new StringBuilder();
        sb.append("\t\"added\":{\n");
        addedMap.forEach((k, v) -> sb.append("\t\t" + inBracesIfNeed(k) + ":" + inBracesIfNeed(v) + ",\n"));
        sb.deleteCharAt(sb.length() - 2).append("\t},\n");
        sb.append("\t\"nochanged\":{\n");
        nochangedMap.forEach((k, v) -> sb.append("\t\t" + inBracesIfNeed(k) + ":" + inBracesIfNeed(v) + ",\n"));
        sb.deleteCharAt(sb.length() - 2).append("\t},\n");
        sb.append("\t\"removed\":{\n");
        removedMap.forEach((k, v) -> sb.append("\t\t" + inBracesIfNeed(k) + ":" + inBracesIfNeed(v) + ",\n"));
        sb.deleteCharAt(sb.length() - 2).append("\t}\n");
        sb.insert(0, "{\n").append("}");
        String result = sb.toString();
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


    private static String inBracesIfNeed(final Object target) {
        String result = target.toString();
        if (!result.equals("null")
                && !result.equals("true")
                && !result.equals("false")) {
            try {
                double temp = Double.parseDouble(result);
            } catch (NumberFormatException e) {
                return "\"" + result + "\"";
            }
            return result;
        }
        return result;
    }
}
