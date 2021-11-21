package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class Differ {

    public static String generate(final String filepath1, final String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(final String filepath1, final String filepath2, String formatName) throws Exception {
        Map<String, Object> file1 = readFile(filepath1);
        Map<String, Object> file2 = readFile(filepath2);
        List<Map<String, Object>> diff = findDifferences(file1, file2);
        return Formatter.format(diff, formatName);
    }

    private static Map<String, Object> readFile(final String filepath) throws Exception {
        return Parser.parseFile(Files.readString(Paths.get(filepath)));
    }

    private static List<Map<String, Object>> findDifferences(
            final Map<String, Object> file1,
            final Map<String, Object> file2) {

        List<Map<String, Object>> diff = new ArrayList<>();
        for (Map.Entry<String, Object> entry : file1.entrySet()) {
            if (file2.containsKey(entry.getKey())) {
                if (file2.get(entry.getKey()) == null) {
                    file2.replace(entry.getKey(), "null");
                }
                if (entry.getValue() == null) {
                    file1.replace(entry.getKey(), "null");
                }
                if (file2.get(entry.getKey()).equals(entry.getValue())) {
                    diff.add(Map.of(
                            "status", "nochanged",
                            "fieldName", entry.getKey(),
                            "value", entry.getValue()));
                } else {
                    diff.add(Map.of(
                            "status", "updated",
                            "fieldName", entry.getKey(),
                            "oldValue", entry.getValue(),
                            "value", file2.get(entry.getKey())));
                }
                file2.remove(entry.getKey());
            } else {
                diff.add(Map.of(
                        "status", "removed",
                        "fieldName", entry.getKey(),
                        "value", entry.getValue()));
            }
        }
        file2.forEach((k, v) -> diff.add(Map.of(
                "status", "added",
                "fieldName", k,
                "value", v)));
        diff.sort(Comparator.comparing(map -> map.get("fieldName").toString()));
        return diff;
    }
}
