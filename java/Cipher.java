import java.util.HashMap;
import java.util.Map;

public class Cipher {
    private final char[] alphabet;
    private final Map<Character, Integer> charToIndex;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
        this.charToIndex = new HashMap<>();
        for (int i = 0; i < alphabet.length; i++) {
            charToIndex.put(alphabet[i], i);
        }
    }

    private char shiftChar(char ch, int key) {
        Integer index = charToIndex.get(ch);   // O(1) вместо цикла
        if (index == null) return ch;          // нет в алфавите – возвращаем как есть
        int newIndex = (index + key) % alphabet.length;
        if (newIndex < 0) {
            newIndex += alphabet.length;
        }
        return alphabet[newIndex];
    }

    public String encrypt(String text, int key) {
        String lowerText = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lowerText.length(); i++) {
            char original = lowerText.charAt(i);
            if (charToIndex.containsKey(original)) {
                result.append(shiftChar(original, key));
            }
        }
        return result.toString();
    }

    public String decrypt(String text, int key) {
        String lowerText = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lowerText.length(); i++) {
            char original = lowerText.charAt(i);
            if (charToIndex.containsKey(original)) {
                result.append(shiftChar(original, -key));
            } else {
                throw new IllegalArgumentException(
                        "В зашифрованном тексте найден недопустимый символ: '" + original + "'"
                );
            }
        }
        return result.toString();
    }
}