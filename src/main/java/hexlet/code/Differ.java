package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.TreeMap;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Differ {

    public static String generate(final String filepath1, final String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(final String filepath1, final String filepath2, String formatName) throws Exception {

        Map<String, Object> file1 = Parser.parseFile(
            new String(
                Files.readAllBytes(
                    Paths.get(filepath1))));
        Map<String, Object> file2 = Parser.parseFile(
            new String(
                Files.readAllBytes(
                    Paths.get(filepath2))));

        Map<String, Object> diff = new TreeMap<>(Comparator.comparing((key) -> key.toString().substring(1))
                    .thenComparing((key) -> key.toString().substring(0, 1), Comparator.reverseOrder()));

        for (Map.Entry<String, Object> entry : file1.entrySet()) {
            if (file2.containsKey(entry.getKey())) {
                if (file2.get(entry.getKey()) == null) {
                    file2.replace(entry.getKey(), "null");
                }
                if (file2.get(entry.getKey()).equals(entry.getValue())) {
                    diff.put("  " + entry.getKey(), entry.getValue());
                } else {
                    diff.put("- " + entry.getKey(), entry.getValue());
                    diff.put("+ " + entry.getKey(), file2.get(entry.getKey()));
                }
                file2.remove(entry.getKey());
            } else {
                diff.put("- " + entry.getKey(), entry.getValue());
            }
        }
        file2.forEach((k, v) -> diff.put("+ " + k, v));

        return Formatter.format(diff, formatName);
    }
}
