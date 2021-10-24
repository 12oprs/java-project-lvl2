package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;
import java.io.File;

class Parser {

    public static Map<String, Object> parseFile(final String filepath) throws Exception {
        File source = new File(filepath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(source, new TypeReference<Map<String, Object>>() { });
    }
}
