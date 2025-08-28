import java.io.File;
import java.io.IOException;

public class FileConstructorDemo {
    public static void main(String[] args) throws IOException {

        //this calls the File(String name) constructor
        File file = new File("abc.text");
        file.createNewFile();

        //this calls File(String directoryName, String name);
        File directory = new File("directory1");
        directory.mkdir();
        File file1 = new File("directory1","123.txt");
        file1.createNewFile();

        System.out.println(file1.exists());

        //this use File(File referenceVariable, String name)

        File file2 = new File("directory2");
        file2.mkdir();
        File file3 = new File(file2, "newFile.txt");
        file3.createNewFile();

        System.out.println(file3.exists());

        //cleanup
        file.delete();
        file1.delete();
        directory.delete();
        file3.delete();
        file2.delete();
    }
}
