import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileCopyDemo3 {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter source file: ");
        String sourceFile = scanner.nextLine();

        System.out.println("Enter destination file: ");
        String destinationFile = scanner.nextLine();

        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);

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
