import java.util.Scanner;

public class MainApp {
    // наш алфавит, только строчные буквы, пробел и знаки препинания
    public static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п',
            'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я',
            '.', ',', '<', '>', '"', '\'', ':', '!', '?', ' '
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cipher cipher = new Cipher(ALPHABET);
        FileManager fileManager = new FileManager();
        Validator validator = new Validator();
        BruteForce bruteForce = new BruteForce(cipher, ALPHABET);
        StatisticalAnalyzer analyzer = new StatisticalAnalyzer(cipher, ALPHABET);
        while (true) {
            System.out.println("\n=== Шифр Цезаря ===");
            System.out.println("1. Зашифровать текст из файла");
            System.out.println("2. Расшифровать текст с известным ключом");
            System.out.println("3. Расшифровать перебором (brute force)");
            System.out.println("4. Расшифровать статистическим анализом");
            System.out.println("0. Выход");
            System.out.print("Выберите режим: ");
        }
    }

}