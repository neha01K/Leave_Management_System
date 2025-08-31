public class Test {
    public static void main(String[] args) {
        myRunnable tObj = new myRunnable();
        Thread t = new Thread(tObj);
        t.start();
        System.out.println("here again in Main Method");
    }
}

class myRunnable implements Runnable{
    public void run(){
        System.out.println("myRunnable class Run method");
    }
}

