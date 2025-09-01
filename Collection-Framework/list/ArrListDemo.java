package list;

import java.lang.reflect.Array;
import java.util.*;

public class ArrListDemo {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        //adding elements
        a.add(1);
        a.add(2);

        //adding at first position
        a.addFirst(23);

        //adding at last position
        a.addLast(32);

        //printing of arryList element - using for each
        for(Integer arr : a){
            System.out.println(arr);
        }
        //removing the 0th index element
        a.remove(0);
        System.out.println("After removal:" + a);
        //adding at specific index
        a.add(3,4);
        System.out.println(a);

        //using of ensureCapacity() method

        ArrayList<Integer> arrList = new ArrayList<>();
        arrList. ensureCapacity(100);
        for(int i=0; i<100; i++){
            arrList.add(i);
        }
        System.out.println("Array List size: "+ arrList.size());

        arrList.add(101);
        System.out.println("Updated Array List size: "+ arrList.size());




    }
}
