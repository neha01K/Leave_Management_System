public class ThreadNaming {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        myThread t = new myThread();
        t.start();
//        System.out.println(t.getName());
//        Thread.currentThread().setName("Main ka Naya Naam");
//        System.out.println(Thread.currentThread().getName());
//        System.out.println(10/0);
    }
}
class myThread extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
}
