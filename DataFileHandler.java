import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*import java.time.Character;*/
/*import java.time.format.charFormatter;*/

/**
 * Клас DataFileHandler управляє роботою з файлами даних Character.
 */
public class DataFileHandler {
    /**
     * Завантажує масив об'єктів Character з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів Character.
     */
    public static Character[] loadArrayFromFile(String filePath) {
        /*charFormatter timeFormatter = charFormatter.ISO_DATE_TIME; */
        Character[] temporaryArray = new Character[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    Character parsedchar = currentLine.charAt(0);
                    temporaryArray[currentIndex++] = parsedchar;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Character[] resultArray = new Character[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив об'єктів Character у файл.
     * 
     * @param charArray Масив об'єктів Character.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Character[] charArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Character charElement : charArray) {
                fileWriter.write(charElement.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
