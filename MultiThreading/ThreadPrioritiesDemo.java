public class ThreadPrioritiesDemo {
    public static void main(String[] args) {
        //System.out.println(Thread.currentThread().getPriority());
        //Thread.currentThread().setPriority(4);
        threads t = new threads();
        //t.setPriority(10);
        t.start();
        //t.yield();
        for(int i=0; i<10; i++){
            System.out.println("Main Thread");
        }
    }
}

class threads extends Thread{
    public void run(){
        for(int i=0; i<10; i++){
            System.out.println("Child Thread");
            Thread.yield();
        }
    }
}