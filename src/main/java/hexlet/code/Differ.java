package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;

public class Differ {

    public static String generate(final String filepath1, final String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(final String filepath1, final String filepath2,
            final String formatName) throws Exception {

        Map<String, Object> file1 = readFile(filepath1);
        Map<String, Object> file2 = readFile(filepath2);
        List<Map<String, Object>> diff = findDifferences(file1, file2);
        return Formatter.format(diff, formatName);
    }

    private static Map<String, Object> readFile(final String filepath) throws Exception {
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1);
        Map<String, Object> result = Parser.parse(Files.readString(Paths.get(filepath)), fileType);
        return result;
    }

    private static List<Map<String, Object>> findDifferences(
            final Map<String, Object> file1,
            final Map<String, Object> file2) {

        List<Map<String, Object>> diff = new ArrayList<>();
        Set<String> keys = new HashSet(file1.keySet());
        keys.addAll(file2.keySet());

        for (String key : keys) {
            Object value1 = file1.get(key);
            Object value2 = file2.get(key);
            if (!file1.containsKey(key)) {
                diff.add(
                    doEntry("added", key, value2));
            } else if (!file2.containsKey(key)) {
                diff.add(
                    doEntry("removed", key, value1));
            } else if (Objects.equals(value1, value2)) {
                diff.add(
                    doEntry("unchanged", key, value1));
            } else {
                diff.add(
                    doEntry("updated", key, value2, value1));
            }
        }
        diff.sort(Comparator.comparing(map -> map.get("fieldName").toString()));
        return diff;
    }

    private static Map<String, Object> doEntry(
            final String status,
            final String key,
            final Object value,
            final Object oldValue) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("fieldName", key);
        result.put("value",  value);
        result.put("oldValue", oldValue);
        return result;
    }

    private static Map<String, Object> doEntry(
            final String status,
            final String key,
            final Object value) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("fieldName", key);
        result.put("value",  value);
        return result;
    }
}
