import java.io.File;

public class FileMethodsDemo {
    public static void main(String[] args) {

        //Displaying all the files and directories present in the C:\Users\neha.k\IdeaProjects\FileHandling

        File file = new File("C:\\Users\\neha.k\\IdeaProjects\\FileHandling");
        String[] fileList = file.list();
        for(String fL : fileList){
            System.out.println(fL);
        }

        //-----------------------------------------------------------------------
        /*Displaying all the files and directories present in the C:\Users\neha.k\IdeaProjects\FileHandling
        and counting them */

        int count = 0;
        for(String fL : fileList){
            count++;
            System.out.println(fL);
        }
        System.out.println("Total number of Files & Directories: "+count);

        //-----------------------------------------------------------------------
        //Displaying only the Files and counting its number

        int countFile = 0;
        for(String fL: fileList){

            //this will tell that "file" is the main directory in which all the fL is present-file or directory
            File file1 = new File(file, fL);

            if(file1.isFile()){
                countFile++;
                System.out.println(fL);
            }
        }
        System.out.println("Total number of File: "+ countFile);

        //-----------------------------------------------------------------------
        //Displaying the Directories name & their count

        int countDirectory=0;
        for(String dirList : fileList){
            File file1 = new File(file,dirList);
            if(file1.isDirectory()){
                countDirectory++;
                System.out.println(dirList);
            }
        }
        System.out.println("Total number of Directories: "+countDirectory);

        file.delete();
    }
}
