package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.TreeMap;

class Differ {

    private String filepath1;
    private String filepath2;
    private String formatType;

    Differ(final String path1, final String path2, String format) {
        this.filepath1 = path1;
        this.filepath2 = path2;
        this.formatType = format;
    }

    public String generate() throws Exception {

        Map<String, Object> file1 = Parser.parseFile(filepath1);
        Map<String, Object> file2 = Parser.parseFile(filepath2);
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

        return formatter(diff);

    }

    private String formatter(Map<String, Object> diff) {
        switch (formatType) {
            case "stylish":
                StringBuilder sb = new StringBuilder();
                diff.forEach((k, v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));
                sb.insert(0, "{\n").append("}\n");
                return sb.toString();
            default:
                return diff.toString();
        }
    }
}
