import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DataFileHandler {

    // Метод для читання масиву Character з файлу за допомогою Stream API
    public static Character[] loadArrayFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(currentLine -> currentLine.charAt(0))
                    .toArray(Character[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    // Метод для читання масиву LocalDateTime з файлу за допомогою Stream API
    public static LocalDateTime[] loadLocalDateTimeArrayFromFile(String filePath) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(currentLine -> LocalDateTime.parse(currentLine, timeFormatter))
                    .toArray(LocalDateTime[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    // Приклад методу для запису масиву Character у файл
    public static void writeArrayToFile(String filePath, Character[] characterArray) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            // Перетворюємо масив символів у рядок, де кожен символ розділений новим рядком
            String content = Arrays.stream(characterArray)
                    .map(String::valueOf)  // Перетворюємо Character в String
                    .collect(Collectors.joining(System.lineSeparator()));  // Збираємо всі рядки з розділенням
            fileWriter.write(content);  // Записуємо в файл
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка запису даних у файл: " + filePath, ioException);
        }
    }

    // Метод для запису масиву LocalDateTime у файл
    public static void writeLocalDateTimeArrayToFile(String filePath, LocalDateTime[] dateTimeArray) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            // Перетворюємо масив LocalDateTime у рядок, де кожен LocalDateTime перетворюється у String
            String content = Arrays.stream(dateTimeArray)
                    .map(LocalDateTime::toString)  // Перетворюємо LocalDateTime в String
                    .collect(Collectors.joining(System.lineSeparator()));  // Збираємо всі рядки з розділенням
            fileWriter.write(content);  // Записуємо в файл
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка запису даних у файл: " + filePath, ioException);
        }
    }

    public static void main(String[] args) {
        // Приклад використання методів
        String filePath = "path_to_output_file.txt";  // Вказати шлях до файлу, куди потрібно записати
        
        // Масив символів
        Character[] characters = {'A', 'B', 'C', 'D', 'E'};
        
        // Викликаємо метод для запису масиву Character в файл
        writeArrayToFile(filePath, characters);
        
        // Масив LocalDateTime
        LocalDateTime[] dateTimes = {
            LocalDateTime.of(2023, 5, 21, 10, 30),
            LocalDateTime.of(2023, 5, 22, 11, 45),
            LocalDateTime.of(2023, 5, 23, 12, 0)
        };
        
        // Викликаємо метод для запису масиву LocalDateTime в файл
        writeLocalDateTimeArrayToFile(filePath, dateTimes);
    }
}
