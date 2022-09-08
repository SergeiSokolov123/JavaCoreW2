import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:
        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia
        **************************************************
        Duplicate filtered, grouped by name, sorted by name and id:
        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();
        System.out.println("Task1**************************************************");
        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени
            Что должно получиться
                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */
        Map<String, Long> persons = Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .distinct()
                .collect(groupingBy(Person::getName, counting()));
        for (Map.Entry<String, Long> str : persons.entrySet()) {
            System.out.println("Key: " + str.getKey() + "\nValue:" + str.getValue());
        }
        // .forEach((key, value) ->
        //        System.out.println("Key: " + key + "\nValue:" + value));


        System.out.println("Task2**************************************************");
        /*
        Task2
            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */
        int[] arr = new int[]{3, 4, 2, 7};
        int sum = 10;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length; j++) {
                if ((arr[i] + arr[j]) == sum && arr[i] != 0 && arr[j] != 0) {
                    System.out.printf("[%d, %d]", arr[i], arr[j]);
                }
            }
        }
        /*
        Task3
            Реализовать функцию нечеткого поиска

                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */
        System.out.println("\nTask3**************************************************");
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel")); // true
        System.out.println(fuzzySearch("cwhl", "cartwheel")); // true
        System.out.println(fuzzySearch("cwhee", "cartwheel")); // true
        System.out.println(fuzzySearch("cartwheel", "cartwheel")); // true
        System.out.println(fuzzySearch("cwheeel", "cartwheel")); // false
        System.out.println(fuzzySearch("lw", "cartwheel")); // false
        assert fuzzySearch("car", "ca6$$#_rtwheel") : "test 1 failed";
        assert fuzzySearch("cwhl", "cartwheel") : "test 2 failed";
        assert fuzzySearch("cwhee", "cartwheel") : "test 3 failed";
        assert fuzzySearch("cartwheel", "cartwheel") : "test 4 failed";
        assert !fuzzySearch("cwheeel", "cartwheel") : "test 5 failed";
        assert !fuzzySearch("lw", "cartwheel") : "test 6 failed";
        System.out.println("All tests have been passed!");
    }

    public static boolean fuzzySearch(String key, String words) {
        int count = 0;
        if (key == null || words == null) return false;
        for (int i = 0; i < words.length(); i++) {
            if (key.charAt(count) == words.charAt(i)) count++;
            if (count == key.length()) {
                //    System.out.println(key + " " + words);
                return true;
            }
        }
        return false;


    }

}

