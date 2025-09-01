package set;

import com.sun.source.tree.Tree;

import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> t= new TreeSet<>();

        //sorted result
        t.add(23);
        t.add(2);
        t.add(56);
        t.add(21);

        System.out.println(t);
        t.clear();
        System.out.println(t);

    }
}
