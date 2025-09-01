import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileBufferedWriterDemo {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("123.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("Yes");
        bufferedWriter.newLine();
        bufferedWriter.write(100);
        bufferedWriter.newLine();
        char[] ch = {'a','b','c','d'};
        bufferedWriter.write(ch);

        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
