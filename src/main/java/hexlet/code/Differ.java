package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class Differ {

    public static String generate(final String filepath1, final String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(final String filepath1, final String filepath2, final String formatName) throws Exception {
        Map<String, Object> file1 = readFile(filepath1);
        Map<String, Object> file2 = readFile(filepath2);
        List<Map<String, Object>> diff = findDifferences(file1, file2);
        return Formatter.format(diff, formatName);
    }

    private static Map<String, Object> readFile(final String filepath) throws Exception {
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1);
        Map<String, Object> result = Parser.parse(Files.readString(Paths.get(filepath)), fileType);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            result.replace(entry.getKey(), null, "null");
        }
        return result;
    }

    private static List<Map<String, Object>> findDifferences(
            final Map<String, Object> file1,
            final Map<String, Object> file2) {

        List<Map<String, Object>> diff = new ArrayList<>();
        Set<String> keys = new HashSet(file1.keySet());
        keys.addAll(file2.keySet());

        for (String key : keys) {
            if (!file1.containsKey(key)) {
                diff.add(Map.of(
                    "status", "added",
                    "fieldName", key,
                    "value", file2.get(key)));
            } else if (!file2.containsKey(key)) {
                diff.add(Map.of(
                    "status", "removed",
                    "fieldName", key,
                    "value", file1.get(key)));
            } else if (file1.get(key).equals(file2.get(key))) {
                diff.add(Map.of(
                    "status", "unchanged",
                    "fieldName", key,
                    "value", file1.get(key)));
            } else {
                diff.add(Map.of(
                    "status", "updated",
                    "fieldName", key,
                    "oldValue", file1.get(key),
                    "value", file2.get(key)));
            }
        }
        diff.sort(Comparator.comparing(map -> map.get("fieldName").toString()));
        return diff;
    }
}
