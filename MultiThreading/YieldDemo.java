public class YieldDemo {
    public static void main(String[] args) {
        childClass c = new childClass();
        c.start();
        for(int i=0; i<10; i++){
            System.out.println("Main method");
        }
    }
}
class childClass extends Thread{
    public void run(){
        for(int i=0; i<10; i++){
            System.out.println("2nd class");
            Thread.yield();
        }
    }
}
