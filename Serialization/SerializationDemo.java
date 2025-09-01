import java.io.*;
class Dog implements Serializable{
    String name="puppy";
    int age = 12;
}

public class SerializationDemo implements Serializable {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {

        Dog dog = new Dog();
        FileOutputStream fileOutputStream = new FileOutputStream("bbc.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(dog);

        FileInputStream fileInputStream = new FileInputStream("bbc.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Dog dog1 = (Dog)objectInputStream.readObject();

        fileOutputStream.close();
        fileInputStream.close();
        objectInputStream.close();
        objectOutputStream.close();

    }
}
