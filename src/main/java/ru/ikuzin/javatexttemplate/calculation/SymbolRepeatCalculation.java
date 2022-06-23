package ru.ikuzin.javatexttemplate.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SymbolRepeatCalculation implements StatisticsCalculation {
    private final Map<Character, Integer> template;
    @Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SymbolRepeatCalculation)) return false;

        SymbolRepeatCalculation that = (SymbolRepeatCalculation) o;
        return template.equals(that.template);
    }

    @Override
    public int hashCode() {
        int result = template.hashCode();
        result = 31 * result + count;
        return result;
    }
}
