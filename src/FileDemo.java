import java.io.File;
import java.io.IOException;

public class FileDemo{
    public static void main(String[] args) throws IOException {
        File file = new File("abc.txt");

        //file didn't exist till this point
        System.out.println("File Exist? "+file.exists());

        //a physical file is created at this point
        file.createNewFile();

        //checks if the the file exists - true
        System.out.println("File Exist? "+file.exists());

        //making directory with the new File Object
        File file1 = new File("directory");
        boolean directoryCreated = file1.mkdir();
        System.out.println("Directory Exist? "+directoryCreated);

        //cleanup
        file.delete();
        file1.delete();

    }
}