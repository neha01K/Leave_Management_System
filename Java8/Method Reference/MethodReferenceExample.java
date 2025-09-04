import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Greeter{
    public void dayGreet(String name){
        System.out.println("Good Morning! " + name);
    }

    public static void nightGreet(String name){
        System.out.println("Good Night! "+ name);
    }
}

public class MethodReferenceExample {
    public static void main(String[] args) {
        List<Integer> list =  Arrays.asList(1,2,3,4);
        List<String> names = Arrays.asList("Neha", "Kiran", "Rachit","Sudha");

        Greeter greeter = new Greeter();

        //without method reference
        list.forEach(number -> System.out.println(number));

        list.forEach(number -> System.out.println("Square= "+ number*number));

        list.forEach(number -> {
            System.out.println("Hello "+ number);
            System.out.println("Welcome!");
        });

        names.forEach(name -> greeter.dayGreet(name));
        names.forEach(name -> Greeter.nightGreet(name));

        names.sort((string1, string2) -> string1.compareToIgnoreCase(string2));

        //with method reference
        list.forEach(System.out::println);

        names.forEach(greeter :: dayGreet);

        names.forEach(Greeter :: nightGreet);

        names.sort(String::compareToIgnoreCase);
        System.out.println(names);

        List<String> nameList1 = Arrays.asList("Ram", "Arjun", "Mukesh");
        List<String> nameList2 = Arrays.asList("ram", "Arjun", "Mukesh");



    }
}
