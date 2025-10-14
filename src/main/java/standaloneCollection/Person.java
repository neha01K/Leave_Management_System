package standaloneCollection;

import java.util.HashMap;
import java.util.List;

public class Person {
    private List<String> friends;
    private HashMap<String, Integer> feestructure;

    public Person(List<String> friends) {
        this.friends = friends;
    }

    public Person() {
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public HashMap<String, Integer> getFeestructure() {
        return feestructure;
    }

    public void setFeestructure(HashMap<String, Integer> feestructure) {
        this.feestructure = feestructure;
    }

    @Override
    public String toString() {
        return "Person{" +
                "friends=" + friends +
                ", feestructure=" + feestructure +
                '}';
    }
}
