public class LongestWordFinder {
    public String findLongestWord(String text) {
        String[] words = text.split("\\s+");
        String longestWord = "";

        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord;
    }
