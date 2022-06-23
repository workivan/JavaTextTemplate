package ru.ikuzin.javatexttemplate;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ikuzin.javatexttemplate.calculation.SequenceContainsCalculation;
import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;
import ru.ikuzin.javatexttemplate.calculation.SymbolRepeatCalculation;
import ru.ikuzin.javatexttemplate.parser.TemplatesParser;
import ru.ikuzin.javatexttemplate.parser.WordsParser;
import ru.ikuzin.javatexttemplate.service.TextAnalyzerService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JavaTextTemplateApplicationTests {
    final static String templateS = "ми";
    final static Map<Character, Integer> templateM = new HashMap<>();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    public static void initFields() {
        templateM.put('т', 1);
        templateM.put('о', 1);
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    public void testSequenceCalculate() {
        SequenceContainsCalculation calc = new SequenceContainsCalculation(templateS);
        calc.calculate("миии");
        assertEquals(calc.getCount(), 1);
        calc.calculate("ииим");
        assertEquals(calc.getCount(), 1);
    }

    @Test
    public void testSequenceCalculateEquals() {
        SequenceContainsCalculation calc1 = new SequenceContainsCalculation(templateS);
        SequenceContainsCalculation calc2 = new SequenceContainsCalculation(templateS);
        SequenceContainsCalculation calc3 = new SequenceContainsCalculation(templateS + "sfdf");
        assertEquals(calc1, calc2);
        assertNotEquals(calc1, calc3);
    }

    @Test
    public void testRepeatCalculate() {
        SymbolRepeatCalculation calc = new SymbolRepeatCalculation(templateM);
        calc.calculate("отец");
        assertEquals(calc.getCount(), 1);
    }

    @Test
    public void testRepeatCalculateEquals() {
        SymbolRepeatCalculation calc1 = new SymbolRepeatCalculation(templateM);
        SymbolRepeatCalculation calc2 = new SymbolRepeatCalculation(templateM);
        assertEquals(calc1, calc2);
        calc1.calculate("отец");
        assertEquals(calc1, calc2);
    }

    @Test
    public void testTemplatesParserCorrect() {
        TemplatesParser parser = new TemplatesParser();
        final Set<StatisticsCalculation> templates = parser.fromFile(Stream.of("\"фа\"", "т1о1"));
        assertEquals(templates.size(), 2);
    }

    @Test
    public void testTemplatesParserNotCorrect() {
        TemplatesParser parser = new TemplatesParser();
        final Set<StatisticsCalculation> templates = parser.fromFile(Stream.of(
                "\"фа", "т1о1 hghfgh", "т4л", "т5дн"));
        assertEquals(templates.size(), 0);
    }

    @Test
    public void testWordsParserCorrect() {
        final List<String> words = WordsParser.fromFile(Stream.of(
                "ghbdt ,jkmit gdfgdfg", "gfdfg dfgdfg dfg2342 dfg>/. fsdf", " fsdf "
        ));
        assertEquals(words.size(), 9);
    }

    @Test
    public void testTextAnalyzerEmptyText() {
        String[] args = new String[3];
        args[0] = System.getenv("toResourcesPart") + "resources/Empty.txt";
        args[1] = System.getenv("toResourcesPart") +  "resources/templates.txt";
        args[2] = System.getenv("toResourcesPart") + "resources/output.txt";
        TextAnalyzerService.analyze(args);
        assertEquals("Text file is empty\n", errContent.toString());
    }

    @Test
    public void testTextAnalyzerEmptyTempaltes() {
        String[] args = new String[3];
        args[0] = System.getenv("toResourcesPart") +  "resources/text.txt";
        args[1] = System.getenv("toResourcesPart") +  "resources/Empty.txt";
        args[2] = System.getenv("toResourcesPart") + "resources/output.txt";
        TextAnalyzerService.analyze(args);
        assertEquals("Templates file is empty\n", errContent.toString());
    }


}
