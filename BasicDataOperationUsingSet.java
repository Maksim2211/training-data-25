/*import java.time.Character;*/
import java.util.Arrays;
//* import java.util.Collections; *//
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною HashSet для Character.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив Character.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві Character.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині Character.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    Character CharacterValueToSearch;
    Character[] charArray;
    Set<Character> charSet = new HashSet<Character>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param CharacterValueToSearch Значення для пошуку
     * @param charArray Масив Character
     */
    BasicDataOperationUsingSet(Character CharacterValueToSearch, Character[] charArray) {
        this.CharacterValueToSearch = CharacterValueToSearch;
        this.charArray = charArray;
        this.charSet = new HashSet<Character>(Arrays.asList(charArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом Character.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дати та часу
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(BasicDataOperation.PATH_TO_DATA_FILE + ".sorted", charArray);
    }

    /**
     * Упорядковує масив об'єктів Character за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        charArray = Arrays.stream(charArray)
                .sorted()
                .toArray(Character[]::new);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();

        int position = (int) Arrays.stream(charArray)
                .map(Arrays.asList(charArray)::indexOf)
                .filter(i -> CharacterValueToSearch.equals(charArray[i]))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + CharacterValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + CharacterValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві Character.
     */
    private void locateMinMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Character minValue = Arrays.stream(charArray).min(Character::compareTo).orElse(null);
        Character maxValue = Arrays.stream(charArray).max(Character::compareTo).orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = charSet.stream()
                .anyMatch(character -> character.equals(CharacterValueToSearch));

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в HashSet дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + CharacterValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Елемент '" + CharacterValueToSearch + "' відсутній в HashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині Character.
     */
    private void locateMinMaxInSet() {
        if (charSet == null || charSet.isEmpty()) {
            System.out.println("HashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Character minValue = charSet.stream()
                .min(Character::compareTo)
                .orElse(null);
        Character maxValue = charSet.stream()
                .max(Character::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в HashSet");

        System.out.println("Найменше значення в HashSet: " + minValue);
        System.out.println("Найбільше значення в HashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + charArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + charSet.size());

        boolean allElementsPresent = Arrays.stream(charArray)
                .allMatch(charSet::contains);

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в HashSet.");
        }
    }
}