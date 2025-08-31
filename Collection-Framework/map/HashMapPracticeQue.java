package map;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HashMapPracticeQue {
    public static void main(String[] args) {

        //Word Counting: Write a program that takes a sentence as input
        // and uses a HashMap to count the frequency of each word. The words
        // will be the keys and their counts will be the values. This is a
        // classic example of using a HashMap for frequency counting.

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a sentence: ");
        String str = sc.nextLine();

        HashMap<String, Integer> hashMap = new HashMap<>();


        for(String s1 : str.split(" ")){
            s1 = s1.toLowerCase().replaceAll("[^a-z]", "");
            if(!s1.isEmpty()){
                hashMap.put(s1, hashMap.getOrDefault(s1, 0) + 1);
            }
        }

        //use of EntrySet

        for(Map.Entry<String, Integer> entry : hashMap.entrySet()){
            System.out.println("Key: "+entry.getKey()+"| Count: "+ entry.getValue());
        }

    }
}
