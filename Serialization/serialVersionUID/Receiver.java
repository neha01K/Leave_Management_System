import java.io.*;

class AnyClassName implements Serializable{
    private static final long serialVersionUID = 1L;
    int i=10;
    int j=20;
    int k=30;
    int l=50;
    int r=90;
}
public class Receiver {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        AnyClassName anyClassName = new AnyClassName();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("file3.ser"));
        AnyClassName anyClassName1 = (AnyClassName) objectInputStream.readObject();
    }
}

