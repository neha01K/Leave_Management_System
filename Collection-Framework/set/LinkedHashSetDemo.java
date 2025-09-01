package set;

import java.util.LinkedHashSet;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.add("riya");
        lhs.addFirst("sunita");
        lhs.add("vinita");
        lhs.add("riya");
        lhs.add("riya");

        System.out.println(lhs);
        lhs.removeFirst();
        System.out.println(lhs);
    }
}
