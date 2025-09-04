import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


//Commented code is for my practice and future reference

public class Main{

    int field = 1200;

    public Predicate<Hotel> getLambda(){
        HotelService hotelService = new HotelService();

        int PRICE = 9000;
        List<Integer> arrayList = new ArrayList<>();
        /*HotelFilteringCondition lambdaExample = hotel -> {
            field = 1300;
            arrayList.add(1);
            return  hotel.getPerNightPrice()<=PRICE;
        };*/
        Predicate<Hotel> lambdaExample = hotel -> {
            field = 1300;
            arrayList.add(1);
            return  hotel.getPerNightPrice()<=PRICE;
        };

        //arrayList = new ArrayList<>(); we cannot do this
        return lambdaExample;
    }


    public static void main(String[] args) {
        HotelService hotelService = new HotelService();

        /*List<Hotel> hotels = hotelService.filterHotels(new HotelFilteringCondition(){
            @Override
            public boolean test(Hotel hotel) {
                return hotel.getPerNightPrice()<=2000;
            }
        });*/

        Main main = new Main();
        //HotelFilteringCondition lambdaExample = main.getLambda();
        Predicate<Hotel> lambdaExample = main.getLambda();
        hotelService.filterHotels(lambdaExample);

        List<Hotel> hotels = hotelService.filterHotels(hotel -> hotel.getPerNightPrice() <= 2000);

        ArrayList<Integer> list = new ArrayList<>(List.of(1,2,3,4,5));
        Collections.sort(list,(a, b) -> {
            return b - a;
        });
        System.out.println(list);


        Predicate<Integer> divisibleBy2 = number -> number%2==0;
        Predicate<Integer> divisibleBy3 = number -> number%3==0;

        Predicate<Integer> divisibleBy6 = divisibleBy2.and(divisibleBy3);
        System.out.println(divisibleBy6.test(24));

        Consumer<String> printConsumer = name -> System.out.println(name);
        printConsumer.accept("Neha Khandelwal");

        Consumer<List<Integer>> listConsumer1 = integerList ->{
            for(Integer listValue : integerList)
                System.out.println(listValue+100);
        };
        listConsumer1.accept(list);

        Consumer<List<Integer>> listConsumer2 = li ->{
            for(Integer listValue : li)
                System.out.println(listValue);
        };
        listConsumer2.accept(list);

        Consumer<List<Integer>> listConsumer = listConsumer1.andThen(listConsumer2);
        listConsumer.accept(Arrays.asList(1,2,3,4));

        System.out.println("List of Hotels Price less than 2000: "+hotels);

    }

}