package ru.ikuzin.javatexttemplate.parser;

import java.util.stream.Stream;


public class WordsParser {
    private static final String DELIMITERS = " ,!?.:;()[]—";

    public static Stream<String> fromLine(String str) {
        Stream.Builder<String> words = Stream.builder();
        StringBuilder word = new StringBuilder();
        for (Character ch : str.toCharArray()) {
            if (DELIMITERS.contains(String.valueOf(ch))) {
                if (word.length() != 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(ch);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words.build();
    }
}