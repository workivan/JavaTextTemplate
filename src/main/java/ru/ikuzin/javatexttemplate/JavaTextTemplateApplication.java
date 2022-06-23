package ru.ikuzin.javatexttemplate;

import ru.ikuzin.javatexttemplate.service.TextAnalyzerService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JavaTextTemplateApplication {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return;
        }

        final Set<String> setOfPaths = new HashSet<>(Arrays.asList(args));
        if (setOfPaths.size() != 3) {
            System.err.println("Duplicate links");
            return;
        }

        for (String arg : args) {
            if (!Files.exists(Paths.get(arg))) {
                System.err.println("File does not exist" + arg);
                return;
            }
        }

        TextAnalyzerService.analyze(args);

    }
}
