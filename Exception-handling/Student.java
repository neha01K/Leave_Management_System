public class Student {
    private String name;
    private int stuID;

    public void setName(String name){
        this.name = name;
    }
    public void setID(int stuID){
        this.stuID = stuID;
    }

    public String getName(){
        return name;
    }
    public int getID(){
        return stuID;
    }

    public static void main(String[] args) {
        Student student1  = null;
        try{
            String s = "Mohan";
            student1.setName(s);

        } catch(ArithmeticException a){
            System.out.println(a);
        } catch(NullPointerException n){
            System.out.println(n);
            System.out.println("this is null pointer exception");
        }
    }
}
