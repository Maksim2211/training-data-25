//*import java.time.Character; *//
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу LinkedList для даних Character.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив елементів Character.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві Character.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List з Character.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private Character CharacterValueToSearch;
    private Character[] charArray;
    private List<Character> charList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param CharacterValueToSearch Значення для пошуку
     * @param charArray Масив Character
     */
    BasicDataOperationUsingList(Character CharacterValueToSearch, Character[] charArray) {
        this.CharacterValueToSearch = CharacterValueToSearch;
        this.charArray = charArray;
        this.charList = new LinkedList<Character>(Arrays.asList(charArray));
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів Character, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        // спочатку працюємо з колекцією List
        findInList();
        locateMinMaxInList();
        
        sortList();
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив дати та часу
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів Character за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(charArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.charArray, CharacterValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук Character");

        if (position >= 0) {
            System.out.println("Елемент '" + CharacterValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + CharacterValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Character minValue = charArray[0];
        Character maxValue = charArray[0];

        for (Character currentchar : charArray) {
            if (currentchar.charValue() < minValue.charValue()) {
                minValue = currentchar;
            }
            if (currentchar.charValue() > maxValue.charValue()) {
                maxValue = currentchar;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції LinkedList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.charList, CharacterValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List дати i часу");        

        if (position >= 0) {
            System.out.println("Елемент '" + CharacterValueToSearch + "' знайдено в LinkedList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + CharacterValueToSearch + "' відсутній в LinkedList.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції LinkedList з датами.
     */
    void locateMinMaxInList() {
        if (charList == null || charList.isEmpty()) {
            System.out.println("Колекція LinkedList є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        Character minValue = Collections.min(charList);
        Character maxValue = Collections.max(charList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в List");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами Character за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(charList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування LinkedList дати i часу");
    }
}