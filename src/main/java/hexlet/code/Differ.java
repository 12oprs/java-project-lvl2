package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
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

    private Map<String, String> parseFile(final String filepath) throws Exception {
        String jsonSource = Files.readString(Paths.get(filepath));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> results = mapper.readValue(jsonSource, new TypeReference<Map<String, String>>() { });
        return results;
    }

    public String generate() throws Exception {

        Map<String, String> json1 = parseFile(filepath1);
        Map<String, String> json2 = parseFile(filepath2);
        Map<String, String> diff = new TreeMap<>(Comparator.comparing((key) -> key.toString().substring(1))
                    .thenComparing((key) -> key.toString().substring(0, 1), Comparator.reverseOrder()));

        for (Map.Entry<String, String> entry : json1.entrySet()) {
            if (json2.containsKey(entry.getKey())) {
                if (json2.get(entry.getKey()).equals(entry.getValue())) {
                    diff.put("  " + entry.getKey(), entry.getValue());
                } else {
                    diff.put("- " + entry.getKey(), entry.getValue());
                    diff.put("+ " + entry.getKey(), json2.get(entry.getKey()));
                }
                json2.remove(entry.getKey());
            } else {
                diff.put("- " + entry.getKey(), entry.getValue());
            }
        }
        json2.forEach((k, v) -> diff.put("+ " + k, v));

        StringBuilder sb = new StringBuilder();
        diff.forEach((k, v) -> sb.append(k + ": " + v + "\n"));
        sb.insert(0, "{\n").append("}\n");
        return sb.toString();

    }


}
