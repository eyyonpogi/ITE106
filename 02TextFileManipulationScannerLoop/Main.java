import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main{

    public static void main(String[] args) {
        try {
            File file = new File("eyyonpogi.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder text = new StringBuilder();

            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append(" ");
            }
            scanner.close();

            String content = text.toString();

            WordCounter wordCounter = new WordCounter();
            SentenceCounter sentenceCounter = new SentenceCounter();
            UpperCaseConverter upperCaseConverter = new UpperCaseConverter();
            LongestWordFinder longestWordFinder = new LongestWordFinder();

            int wordCount = wordCounter.countWords(content);
            System.out.println("Number of words: " + wordCount);

            int sentenceCount = sentenceCounter.countSentences(content);
            System.out.println("Number of sentences: " + sentenceCount);

            String upperText = upperCaseConverter.convertToUpperCase(content);
            System.out.println("Uppercase text: " + upperText);

            String longestWord = longestWordFinder.findLongestWord(content);
            System.out.println("Longest word: " + longestWord);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
