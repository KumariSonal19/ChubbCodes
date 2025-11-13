import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileFunctional {

    public static void main(String[] args) {

        String filename = "src/Data.txt";  

        try {
            long count = Files.lines(Path.of(filename)).map(String::toLowerCase).flatMap(line -> Arrays.stream(line.split("\\W+"))).filter(word -> word.equals("india")).count();

            System.out.println("Total count of the word 'India':" + count);

        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
    }
}