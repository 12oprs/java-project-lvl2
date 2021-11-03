package hexlet.code.formatters;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import java.util.List;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json {

    private static final String[] PATTERNS = {
                "\\{|.(?=})|},|}\",|[[\\d][]][\\w]],(?=\")|:\".[^}]*?\",(?=\")",
                "\\{\n|\n(?=[^}])|}"
        };

    public static String format(final Map<String, Object> diff) throws Exception {
        String result = "";
        Map<String, List<Map.Entry<String, Object>>> buffer = diff.entrySet().stream()
            .collect(Collectors.groupingBy(entry -> entry.getKey().substring(0, 1)));

        Map<String, Map<String, Object>> diffJsonStyle = new HashMap<>();
        buffer.forEach((k, v) -> {
            Map<String, Object> map = new HashMap<>();
            v.forEach(entry -> map.put(entry.getKey().substring(2), entry.getValue()));
            diffJsonStyle.put(k.toString(), map);
        }
        );

        ObjectMapper mapper = new ObjectMapper();
        result = mapper.writeValueAsString(diffJsonStyle);
        result = markUp(result);
        result = addTabs(result);
        return result;
    }

     private static String markUp(final String target) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile(PATTERNS[0]);
        Matcher m = p.matcher(target);
        while (m.find()) {
            //if (flag == 1)
            m.appendReplacement(result, m.group() + "\n");
            //if (flag == 2) m.appendReplacement(result, m.group() + "\t");

        }
        m.appendTail(result);
        return result.toString();
    }

    private static String addTabs(final String target) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile(PATTERNS[1]);
        Matcher m = p.matcher(target);
        int n = 0;
        while (m.find()) {
            if (m.group().contains("{")) {
                n++;
            } else if (m.group().contains("}")) {
                n--;
                m.appendReplacement(result, "\t".repeat(n) + m.group());
                continue;
            }
            m.appendReplacement(result, m.group() + "\t".repeat(n));

        }
        m.appendTail(result);
        return result.toString();
    }
}
