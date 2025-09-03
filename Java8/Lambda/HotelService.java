import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HotelService {
        List<Hotel> hotels = new ArrayList<>();

       public HotelService(){
           hotels.add(new Hotel(2000,3, HotelType.THREE_STAR));
           hotels.add(new Hotel(1000,2, HotelType.THREE_STAR));
           hotels.add(new Hotel(4000,5, HotelType.FOUR_STAR));
           hotels.add(new Hotel(3500,4, HotelType.FIVE_STAR));
           hotels.add(new Hotel(1200,2, HotelType.THREE_STAR));
           hotels.add(new Hotel(4500,5, HotelType.FIVE_STAR));

       }

       /*private boolean isHotelpriceLessThan(int price, Hotel hotel) {
               return hotel.getPerNightPrice()<=price;
       }

       public List<Hotel> hotelPriceLessThan(int price){
           List<Hotel> filteredHotels = new ArrayList<>();

           for(Hotel hotel : hotels)
               if(isHotelpriceLessThan(price, hotel))
                   filteredHotels.add(hotel);

           return filteredHotels;
       }

       private boolean isHotel5Star(Hotel hotel) {
           return hotel.getHotelType()==HotelType.FIVE_STAR;
       }

       public List<Hotel> filterHotelBy5Star(){
           List<Hotel> filteredHotels = new ArrayList<>();

           for(Hotel hotel : hotels)
               if(isHotel5Star(hotel))
                   filteredHotels.add(hotel);

           return filteredHotels;
       }*/

      public List<Hotel> filterHotels(Predicate<Hotel> hotelFilteringCondition){
          List<Hotel> filteredHotels = new ArrayList<>();

          for(Hotel hotel : hotels)
              if(hotelFilteringCondition.test(hotel))
                  filteredHotels.add(hotel);

          return filteredHotels;
      }
}
