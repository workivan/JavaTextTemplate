package ru.ikuzin.javatexttemplate.calculation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SequenceContainsCalculation implements StatisticsCalculation {
    private final String template;
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
}
