public static final char[] ALPHABET = {
        'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п',
        'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я',
        '.', ',', '<', '>', '"', '\'', ':', '!', '?', ' '
};

void main() {
    Scanner scanner = new Scanner(System.in);
    Cipher cipher = new Cipher(ALPHABET);
    FileManager fileManager = new FileManager();
    Validator validator = new Validator();
    BruteForce bruteForce = new BruteForce(cipher, ALPHABET);
    StatisticalAnalyzer analyzer = new StatisticalAnalyzer(cipher, ALPHABET);
    while (true) {
        IO.println("\n=== Шифр Цезаря ===");
        IO.println("1. Зашифровать текст из файла");
        IO.println("2. Расшифровать текст с известным ключом");
        IO.println("3. Расшифровать перебором (brute force)");
        IO.println("4. Расшифровать статистическим анализом");
        IO.println("0. Выход");
        IO.print("Выберите режим: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        try {
            switch (choice) {
                case 1 -> encryptMode(scanner, cipher, fileManager, validator);
                case 2 -> decryptMode(scanner, cipher, fileManager, validator);
                case 3 -> bruteForceMode(scanner, bruteForce, fileManager, validator);
                case 4 -> statisticalMode(scanner, analyzer, fileManager, validator);
                case 0 -> {
                    IO.println("До свидания!");
                    return;
                }
                default -> IO.println("Неверный пункт меню, попробуйте ещё раз.");
            }
        } catch (Exception e) {
            IO.println("Ошибка: " + e.getMessage());
        }
    }
}

private static void encryptMode(Scanner scanner, Cipher cipher, FileManager fileManager, Validator validator) throws
        Exception {
    IO.println("Введите путь к файлу с исходным текстом: ");
    String input = scanner.nextLine();
    validator.checkFileExists(input);
    IO.println("Введите ключ (сдвиг, число от 0 до " + (ALPHABET.length - 1) + "): ");
    int key = scanner.nextInt();
    scanner.nextLine();
    validator.checkKey(key, ALPHABET.length);
    IO.println("Введите путь к файлу для сохранения зашифрованного текста: ");
    String output = scanner.nextLine();
    String text = fileManager.readFile(input);
    String encrypted = cipher.encrypt(text, key);
    fileManager.writeFile(encrypted, output);
    IO.println("Текст зашифрован и сохранён в " + output);
}

private static void decryptMode(Scanner scanner, Cipher cipher, FileManager fileManager, Validator validator) throws
        Exception {
    IO.print("Введите путь к зашифрованному файлу: ");
    String input = scanner.nextLine();
    validator.checkFileExists(input);
    IO.print("Введите ключ (сдвиг): ");
    int key = scanner.nextInt();
    scanner.nextLine();
    validator.checkKey(key, ALPHABET.length);
    IO.print("Введите путь для сохранения расшифрованного текста: ");
    String output = scanner.nextLine();
    String text = fileManager.readFile(input);
    String decrypted = cipher.decrypt(text, key);
    fileManager.writeFile(decrypted, output);
    IO.print("Текст расшифрован и сохранён в " + output);
}

private static void bruteForceMode(Scanner scanner, BruteForce bruteForce, FileManager fileManager, Validator
        validator) throws Exception {
    IO.print("Введите путь к зашифрованному файлу: ");
    String input = scanner.nextLine();
    validator.checkFileExists(input);
    IO.print("Введите путь для сохранения расшифрованного текста: ");
    String output = scanner.nextLine();
    IO.print("Есть ли файл с примером текста того же автора? (да/нет): ");
    String answer = scanner.nextLine();
    String sampleFile = null;
    if (answer.equalsIgnoreCase("да")) {
        IO.print("Введите путь к файлу-образцу: ");
        sampleFile = scanner.nextLine();
        validator.checkFileExists(sampleFile);
    }
    String encryptedText = fileManager.readFile(input);
    String bestDecrypt;
    if (sampleFile != null) {
        String sampleText = fileManager.readFile(sampleFile);
        bestDecrypt = bruteForce.decryptWithSample(encryptedText, sampleText);
    } else {
        bestDecrypt = bruteForce.decryptWithoutSample(encryptedText);
    }
    fileManager.writeFile(bestDecrypt, output);
    IO.println("Лучший вариант расшифровки сохранён в " + output);
}

private static void statisticalMode(Scanner scanner, StatisticalAnalyzer analyzer, FileManager fileManager,
                                    Validator validator) throws Exception {
    IO.print("Введите путь к зашифрованному файлу: ");
    String input = scanner.nextLine();
    validator.checkFileExists(input);
    IO.print("Введите путь к файлу-образцу (репрезентативный текст): ");
    String sampleFile = scanner.nextLine();
    validator.checkFileExists(sampleFile);
    IO.print("Введите путь для сохранения расшифрованного текста: ");
    String output = scanner.nextLine();
    String encryptedText = fileManager.readFile(input);
    String sampleText = fileManager.readFile(sampleFile);
    String decrypted = analyzer.decryptByStatisticalAnalysis(encryptedText, sampleText);
    fileManager.writeFile(decrypted, output);
    IO.println("Текст расшифрован статистическим анализом и сохранён в " + output);
}