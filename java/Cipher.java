public class Cipher {
    private final char[] alphabet;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    // Шифрование одного символа, сдвиг вперёд на key позиций
    private char shiftChar(char ch, int key) {
        // Ищем индекс символа в алфавите
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == ch) {
                int newIndex = (i + key) % alphabet.length; // циклический сдвиг
                if (newIndex < 0) { // на случай отрицательного key в будущем
                    newIndex += alphabet.length;
                }
                return alphabet[newIndex];
            }
        }
        // если символа нет в алфавите, возвращаем как есть (при шифровании пропускаем)
        return ch;
    }

    public String encrypt(String text, int key) {
        String lowerText = text.toLowerCase(); // приводим к нижнему регистру
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lowerText.length(); i++) {
            char original = lowerText.charAt(i);
            if (contains(original)) { // символ есть в алфавите, шифруем
                result.append(shiftChar(original, key));
            }
        }
        return result.toString();
    }

    public String decrypt(String text, int key) {
        // расшифровка = шифрование с отрицательным ключом
        String lowerText = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lowerText.length(); i++) {
            char original = lowerText.charAt(i);
            if (contains(original)) {
                // для расшифровки сдвиг в обратную сторону
                result.append(shiftChar(original, -key));
            } else {
                throw new IllegalArgumentException("В зашифрованном тексте найден недопустимый символ:'" + original + "'"
                );
            }
        }
        return result.toString();
    }

    // Проверка, есть ли символ в алфавите
    private boolean contains(char c) {
        for (char a : alphabet) {
            if (a == c) return true;
        }
        return false;
    }
    // Геттер для алфавита

    public char[] getAlphabet() {
        return alphabet;
    }
}