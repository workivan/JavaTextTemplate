package ru.ikuzin.javatexttemplate.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static ru.ikuzin.javatexttemplate.util.Utils.DELIMITERS;

public class WordsParser {
    public static List<String> fromFile(Stream<String> lines) {
        List<String> words = Collections.synchronizedList(new ArrayList<>());
        lines.parallel().forEach(s -> words.addAll(Arrays.asList(s.trim().split(DELIMITERS))));
        words.removeAll(Collections.singletonList(""));
        return words;
    }
}
