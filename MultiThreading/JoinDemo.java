public class JoinDemo {
    public static void main(String[] args) throws InterruptedException{
        /*newC obj = new newC();   //thread is instantiated here
        obj.start();
        obj.join(6000);   //specifying join only 6 secs this Main thread will wait
        for(int i=0; i<5; i++){
            System.out.println("main thread");
        }
         */

        newC.t = Thread.currentThread();
        newC th = new newC();
        th.start();
        for(int i=0; i<5; i++){
            System.out.println("main");
            Thread.sleep(2000);
        }
    }
}
class newC extends Thread{
    static Thread t;
    public void run(){

        //---------- this was called by main thread------------


        /*for(int i=0; i<5; i++){
            System.out.println("child thread");
            try{
                sleep(2000);  //this says it will wait for 2 secs everytime b4 giving output
            }
            catch(InterruptedException e){
                System.out.println(e);
            }
        }*/


        //-------here we are trying calling main method to join in between--------
        try{
            t.join();
        }catch(InterruptedException e){ }

        for(int i=0; i<5; i++){
            System.out.println("child");
        }
    }
}
