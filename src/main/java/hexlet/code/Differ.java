package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.TreeMap;

class Differ {

    // private String filepath1;
    // private String filepath2;
    private String formatType;

    // Differ(final String path1, final String path2, String format) {
    //     this.filepath1 = path1;
    //     this.filepath2 = path2;
    //     this.formatType = format;
    // }

    public String generate(final String filepath1, final String filepath2, String formatName) throws Exception {
        this.formatType = formatName;
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
        StringBuilder sb = new StringBuilder();
        switch (formatType) {
            case "plain":
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
                        sb.append("Property '" + currentKey + "' was updated. From ")
                            .append(previosValue + " to " + currentValue + "\n");
                        continue;
                    }
                    if (added) {
                        sb.append("Property '" + currentKey + "' was added with value: ")
                            .append(currentValue + "\n");
                        continue;
                    }
                    if (removed) {
                        sb.append("Property '" + currentKey + "' was removed" + "\n");
                    }
                }
                break;
            case "no-format":
                sb.append(diff.toString());
                break;
            default:
                diff.forEach((k, v) -> sb.append("  " + k + ": " + v + "\n"));
                sb.insert(0, "{\n").append("}\n");
        }
        return sb.toString();
    }

    private String formatValue(Object o) {
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
