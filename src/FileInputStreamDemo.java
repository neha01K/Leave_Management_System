import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamDemo {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream("abc.txt");
        int data =0;
        for(int i=0; i<10; i++){
            data = fileInputStream.read();
        }
        System.out.println("Data: "+ (char)data);
        fileInputStream.close();
    }
}
