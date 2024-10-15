public class SentenceCounter {
    public int countSentences(String text) {
        int sentenceCount = 0;
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '.' || c == '!' || c == '?') {
                sentenceCount++;
            }
        }
        return sentenceCount;
    }
}
