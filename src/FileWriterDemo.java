import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("abc.txt");
        fileWriter.write(97);
        fileWriter.write('n');
        fileWriter.write("eha Khandelwal\nIn Time Tec");
        char[] characters = {'a','b','c'};
        fileWriter.write("\n");
        fileWriter.write(characters);

        fileWriter.flush();
        fileWriter.close();


    }
}
