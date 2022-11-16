package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<k, v>{
    private final int DEFAULT_INITIAL_CAPACITY = 16;
    // Коэффициент загрузки
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Node[] table = new Node[DEFAULT_INITIAL_CAPACITY];
    // длина
    private List<k> keySet = new ArrayList<>();
    private List<v> values = new ArrayList<>();
    private  int size = 0;

    public void clear() {
        if (table != null && size > 0) {
            size = 0;
            for (int i = 0; i < table.length; ++i)
                table[i] = null;
        }
    }

    public int size() {
        return size;
    }

    public boolean containsKey(Object key) {
        return keySet.contains(key);
    }

    public boolean containsValue(Object key) {
        return values.contains(key);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public List<v> values(){
        return values;
    }

    public List<k> keySet() {
        return keySet;
    }

    public Object remove(Object key){
        // хеш-значение ключа
        int hashValue = hash(key);
        // индекс бакета(ячейки)
        int i = indexFor(hashValue,table.length);
        Node prev = table[i];
        for(Node node = table[i];node != null; prev = node, node = node.next){
            Object k;
            if(node.hash == hashValue && ((k = node.key)==key||key.equals(k))){
                keySet.remove(key);
                values.remove(node.value);
                size--;
                Object oldValue = node.value;
                if(table[i] == node) table[i] = node.next;
                else prev.next = node.next;
                return  oldValue;
            }
        }
        return null;
    }

    public Object put(Object key, Object value) {
        // хеш-значение ключа
        int hashValue = hash(key);
        // индекс бакета(ячейки)
        int i = indexFor(hashValue,table.length);
        for(Node node = table[i];node != null; node = node.next){
            Object k;
            // Если в i есть данные и ключ тот же, перезаписать
            if(node.hash == hashValue && ((k = node.key)==key||key.equals(k))){
                Object oldValue = node.value;
                node.value = value;
                return  oldValue;
            }
        }
        // Если в позиции i нет данных или есть данные в позиции i, но ключ - это новый ключ, добавьте узел
        addEntry(key,value,hashValue,i);
        return null;
    }


    public Object get(Object key) {
        // хеш-значение ключа
        int hashValue = hash(key);
        // индекс бакета(ячейки)
        int i = indexFor(hashValue,table.length);
        for(Node node = table[i];node != null;node = node.next){
            if(node.key.equals(key) && hashValue == node.hash){
                return node.value;
            }
        }
        return null;
    }

    private void addEntry(Object key,Object value,int hashValue,int i){
        // Если согласованная длина массива превышена, расширяем емкость
        if(++size >= table.length * DEFAULT_LOAD_FACTOR){
            Node[] newTable = new Node[table.length << 1];
            // копировать массив
            transfer(table,newTable);
            table = newTable;
        }
        // получить данные в i
        Node eNode = table[i];
        // Добавить узел, указать узел рядом с предыдущим узлом
        table[i] = new Node(hashValue,key,value,eNode);
        //Добавить ключ в множество ключей
        keySet.add((k) key);
        //Добавить значение в список значений
        values.add((v) value);
    }

    private void transfer (Node [] oldTable, Node [] newTable) {// oldTable ссылается на старый массив Entry
        int newCapacity = newTable.length;
        for (int j = 0; j <oldTable.length; j ++) {// пройти старый массив Entry
            Node e = oldTable [j]; // Получить каждый элемент старого массива Entry
            if (e != null) {
                oldTable [j] = null; // Освободить ссылку на объект старого массива Entry (после цикла for старый массив Entry больше не ссылается ни на какие объекты)
                do {
                    Node next = e.next;
                    int i = indexFor (e.hash, newCapacity); //! ! Пересчитать положение каждого элемента в массиве
                    e.next = newTable [i];
                    newTable [i] = e; // Поместить элемент в массив
                    e = next; // Доступ к элементам в следующей цепочке ввода
                } while (e != null);
            }
        }
    }

    private int indexFor(int hashValue,int length){
        return hashValue & length - 1;
    }

    private int hash(Object key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node{
        // хэш-значение
        int hash;
        Object key;
        Object value;
        // Указывать на следующий узел (односвязный список)
        Node next;
        Node(int hash,Object key,Object value,Node next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
