public class Main{

    public static void main(String[] args) {
        MyThread mT = new MyThread();
        mT.start();
        mT.run("Okay!");
    }
}

class MyThread extends Thread{
//    public void start(){
//       super.start();
//        System.out.println("that's our Start method");
//    }
    public void run(){
        System.out.println("No-arg run method");
    }

    public void run(String str){
        System.out.println("Overrided run method: "+ str+ " with this string");
    }
}