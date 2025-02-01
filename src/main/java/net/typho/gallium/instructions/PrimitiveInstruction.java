package net.typho.gallium.instructions;

import net.typho.gallium.Instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitiveInstruction implements Instruction {
    public final Object value;

    public PrimitiveInstruction(Object value) {
        this.value = value;
    }

    public PrimitiveInstruction(String value) {
        this.value = parse(value);
    }

    public static Object parse(String input) {
        if (input.matches("^'.*'$")) {
            return parseString(input.substring(1, input.length() - 1)).charAt(0);
        }

        if (input.matches("^\".*\"$")) {
            return parseString(input.substring(1, input.length() - 1));
        }

        return parseNumber(input);
    }

    private static String parseString(String input) {
        StringBuilder result = new StringBuilder();
        Matcher matcher = Pattern.compile("\\\\u[0-9a-fA-F]{4}|\\\\[btnfr\"'\\\\]").matcher(input);
        int lastEnd = 0;

        while (matcher.find()) {
            result.append(input, lastEnd, matcher.start());

            String match = matcher.group();

            switch (match) {
                case "\\b":
                    result.append('\b');
                    break;
                case "\\t":
                    result.append('\t');
                    break;
                case "\\n":
                    result.append('\n');
                    break;
                case "\\f":
                    result.append('\f');
                    break;
                case "\\r":
                    result.append('\r');
                    break;
                case "\\\"":
                    result.append('\"');
                    break;
                case "\\'":
                    result.append('\'');
                    break;
                case "\\\\":
                    result.append('\\');
                    break;
                default:
                    result.append((char) Integer.parseInt(match.substring(2), 16));
                    break;
            }

            lastEnd = matcher.end();
        }

        result.append(input.substring(lastEnd));

        return result.toString();
    }

    private static Number parseNumber(String input) {
        if (input.charAt(0) == '0' && input.length() > 1) {
            return Long.parseLong(input.substring(1), 8);
        }

        if (input.length() > 2) {
            String start = input.substring(0, 2);

            switch (start) {
                case "0b":
                case "0B":
                    return Long.parseLong(input.substring(2), 2);
                case "0x":
                case "0X":
                    return Long.parseLong(input.substring(2), 16);
            }
        }

        if (input.contains(".")) {
            return Double.parseDouble(input.replace("_", ""));
        }

        return Long.parseLong(input.replace("_", ""));
    }

    @Override
    public Object invoke() {
        return value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + value + "]";
    }
}
