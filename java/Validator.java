import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {
    public void checkFileExists(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Файл не найден: " + filePath);
        }
    }

    public void checkKey(int key, int alphabetLength) {
        if (key < 0 || key >= alphabetLength) {
            throw new IllegalArgumentException(
                    "Ключ должен быть от 0 до " + (alphabetLength - 1) + ". Вы ввели: " + key
            );
        }
    }
}