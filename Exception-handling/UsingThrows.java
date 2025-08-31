import java.io.FileNotFoundException;
import java.io.FileReader;

public class UsingThrows {
    public static void main(String[] args) {
        try{
            readFile();
        }catch(FileNotFoundException fnf){
            System.out.println("File is not present in our system");
        }

    }

    //throws will confirm that this method might throw an exception
    //this exception will be handle by the
    static void readFile() throws FileNotFoundException{
        FileReader fr = new FileReader("test.txt");
    }
}
