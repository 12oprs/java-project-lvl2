package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

class Parser {

    public static Map<String, Object> parse(final String source, final String fileType) throws Exception {
        ObjectMapper mapper;
        if (fileType.equals("json")) {
            mapper = new ObjectMapper();
        } else {
            mapper = new ObjectMapper(new YAMLFactory());
        }
        return mapper.readValue(source, new TypeReference<Map<String, Object>>() { });
    }
}
