import java.io.*;
class Cat implements Serializable{
    String name = "Tom";
}
class Rat implements Serializable{
    String name = "Jerry";
}

public class SerializationDemo2 {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {

        Dog dog = new Dog();
        Cat cat = new Cat();
        Rat rat = new Rat();

        FileOutputStream fileOutputStream = new FileOutputStream("bbc.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(dog);
        objectOutputStream.writeObject(cat);
        objectOutputStream.writeObject(rat);


        FileInputStream fileInputStream = new FileInputStream("bbc.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object object = objectInputStream.readObject();
        if(object instanceof Dog){
            Dog dog1 = (Dog)object;
        }
        else if(object instanceof Cat){
            Cat cat1 = (Cat)object;
        }
        else{
            Rat rat1 = (Rat)object;
        }

        fileInputStream.close();
        objectInputStream.close();
        fileOutputStream.close();
        objectOutputStream.close();
    }
}
