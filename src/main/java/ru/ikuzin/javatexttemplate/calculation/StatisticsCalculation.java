package ru.ikuzin.javatexttemplate.calculation;

public interface StatisticsCalculation {
    void calculate(String word);

    @Override
    String toString();

    @Override
    boolean equals(Object obj);
}
