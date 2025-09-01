public class SynchronizationDemo {
    private int counter=0;
    public void inc(){
        synchronized(this){
            counter++;
        }
    }

    public int getCounter(){
        return counter;
    }
    public static void main(String[] args) {
        SynchronizationDemo sd = new SynchronizationDemo();
        Thread thrd = new Thread(()->{
               for(int i=0; i<1000; i++) {
                   sd.inc();
               }
        });

        Thread thrd2 = new Thread(()->{
            for(int i=0; i<1000; i++) {
                sd.inc();
            }
        });

        thrd.start();
        thrd2.start();
        try{
            thrd.join();
            thrd2.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(sd.getCounter());
    }
}
