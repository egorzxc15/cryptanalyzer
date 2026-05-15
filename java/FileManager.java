import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileManager {
    public String readFile(String filePath) throws IOException {
        Path path = Path.get(filePath);
        byte[] bytes = Files.readAllBytes(path); // читаем весь файл как массив байт
        return new String(bytes, StandardCharsets.UTF_8); // преобразуем строку
    }

    public void writeFile(String content, String filePath) throws IOException {
        Path path = Path.get(filePath);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }
}