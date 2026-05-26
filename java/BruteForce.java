import java.util.HashSet;
import java.util.Set;

public class BruteForce {
    private final Cipher cipher;
    private final char[] alphabet;
    private final Set<Character> alphabetSet;

    public BruteForce(Cipher cipher, char[] alphabet) {
        this.cipher = cipher;
        this.alphabet = alphabet;
        this.alphabetSet = new HashSet<>();
        for (char c : alphabet) {
            alphabetSet.add(c);
        }
    }

    public String decryptWithSample(String encryptedText, String sampleText) {
        String filteredSample = filterToAlphabet(sampleText);

        String bestResult = "";
        int maxMatches = -1;

        for (int key = 1; key < alphabet.length; key++) {
            try {
                String decrypted = cipher.decrypt(encryptedText, key);
                int matches = countCommonWords(decrypted, filteredSample);
                if (matches > maxMatches) {
                    maxMatches = matches;
                    bestResult = decrypted;
                }
            } catch (Exception e) {
            }
        }

        if (bestResult.isEmpty()) {
            throw new RuntimeException("Не удалось подобрать ключ перебором с образцом.");
        }
        return bestResult;
    }

    public String decryptWithoutSample(String encryptedText) {
        String bestResult = "";
        int maxSpaces = -1;

        for (int key = 1; key < alphabet.length; key++) {
            try {
                String decrypted = cipher.decrypt(encryptedText, key);
                int spaces = countChar(decrypted, ' ');
                if (spaces > maxSpaces) {
                    maxSpaces = spaces;
                    bestResult = decrypted;
                }
            } catch (Exception e) {
            }
        }

        if (bestResult.isEmpty()) {
            throw new RuntimeException("Не удалось подобрать ключ перебором без образца.");
        }
        return bestResult;
    }

    private String filterToAlphabet(String text) {
        String lower = text.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if (alphabetSet.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private int countChar(String text, char target) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) count++;
        }
        return count;
    }

    private int countCommonWords(String decrypted, String filteredSample) {
        String[] words1 = decrypted.split(" ");
        String[] words2 = filteredSample.split(" ");
        int common = 0;
        for (String w1 : words1) {
            for (String w2 : words2) {
                if (w1.equals(w2)) {
                    common++;
                    break;
                }
            }
        }
        return common;
    }
}