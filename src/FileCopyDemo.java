import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyDemo {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream("bbc.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("newFile.txt");
        int data;
        while((data=fileInputStream.read())!=-1){
            fileOutputStream.write(data);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}
