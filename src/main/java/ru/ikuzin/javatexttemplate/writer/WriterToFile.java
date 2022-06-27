package ru.ikuzin.javatexttemplate.writer;

import lombok.RequiredArgsConstructor;
import ru.ikuzin.javatexttemplate.calculation.StatisticsCalculation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class WriterToFile {
    final String path;

    public void toFile(Set<StatisticsCalculation> templates) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(path)){
            for (StatisticsCalculation template: templates){
                fileOutputStream.write(template.toString().getBytes());
                fileOutputStream.write('\n');
            }
        } catch (IOException e) {
            System.err.println("Error while write to file - " + path);
        }
    }
}
