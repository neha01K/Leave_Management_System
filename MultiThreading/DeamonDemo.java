public class DeamonDemo {
    public static void main(String[] args) {
        Thread autoSaved = new Thread(() ->
        {
            while(true){
                System.out.println("Auto-saving draft...");
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    System.out.println("Auto-saving interrupted!!");
                    break;
                }
            }
        });

        autoSaved.setDaemon(true);
        autoSaved.start();
        System.out.println("User is typing..");
        try{
            Thread.sleep(700);
        }catch(InterruptedException e1){
            e1.printStackTrace();
        }

        System.out.println("User closes Editor. Application Exiting!");

        //manually triggering the exception- InterruptedException
        autoSaved.interrupt();
    }
}

