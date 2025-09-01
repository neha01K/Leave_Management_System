package list;

import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {

        //Vector are Thread-safe Array List
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        v.add(4);
        System.out.println(v);
        System.out.println("--------------------------");
        v.removeAllElements();
        System.out.println(v);

    }
}
