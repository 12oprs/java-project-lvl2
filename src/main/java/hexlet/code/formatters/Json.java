package hexlet.code.formatters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json {

    public static String format(final String diff) throws Exception {
        String result = "";
        result = markUp(diff);
        result = sort(result);
        result = addBraces(result);
        result = result.replaceAll("=", ":");
        return result;
    }

    private static String markUp(final String diff) {
        StringBuilder buffer = new StringBuilder();
        Pattern p = Pattern.compile("^\\{|.(?=}$)|},|=[\\[\\w\\d][^}]*?[]\\w\\d],(?=\\s[+\\-\\s])");
        Matcher m = p.matcher(diff);
        while (m.find()) {
            m.appendReplacement(buffer, m.group() + "\n");
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    private static String addBraces(final String diff) {
        StringBuilder buffer = new StringBuilder();
        Pattern p = Pattern.compile("(?!true|false|null)(?<=\\[|,.|=|\\{)[^\\[{\\d,\\]=\n]+\\d*|\\w+(?==)");
        Matcher m = p.matcher(diff);
        while (m.find()) {
            m.appendReplacement(buffer, "\"" + m.group() + "\"");
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    private static String sort(String target) {
        StringBuilder buffer = new StringBuilder();
        String[] temp = target.split("\\n");
        buffer.append("\t\" \":{\n");
        for (String s : temp) {
            if (s.startsWith("  ")) {
                buffer.append("\t\t").append(s.trim()).append("\n");
            }
        }
        if (buffer.charAt(buffer.length() - 2) == ',') {
            buffer.deleteCharAt(buffer.length() - 2);
        }
        buffer.append("\t},\n").append("\t\"+\":{\n");
        for (String s : temp) {
            if (s.startsWith(" + ")) {
                buffer.append("\t\t").append(s.trim().substring(2)).append("\n");
            }
        }
        if (buffer.charAt(buffer.length() - 2) == ',') {
            buffer.deleteCharAt(buffer.length() - 2);
        }
        buffer.append("\t},\n").append("\t\"-\":{\n");
        for (String s : temp) {
            if (s.startsWith(" - ")) {
                buffer.append("\t\t").append(s.trim().substring(2)).append("\n");
            }
        }
        if (buffer.charAt(buffer.length() - 2) == ',') {
            buffer.deleteCharAt(buffer.length() - 2);
        }
        buffer.append("\t}\n");
        buffer.insert(0, "{\n").append("}");
        return buffer.toString();
    }
}
