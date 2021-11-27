package hexlet.code.formatters;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.Comparator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static Map<String, Object> unchangedMap;
    private static Map<String, Object> addedMap;
    private static Map<String, Object> removedMap;

    public static String format(final List<Map<String, Object>> diff) throws Exception {
        extractData(diff);
        Map<String, Map<String, Object>> diffMap = new TreeMap<>(Comparator.naturalOrder());
        diffMap.put("added", addedMap);
        diffMap.put("removed", removedMap);
        diffMap.put("unchanged", unchangedMap);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(diffMap);
        return result;
    }

    private static void extractData(final List<Map<String, Object>> diff) {
        unchangedMap = new TreeMap<>(Comparator.naturalOrder());
        addedMap = new TreeMap<>(Comparator.naturalOrder());
        removedMap = new TreeMap<>(Comparator.naturalOrder());
        for (Map<String, Object> map : diff) {
            String status = map.get("status").toString();
            String name = map.get("fieldName").toString();
            String value = String.valueOf(map.get("value"));
            String oldValue = map.containsKey("oldValue") ? String.valueOf(map.get("oldValue")) : "";
            switch (status) {
                case "unchanged":
                    unchangedMap.put(name, value);
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
}
