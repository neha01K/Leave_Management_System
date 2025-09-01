import java.io.*;
public class FileOutputStreamDemo{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("bbc.txt");
        fileOutputStream.write(78);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}