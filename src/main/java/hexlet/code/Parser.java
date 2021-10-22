package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

class Parser {

    public static Map<String, String> parseFile(final String filepath) throws Exception {
        String source = Files.readString(Paths.get(filepath));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(source, new TypeReference<Map<String, String>>() { });
    }
}
