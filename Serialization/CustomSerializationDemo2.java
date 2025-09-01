import java.io.*;

class ATM implements Serializable {
    String username="Neha";
    transient String password = "nehakhandelwal";
    transient int pin = 1234;

    private void writeObject(ObjectOutputStream objectOutputStream) throws Exception {
        objectOutputStream.defaultWriteObject();
        String encryptPassword = "123"+password;
        objectOutputStream.writeObject(encryptPassword);
        int encryptPin = 4444+pin;
        objectOutputStream.writeInt(encryptPin);
    }
    private void readObject(ObjectInputStream objectInputStream) throws Exception {
        objectInputStream.defaultReadObject();
        String decryptPassword = (String)objectInputStream.readObject();
        password = decryptPassword.substring(3);
        int decryptPin = (int)objectInputStream.readInt();
        pin = decryptPin-4444;
    }
}

public class CustomSerializationDemo2 {
    public static void main(String[] args) throws IOException, FileNotFoundException,ClassNotFoundException {
        ATM atm = new ATM();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("atm.ser"));
        objectOutputStream.writeObject(atm);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream((new FileInputStream("atm.ser")));
        ATM atm1 = (ATM)objectInputStream.readObject();
        objectInputStream.close();
    }
}
