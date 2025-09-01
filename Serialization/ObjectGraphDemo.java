import java.io.*;

class Address implements Serializable {
    String city;
    public Address(String city){
        this.city = city;
    }
}

class Person implements Serializable{
    String name;
    Address address;
    public Person(String name, Address address){
        this.name  = name;
        this.address = address;
    }
}
public class ObjectGraphDemo {
    public static void main(String[] args)  throws IOException, FileNotFoundException, ClassNotFoundException {
        Address address = new Address("Jaipur");
        Person person = new Person("Neha", address);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("person.txt"));
        objectOutputStream.writeObject(person);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("person.txt"));
        Person person1 = (Person)objectInputStream.readObject();

        System.out.println(person1.name);
        System.out.println(person1.address.city);
    }
}
