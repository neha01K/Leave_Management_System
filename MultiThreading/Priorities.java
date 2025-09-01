public class Priorities {
    public static void main(String[] args) {
//        Thread t1 = new Thread(
//                ()-> {
//            System.out.println("Thread 1 is running");
//        });
//        Thread t2 = new Thread(() ->{
//            System.out.println("Thread 2 is running");
//        });
//
//        //creating above thread won't be enough, need to start them to use them
//        t1.start();
//        t2.start();
//
//        //setting priority in arguments
//        threadPriority P1 = new threadPriority("Bada", 10);
//
//        threadPriority P2 = new threadPriority("chota",3);
//        P1.start();
//        P2.start();
//
//        //setting priority using method
//        thPri threadPrio = new thPri();
//        threadPrio.setPriority(Thread.MIN_PRIORITY);
//        threadPrio.start();


        //checking default priority
        System.out.println(Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(7);
        System.out.println(Thread.currentThread().getPriority());
        NewThread m = new NewThread();
        System.out.println(m.getPriority());

    }
}

class threadPriority extends Thread{
    public threadPriority(String name, int priority){
        super(name);
        setPriority(priority);
    }
    public void run(){
            System.out.println(getName() + " -it's Priority is "+ getPriority());
    }
}


class thPri extends Thread{
    public void run(){
        System.out.println(getName()+" : name of thread\n"+getPriority()+" : priority");
    }
}

class NewThread extends Thread{
}

