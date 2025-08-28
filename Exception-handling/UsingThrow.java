import java.sql.SQLOutput;

public class UsingThrow {
    public static void main(String[] args) {
        try {
            setAge(25);
            setAge(-1);
            setAge(90);
        }
        catch(IllegalArgumentException illArg){
            System.out.println("Exception caught: "+ illArg.getMessage());
        }
        setAge(90);
    }

    static void setAge(int age){
        if(age<0){
            throw new IllegalArgumentException("Negative age doesn't make sense");
        }
        System.out.println("Age set to "+age);
    }
}
