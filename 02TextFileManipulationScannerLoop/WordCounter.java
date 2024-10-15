public class WordCounter {
    public int countWords(String text) {
        String[] words = text.split("\\s+");
        int wordCount = 0;

        for (String word : words) {
            if (!word.trim().isEmpty()) {
                wordCount++;
            }
        }
        return wordCount;
    }
