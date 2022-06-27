package ru.ikuzin.javatexttemplate.parser;

import ru.ikuzin.javatexttemplate.calculation.SequenceContainsCalculation;
import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;
import ru.ikuzin.javatexttemplate.calculation.SymbolRepeatCalculation;

import java.util.*;
import java.util.stream.Stream;


public class TemplatesParser {
    private final String ERROR_TEXT = "Problem while parse template- ";


    public Set<StatisticsCalculation> fromFile(Stream<String> lines) {
        final Set<StatisticsCalculation> calculations = new HashSet<>();
        lines.forEach(s -> {
            s = s.replaceAll(" ", "");
            if (s.startsWith("\"")) {
                LinkedList<Integer> indexesOfQuote = new LinkedList<>();
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '\"') {
                        indexesOfQuote.add(i);
                    }
                }
                if (indexesOfQuote.size() == 1) {
                    System.err.println("template" + s + " does not contain a closing quote");
                    return;
                }
                String pattern = s.substring(indexesOfQuote.getFirst() + 1, indexesOfQuote.getLast()); // + 1 used for skip \"
                calculations.add(new SequenceContainsCalculation(pattern));
            } else {
                if (s.length() % 2 != 0) {
                    System.err.println("template " + s + " is incorrect");
                    return;
                }
                Map<Character, Integer> template = new HashMap<>();
                for (int i = 0; i < s.length(); i = i + 2) {
                    char character = s.charAt(i);
                    if (!Character.isDigit(s.charAt(i + 1))) {
                        System.err.println("template " + character + "" + s.charAt(i + 1) + "" +
                                " does not contain the number of required repetitions");
                    } else {
                        int integer = Character.getNumericValue(s.charAt(i + 1));
                        template.put(character, integer);
                    }
                }
                calculations.add(new SymbolRepeatCalculation(template));
            }
        });
        return calculations;
    }
}
