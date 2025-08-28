package list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class LLDemo {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        //already there
        list.add(23);
        list.add(87);

        //linked list specific
        list.addFirst(32);
        list.addLast(51);
        for(Integer i: list){
            System.out.println(i);
        }
        list.remove(2);
        System.out.println("---------------");
        for(Integer i: list){
            System.out.println(i);
        }

        ArrayList<String> fruit = new ArrayList<>();
        fruit.add("Banana");
        fruit.add("apple");
        fruit.add("orange");

        HashSet<String> fruits = new HashSet<>();
        fruits.add("Guava");
        fruits.add("Tomato");

        //addAll helps to add other elements of different collections in present list
        fruit.addAll(fruits);
        System.out.println(fruit);
    }
}
