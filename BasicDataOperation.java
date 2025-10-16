import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.time.Character;

/**
 * Загальний клас BasicDataOperation координує роботу різних структур даних.
 * 
 * <p>Цей клас служить центральною точкою для демонстрації операцій з різними
 * колекціями Java: List, Queue та Set. Він об'єднує функціональність всіх
 * спеціалізованих класів для комплексного аналізу даних Character.</p>
 * 
 * <p>Основні можливості:</p>
 * <ul>
 *   <li>Координація операцій з різними типами колекцій</li>  
 *   <li>Порівняльний аналіз продуктивності структур даних</li>
 *   <li>Централізоване управління обробкою даних</li>
 *   <li>Демонстрація переваг різних колекцій</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperation "2024-03-16T00:12:38Z" list
 * java BasicDataOperation "2024-03-16T00:12:38Z" queue  
 * java BasicDataOperation "2024-03-16T00:12:38Z" set
 * java BasicDataOperation "2024-03-16T00:12:38Z" all
 * }
 * </pre>
 */
public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/Character.data";

    Character CharacterValueToSearch;
    Character[] dateTimeArray;

    private static final String SEPARATOR = "\n" + "=".repeat(80) + "\n";
    private static final String USAGE_MESSAGE = "Використання: java BasicDataOperation <пошукове-значення> \n" +
"Приклад:\n" +
"  java BasicDataOperation \"2025-01-02T20:42:25Z\"";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        String searchValue = args[0];

        // Валідація введеного значення дати
        try {
            Character.parse(currentLine.charAt(0));
        } catch (Exception e) {
            System.out.println("Помилка: Невірний формат дати-часу. Використовуйте ISO формат (наприклад: 2024-03-16T00:12:38Z)");
            return;
        }

        BasicDataOperation coordinator = new BasicDataOperation();
        coordinator.executeOperations(args);
    }

    /**
     * Координує виконання операцій залежно від обраного типу.
     * 
     * @param args Аргументи командного рядка
     */
    private void executeOperations(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("🚀 РОЗПОЧАТО АНАЛІЗ ДАНИХ Character 🚀");
        System.out.println("Пошуковий параметр: " + args[0]);
        System.out.println(SEPARATOR);
        
        // Підготовка даних та перевірка формату
        CharacterValueToSearch = Character.parse(args[0]);
        dateTimeArray = DataFileHandler.loadArrayFromFile(PATH_TO_DATA_FILE);
        
        runAllOperations();

        System.out.println(SEPARATOR);
        System.out.println("✅ АНАЛІЗ ЗАВЕРШЕНО ✅");
        System.out.println(SEPARATOR);
    }

    /**
     * Запускає операції з колекцією List.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runListOperations() {
        System.out.println("📋 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ LIST");
        System.out.println("-".repeat(50));
        
        try {
            // Створення екземпляру класу з передаванням даних
            BasicDataOperationUsingList listProcessor = new BasicDataOperationUsingList(CharacterValueToSearch, dateTimeArray);
            listProcessor.executeDataOperations();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з List: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Queue.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runQueueOperations() {
        System.out.println("🔄 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ QUEUE");
        System.out.println("-".repeat(50));
        
        try {
            // Створення екземпляру класу з передаванням даних
            BasicDataOperationUsingQueue queueProcessor = new BasicDataOperationUsingQueue(CharacterValueToSearch, dateTimeArray);
            queueProcessor.runDataProcessing();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Set.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runSetOperations() {
        System.out.println("🔍 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ SET");
        System.out.println("-".repeat(50));
        
        try {
            // Створення екземпляру класу з передаванням даних           
            BasicDataOperationUsingSet setProcessor = new BasicDataOperationUsingSet(CharacterValueToSearch, dateTimeArray);
            setProcessor.executeDataAnalysis();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Set: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з усіма типами колекцій для порівняння.
     * 
     * @param args Аргументи для передачі до класів
     */
    private void runAllOperations() {
        System.out.println("🎯 КОМПЛЕКСНИЙ АНАЛІЗ ВСІХ СТРУКТУР ДАНИХ");
        System.out.println("=".repeat(60));
        
        // Обробка List
        runListOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");
        
        // Обробка Queue  
        runQueueOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");
        
        // Обробка Set
        runSetOperations();
    }
}
