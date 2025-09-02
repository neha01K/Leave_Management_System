import java.io.*;

class AnyClassName implements Serializable{
    private static final long serialVersionUID = 1L;
    int i=10;
    int j=20;
    int k=30;
}
public class Sender {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        AnyClassName anyClassName = new AnyClassName();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("file3.ser"));
        objectOutputStream.writeObject(anyClassName);
    }
}

