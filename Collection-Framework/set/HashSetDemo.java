package set;

import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> hs = new HashSet<>();

        //no duplicacy
        //no order
        hs.add("lokesh");
        hs.add("lokesh");
        hs.add("ramesh");
        hs.add("suresh");
        for(String s:hs){
            System.out.println(s);
        }




    }
}
