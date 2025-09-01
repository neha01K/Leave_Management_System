import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileBufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("123.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while(line!=null){
            System.out.println(line);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
    }
}
