package ru.ikuzin.javatexttemplate.parser;

import ru.ikuzin.javatexttemplate.calculation.SequenceContainsCalculation;
import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;
import ru.ikuzin.javatexttemplate.calculation.SymbolRepeatCalculation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ru.ikuzin.javatexttemplate.util.Utils.DELIMITERS;

public class TemplatesParser {
    private final String ERROR_TEXT = "Problem while parse template- ";
    private final Pattern quotes = Pattern.compile("\"([^\"]*)\"");
    private final Pattern separators = Pattern.compile(DELIMITERS);

    public Set<StatisticsCalculation> fromFile(Stream<String> lines) {
        final Set<StatisticsCalculation> calculations = Collections.synchronizedSet(new HashSet<>());
        lines.parallel().forEach(s -> {
            if (s.startsWith("\"")) {
                Matcher m = quotes.matcher(s);
                if (m.find()) {
                    calculations.add(new SequenceContainsCalculation(m.group(1)));
                } else {
                    System.err.println(ERROR_TEXT + s);
                }
            } else {
                Matcher m = separators.matcher(s);
                if (m.find()) {
                    System.err.println(ERROR_TEXT + s);
                    return;
                }
                if (s.length() % 2 != 0) {
                    System.err.println(ERROR_TEXT + s);
                    return;
                }

                final Map<Character, Integer> template = new HashMap<>();
                for (int i = 0; i < s.length(); i = i + 2) {
                    boolean isDigit = Character.isDigit(s.charAt(i + 1));
                    if (isDigit) {
                        template.put(s.charAt(i), Character.getNumericValue(s.charAt(i + 1)));
                    } else {
                        System.err.println(ERROR_TEXT + s);
                        return;
                    }
                }
                calculations.add(new SymbolRepeatCalculation(template));
            }
        });
        return calculations;
    }
}
