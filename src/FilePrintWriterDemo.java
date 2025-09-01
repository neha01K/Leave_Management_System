import java.io.IOException;
import java.io.PrintWriter;

public class FilePrintWriterDemo {
    public static void main(String[] args) throws IOException {
        PrintWriter printWriter = new PrintWriter("123.txt");
        printWriter.write(100);
        printWriter.println(100);
        printWriter.println(10.5);
        printWriter.println("Okay! Let's move on");

        printWriter.flush();
        printWriter.close();
    }
}
