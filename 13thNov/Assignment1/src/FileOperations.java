import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileOperations {

    public static void main(String[] args) {
        String filename = "src/Data.txt";
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();

                String[] words = line.split("[^a-zA-Z]+");

                for (String word : words) {
                    if (word.isEmpty()) continue;

                    if (word.equals("india")) {
                        count++;
                    }
                }
            }
            System.out.println("The total count of the word India is: " + count);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}