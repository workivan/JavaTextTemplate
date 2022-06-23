package ru.ikuzin.javatexttemplate.calculation;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SymbolRepeatCalculation implements StatisticsCalculation {
    private final Map<Character, Integer> template;
    private int count;

    @Override
    public void calculate(String word) {
        final Map<Character, Integer> collect = word.chars().parallel().mapToObj(i -> (char) i).collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum));
        for (Map.Entry<Character, Integer> config : template.entrySet()) {
            final Integer value = collect.get(config.getKey());
            if (value == null || value.compareTo(config.getValue()) < 0) {
                return;
            }
        }
        count++;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Character, Integer> config : template.entrySet()) {
            builder.append(config.getKey());
            builder.append(config.getValue());
        }
        return String.format("%s %o", builder, count);
    }
}
