package hexlet.code.formatters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Plain {

    public static String format(final String diff) {
        StringBuilder buffer = new StringBuilder();
        Pattern p = Pattern.compile("(?<sign>[+-])\\s(?<key>[\\d\\w]+?)=(?<value>.+?)(?=,\\s[-+\\s]|}$)");
        Matcher m = p.matcher(diff); //.replaceAll("\\s\\s.+?,\\s(?=\\s|[+-])", ""));

        String oldKey = "";
        String oldValue = "";
        String key = "";
        String value = "";
        String sign = "";
        boolean isNotAdded = false;

        while (m.find()) {
            sign = m.group("sign");
            key = m.group("key");
            value = m.group("value");
            if (sign.equals("-")) {
                oldKey = key;
                oldValue = value;
                isNotAdded = true;
            }
            if (sign.equals("+") && key.equals(oldKey)) {
                buffer.append("Property '" + key + "' was updated.")
                    .append(" From '" + oldValue + "' to '" + value + "'\n");
                continue;
            } else if (sign.equals("+")) {
                if (isNotAdded) {
                    buffer.append("Property '" + oldKey + "' was removed\n");
                    isNotAdded = false;
                }
                buffer.append("Property '" + key + "' was added with value: '" + value + "'\n");
            }
        }
        String result = buffer.toString()
            .replaceAll("'[\\[\\{].*?[]}]'", "[complex value]")
            .replaceAll("(?<=From |to )'(?=true|false|null)|(?<=true|false|null)'(?= to|\n)", "");
        result = removeBracesFromInt(result);
        return result;
    }

    private static String removeBracesFromInt(final String diff) {
        StringBuilder buffer = new StringBuilder();
        Pattern p = Pattern.compile("'\\d+?'");
        Matcher m = p.matcher(diff);
        while (m.find()) {
            m.appendReplacement(buffer, m.group().substring(1, m.group().length() - 1));
        }
        m.appendTail(buffer);
        return buffer.toString();
    }
}
