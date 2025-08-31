public class ThreadDemo {
    public static void main(String[] args) {
        MyThreadc t = new MyThreadc();
//        Thread t1 = new Thread(t);
//        t1.start();

        t.start();
    }
}
class MyThreadc extends Thread{
    public void run(){
        System.out.println("Run method of MyThreadc");
    }
}

