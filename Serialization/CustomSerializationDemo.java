import java.io.*;

class Account implements Serializable {
    String username = "Neha";
    transient String password = "nehakhandelwal";

    private void writeObject(ObjectOutputStream objectOutputStream) throws Exception{
        String encryptPassword = "123"+password;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(encryptPassword);
    }

    private void readObject(ObjectInputStream objectInputStream) throws Exception{
        objectInputStream.defaultReadObject();
        String encryptPassword = (String)objectInputStream.readObject();
        password = encryptPassword.substring(3);
    }

}
public class CustomSerializationDemo {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        Account account = new Account();
        System.out.println("Username: "+account.username+" Password: "+account.password);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("account.ser"));
        outputStream.writeObject(account);

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("account.ser"));
        Account account2 = (Account)objectInputStream.readObject();

        System.out.println("Username: "+account2.username+" Password: "+account2.password);
    }
}
