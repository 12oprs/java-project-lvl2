package hexlet.code.formatters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stylish {

    public static String format(final String diff) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile("=.+?,(?=\\s[\\+\\-\\s])|=.+?}$|^\\{\\s");
        Matcher m = p.matcher(diff);
        while (m.find()) {
            m.appendReplacement(result, m.group().substring(0, m.group().length() - 1).replaceFirst("=", ": ") + "\n ");
        }
        m.appendTail(result);
        result.insert(2, "  ").deleteCharAt(result.length() - 1).append("}\n");
        return result.toString();
    }
}
