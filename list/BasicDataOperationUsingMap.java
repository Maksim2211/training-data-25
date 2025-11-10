import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Python KEY_TO_SEARCH_AND_DELETE = new Python("Луна", "Полярна сова");
    private final Python KEY_TO_ADD = new Python("Кір", "Сова вухата");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Олена";
    private final String VALUE_TO_ADD = "Богдан";

    private Hashtable<Python, String> hashtable;
    private TreeMap<Python, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Python, String>> {
        @Override
        public int compare(Map.Entry<Python, String> e1, Map.Entry<Python, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Python для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Python> для визначення природного порядку сортування.
     * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (skinSpots) за спаданням.
     */
    public static class Python implements Comparable<Python> {
        private final String nickname;
        private final String skinSpots;

        public Python(String nickname) {
            this.nickname = nickname;
            this.skinSpots = null;
        }

        public Python(String nickname, Integer skinSpots) {
            this.nickname = nickname;
            this.skinSpots = skinSpots;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Integer getSkinSpots() {
            return skinSpots;
        }

        /**
         * Порівнює цей об'єкт Python з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (skinSpots) за спаданням.
         * 
         * @param other Python об'єкт для порівняння
         * @return негативне число, якщо цей Python < other; 
         *         0, якщо цей Python == other; 
         *         позитивне число, якщо цей Python > other
         * 
         * Критерій порівняння: поля nickname (кличка) за зростанням та skinSpots (вид) за спаданням.
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Python за nickname (зростання), потім за skinSpots (спадання)
         * - Collections.sort() для сортування Map.Entry за ключами Python
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Python other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за зростанням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = -1;
            } else if (other.nickname == null) {
                nicknameComparison = 1;
            } else {
                nicknameComparison = other.nickname.compareTo(other.nickname);
            }
            
            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за видом (за спаданням - інвертуємо результат)
            if (this.skinSpots == null && other.skinSpots == null) return 0;
            if (this.skinSpots == null) return 1;  // null йде в кінець при спаданні
            if (other.skinSpots == null) return -1;
            return other.skinSpots.compareTo(other.skinSpots);  // Інвертоване порівняння для спадання
        }

        /**
         * Перевіряє рівність цього Python з іншим об'єктом.
         * Два Python вважаються рівними, якщо їх клички (nickname) та види (skinSpots) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та skinSpots (вид).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та skinSpots.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Python Python = (Python) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(Python.nickname) : Python.nickname == null;
            boolean skinSpotsEquals = skinSpots != null ? skinSpots.equals(Python.skinSpots) : Python.skinSpots == null;
            
            return nicknameEquals && skinSpotsEquals;
        }

        /**
         * Повертає хеш-код для цього Python.
         * 
         * @return хеш-код, обчислений на основі nickname та skinSpots
         * 
         * Базується на полях nickname та skinSpots для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Python рівні за equals()
         * (мають однакові nickname та skinSpots), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код виду (або 0, якщо skinSpots == null) до загального результату
            result = 31 * result + (skinSpots != null ? skinSpots.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Python.
         * 
         * @return кличка тварини (nickname), вид (skinSpots) та hashCode
         */
        @Override
        public String toString() {
            if (skinSpots != null) {
                return "Python{nickname='" + nickname + "', skinSpots='" + skinSpots + "', hashCode=" + hashCode() + "}";
            }
            return "Python{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Python, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Python, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Python, String> hashtable, TreeMap<Python, String> treeMap) {
        this.hashtable = hashtable;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        // Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // Потім обробляємо TreeMap
        System.out.println("\n\n========= Операції з TreeMap =========");
        System.out.println("Початковий розмір TreeMap: " + treeMap.size());
        
        findByKeyInTreeMap();
        findByValueInTreeMap();

        printTreeMap();

        addEntryToTreeMap();
        
        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();
        
        System.out.println("Кінцевий розмір TreeMap: " + treeMap.size());
    }


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Python, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує Hashtable за ключами.
     * Використовує Collections.sort() з природним порядком Python (Python.compareTo()).
     * Перезаписує hashtable відсортованими даними.
     */
    private void sortHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Python
        List<Python> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову Hashtable з відсортованими ключами
        Hashtable<Python, String> sortedHashtable = new Hashtable<>();
        for (Python key : sortedKeys) {
            sortedHashtable.put(key, hashtable.get(key));
        }
        
        // Перезаписуємо оригінальну hashtable
        hashtable = sortedHashtable;

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Python.hashCode() та Python.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в Hashtable.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Python, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Python, String> searchEntry = new Map.Entry<Python, String>() {
            public Python getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в Hashtable");

        if (position >= 0) {
            Map.Entry<Python, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Python: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Python='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        List<Python> keysToRemove = new ArrayList<>();
        for (Map.Entry<Python, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Python key : keysToRemove) {
            hashtable.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Python nickname за зростанням, skinSpots за спаданням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Python, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    /**
     * Здійснює пошук елемента за ключем в TreeMap.
     * Використовує Python.compareTo() для навігації по дереву.
     */
    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в TreeMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Python, String>> entries = new ArrayList<>(treeMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Python, String> searchEntry = new Map.Entry<Python, String>() {
            public Python getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в TreeMap");

        if (position >= 0) {
            Map.Entry<Python, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Python: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Додає новий запис до TreeMap.
     */
    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");

        System.out.println("Додано новий запис: Python='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з TreeMap за ключем.
     */
    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з TreeMap за значенням.
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Python> keysToRemove = new ArrayList<>();
        for (Map.Entry<Python, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Python key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Python, значення: ім'я власника)
        Hashtable<Python, String> hashtable = new Hashtable<>();
        hashtable.put(new Python("Тум", "Сова вухата"), "Андрій");
        hashtable.put(new Python("Луна", "Полярна сова"), "Ірина");
        hashtable.put(new Python("Барсик", "Сова сіра"), "Олена");
        hashtable.put(new Python("Боні", "Сипуха"), "Олена");
        hashtable.put(new Python("Тайсон", "Сова болотяна"), "Ірина");
        hashtable.put(new Python("Барсик", "Сичик-горобець"), "Андрій");
        hashtable.put(new Python("Ґуфі", "Сова болотяна"), "Тимофій");
        hashtable.put(new Python("Боні", "Сова яструбина"), "Поліна");
        hashtable.put(new Python("Муся", "Сова білолиця"), "Стефанія");
        hashtable.put(new Python("Чіпо", "Сичик-хатник"), "Ярослав");

        TreeMap<Python, String> treeMap = new TreeMap<Python, String>() {{
            put(new Python("Тум", "Сова вухата"), "Андрій");
            put(new Python("Луна", "Полярна сова"), "Ірина");
            put(new Python("Барсик", "Сова сіра"), "Олена");
            put(new Python("Боні", "Сипуха"), "Олена");
            put(new Python("Тайсон", "Сова болотяна"), "Ірина");
            put(new Python("Барсик", "Сичик-горобець"), "Андрій");
            put(new Python("Ґуфі", "Сова болотяна"), "Тимофій");
            put(new Python("Боні", "Сова яструбина"), "Поліна");
            put(new Python("Муся", "Сова білолиця"), "Стефанія");
            put(new Python("Чіпо", "Сичик-хатник"), "Ярослав");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, treeMap);
        operations.executeDataOperations();
    }
}