public class ThreadSleepDemo {
    public static void main(String[] args) {
        sleepdemo s = new sleepdemo();
        s.start();
        s.interrupt();
        System.out.println("End of Main thread");
    }
}
class sleepdemo extends Thread{
    public void run(){
        try{
            for(int i=0; i<5; i++){
                System.out.println("I am Lazy");
                sleep(2000);
            }
        }catch(InterruptedException e){
            System.out.println("Thread got interrupted");
        }
    }
}
