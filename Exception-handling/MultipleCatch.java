public class MultipleCatch {
    public static void main(String[] args) {
        Student s = null;
        try{
            s.setName("Michael");
        }catch(ArithmeticException aE){
            System.out.println("Arithmetic is the problem");
        }
        catch(NullPointerException nP){
            System.out.println("Null pointer problem it is");
        }

    }
}
