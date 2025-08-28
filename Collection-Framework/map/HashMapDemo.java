package map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<Integer,String> hashmap = new HashMap<>();
        hashmap.put(1,"omkar");
        hashmap.put(2,"omkar");
        hashmap.put(3,"piyush");
        hashmap.put(4,"lucky");
        hashmap.put(5,"");

        for(Map.Entry<Integer,String> m: hashmap.entrySet()){
            System.out.println("Key: "+m.getKey()+", Value: "+m.getValue());
        }

    }
}
