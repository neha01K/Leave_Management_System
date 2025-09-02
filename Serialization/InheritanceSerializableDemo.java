import java.io.*;

class Vehicle{
    int wheels = 4;
    public Vehicle(){
        System.out.println("Parent constructor called");
    }
}
class Bicycle extends Vehicle implements Serializable {
    int handle = 2;
    public Bicycle() {
        System.out.println("Child constructor called");
    }
}
public class InheritanceSerializableDemo {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {

        Bicycle bicycle = new Bicycle();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("file.ser"));
        objectOutputStream.writeObject(bicycle);

        System.out.println("Deserialization started!");

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("file.ser"));
        Bicycle bicycle1 = (Bicycle)objectInputStream.readObject();

        System.out.println(bicycle1.wheels + "............." + bicycle1.handle);
    }
}
