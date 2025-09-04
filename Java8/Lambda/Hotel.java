public class Hotel {
    public int perNightPrice;
    public int rating;
    public HotelType hotelType;

    public Hotel(int perNightPrice, int rating, HotelType hotelType){
        this.perNightPrice = perNightPrice;
        this.rating = rating;
        this.hotelType = hotelType;
    }

    public void setHotelType(HotelType hotelType) {
        this.hotelType = hotelType;
    }

    public void setPerNightPrice(int perNightPrice) {
        this.perNightPrice = perNightPrice;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPerNightPrice() {
        return perNightPrice;
    }

    public HotelType getHotelType() {
        return hotelType;
    }

    public int getRating() {
        return rating;
    }
}
