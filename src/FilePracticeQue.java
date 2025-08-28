import java.io.*;

public class FilePracticeQue {
    public static void main(String[] args) throws IOException {

        //Q1: Merging two files- "123.txt" & "abc.txt" into the "file3.txt"

        /*PrintWriter printWriter = new PrintWriter("file3.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("123.txt"));
        String line = bufferedReader.readLine();
        while(line!=null){
            printWriter.println(line);
            line = bufferedReader.readLine();
        }
        bufferedReader = new BufferedReader(new FileReader("abc.txt"));
        line = bufferedReader.readLine();
        while(line!=null){
            printWriter.println(line);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        printWriter.close();*/

        //-----------------------------------------------------------------------------------
        //Q2: Merging two files- "123.txt" & "abc.txt" into the "file3.txt" but line by line

        /*PrintWriter printWriter = new PrintWriter("file3.txt");
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("123.txt"));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("abc.txt"));

        String line1 = bufferedReader1.readLine();
        String line2 = bufferedReader2.readLine();

        while(line1!=null || line2!=null){
            if(line1!=null){
                printWriter.println(line1);
                line1 = bufferedReader1.readLine();
            }
            if(line2!=null){
                printWriter.println(line2);
                line2 = bufferedReader2.readLine();
            }

        }
        printWriter.flush();
        bufferedReader1.close();
        bufferedReader2.close();*/

        //-----------------------------------------------------------------------------------
        //Q3. Merge data from all the files present in C:\Users\neha.k\IdeaProjects\FileHandling folder to output.txt

        /*PrintWriter printWriter = new PrintWriter("output.txt");
        File file = new File("C:\\Users\\neha.k\\IdeaProjects\\FileHandling\\src\\123");
        String[] fileList = file.list();

        for(String fileName : fileList){
            File file1 = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));

            String line = bufferedReader.readLine();
            while(line!=null){
                printWriter.println(line);
                line = bufferedReader.readLine();
            }

        }
        printWriter.flush();
        printWriter.close();*/

        //-----------------------------------------------------------------------------------
        //Q4. File Extraction process

        /*PrintWriter printWriter = new PrintWriter("output.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("123.txt"));
        String line = bufferedReader.readLine();
        while(line!=null){
            boolean available = false;
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader("abc.txt"));
            String line2 = bufferedReader1.readLine();
            while(line2!=null){
                if(line.equals(line2)){
                    available =true;
                    break;
                }
                line2 = bufferedReader1.readLine();
            }
            if(available==false){
                printWriter.println(line);
            }
            line = bufferedReader.readLine();
        }
        printWriter.flush();
        printWriter.close();*/

        //-----------------------------------------------------------------------------------
        //Q5. Remove Duplicates present in the file

        PrintWriter printWriter = new PrintWriter("output.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("abc.txt"));
        String line1 = bufferedReader.readLine();
        while(line1!=null){
            boolean available = false;
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader("output.txt"));
            String line2 = bufferedReader1.readLine();
            while(line2!=null){
                if(line1.equals(line2)){
                    available = true;
                    break;
                }
                line2 = bufferedReader1.readLine();
            }
            if(available==false){
                printWriter.println(line1);
                printWriter.flush();
            }
            line1 = bufferedReader.readLine();
        }
        bufferedReader.close();

    }
}
