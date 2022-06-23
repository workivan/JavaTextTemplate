package ru.ikuzin.javatexttemplate.writer;

import lombok.RequiredArgsConstructor;
import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
public class WriterToFile {
    final Path path;

    public void toFile(StatisticsCalculation result) {
        try {
            Files.write(path, (result.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error while write to file - " + path);
        }
    }
}
