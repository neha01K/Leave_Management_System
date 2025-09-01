import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyDemo2 {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(args[0]);
        FileOutputStream fileOutputStream = new FileOutputStream(args[1]);
        int data;
        while((data=fileInputStream.read())!=-1){
            fileOutputStream.write(data);
            fileOutputStream.flush();
        }
        System.out.println("File Copied!");
        fileOutputStream.close();
        fileInputStream.close();
    }
}
