package hexlet.code.formatters;

import java.util.Map;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    public static String format(final List<Map<String, Object>> diff) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(diff);
        return result.substring(1, result.length() - 1);
    }
}
