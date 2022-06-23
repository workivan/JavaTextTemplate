package ru.ikuzin.javatexttemplate.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SequenceContainsCalculation implements StatisticsCalculation {
    private final String template;
    @Getter
    private int count;

    @Override
    public void calculate(String word) {
        if (word.contains(template)) {
            count++;
        }
    }

    @Override
    public String toString() {
        return String.format("\"%s\" %o", template, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SequenceContainsCalculation)) return false;

        SequenceContainsCalculation that = (SequenceContainsCalculation) o;
        return template.equals(that.template);
    }

    @Override
    public int hashCode() {
        int result = template.hashCode();
        result = 31 * result + count;
        return result;
    }
}
