package hexlet.code;

import java.util.Map;
import java.util.Comparator;
import java.util.TreeMap;

class Differ {

    private String filepath1;
    private String filepath2;

    Differ(final String path1, final String path2) {
        this.filepath1 = path1;
        this.filepath2 = path2;
    }

    public String generate() throws Exception {

        Map<String, String> file1 = Parser.parseFile(filepath1);
        Map<String, String> file2 = Parser.parseFile(filepath2);
        Map<String, String> diff = new TreeMap<>(Comparator.comparing((key) -> key.toString().substring(1))
                    .thenComparing((key) -> key.toString().substring(0, 1), Comparator.reverseOrder()));

        for (Map.Entry<String, String> entry : file1.entrySet()) {
            if (file2.containsKey(entry.getKey())) {
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

        StringBuilder sb = new StringBuilder();
        diff.forEach((k, v) -> sb.append(k + ": " + v + "\n"));
        sb.insert(0, "{\n").append("}\n");
        return sb.toString();

    }


}
