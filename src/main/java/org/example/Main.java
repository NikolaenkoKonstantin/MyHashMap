package org.example;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();

        map.put(1, "One");
        map.put(2, "two");
        map.put(3, "three");

        System.out.println("Множество ключей: " + map.keySet());
        System.out.println("Множество значений: " + map.values());
        System.out.println("Существует ли такой ключ 3: " + map.containsKey(3));
        System.out.println("Существует ли такое значение four: " + map.containsValue("four"));
        System.out.println("Удаление по ключу 2 , вернуло значение иначе null: " + map.remove(2));
        System.out.println("Размер: " + map.size());
        System.out.println("Получит значение по ключу 3: " + map.get(3));
        System.out.println("Проверяем существует ли ключ 2 после удаления: " + map.containsKey(2));
        System.out.println("Проверяем существует ли значение two после удаления: " + map.containsValue("two"));
        System.out.println("Пусто или нет(До очищения): " + map.isEmpty());
        map.clear();
        System.out.println("Пусто или нет(После очищения): " + map.isEmpty());
    }
}