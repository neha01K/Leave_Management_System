import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderDemo2 {
    public static void main(String[] args) throws IOException {
        //using the second method of File Reader to read the data from the file
        File file = new File("123.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("Hello World");

        FileReader fileReader = new FileReader(file);
        char[] charArray = new char[(int)file.length()];
        fileReader.read(charArray);
        for(char ch : charArray){
            System.out.print(ch);
        }

        fileReader.close();
        file.delete();
    }
}
