import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
public class FileReaderDemo {
    public static void main(String[] args) throws Exception {

        File file = new File("123.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("Hello World");

        FileReader fileReader = new FileReader(file);

        int character = fileReader.read();
        while(character != -1){
            System.out.print((char)character);
            character=fileReader.read();
        }

        file.delete();
        fileReader.close();
        fileWriter.close();
    }
}
