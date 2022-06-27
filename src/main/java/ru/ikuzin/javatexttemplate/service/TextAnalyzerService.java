package ru.ikuzin.javatexttemplate.service;

import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;
import ru.ikuzin.javatexttemplate.parser.TemplatesParser;
import ru.ikuzin.javatexttemplate.parser.WordsParser;
import ru.ikuzin.javatexttemplate.writer.WriterToFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextAnalyzerService {
    public static void analyze(String[] args) {

        final List<String> words;
        Path textPath = Paths.get(args[0]);
        try (Stream<String> lines = Files.lines(textPath)) {
            words = lines.flatMap(WordsParser::fromLine).collect(Collectors.toList());
            if (words.isEmpty()) {
                System.err.println("Text file is empty");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while read from file - " + args[0]);
            return;
        }

        final Set<StatisticsCalculation> templates;
        Path templatesPath = Paths.get(args[1]);
        try (Stream<String> lines = Files.lines(templatesPath)) {
            TemplatesParser parser = new TemplatesParser();
            templates = parser.fromFile(lines);
            if (templates.isEmpty()) {
                System.err.println("Templates file is empty");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while read from file - " + templatesPath);
            return;
        }


        for (String word : words) {
            templates.forEach(template -> template.calculate(word));
        }

        WriterToFile writer = new WriterToFile(args[2]);
        writer.toFile(templates);
    }
}
