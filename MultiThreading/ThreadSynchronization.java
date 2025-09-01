public class ThreadSynchronization {
    public static void main(String[] args) {
        TicketBooking t = new TicketBooking();
        Thread t0 = new Thread(()->{
            for(int i=0; i<2; i++){
                t.bookTicket(2);
            }
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                System.out.println("Interruption!!!");
            }
        });

        Thread t1 = new Thread(()->{
            for(int i=0; i<2; i++){
                t.bookTicket(3);
            }
            try{
                Thread.sleep(40);
            }catch(InterruptedException e){
                System.out.println("Interruption!!!");
            }
        });

        t0.start();
        t1.start();

        try{
            t0.join();
            t1.join();
        }
        catch(InterruptedException e){
            System.out.println("Interruption!!!");
        }

        System.out.println("Available Tickets: "+t.getAvailableTickets());
    }
}

class TicketBooking{
    private int availableTickets = 10;

    public synchronized void bookTicket(int tickets){
        if(availableTickets>=tickets){
            availableTickets-=tickets;
            System.out.println(tickets + ": tickets booked, "+availableTickets+": tickets available");
        }
        else{
            System.out.println("Tickets are not available");
        }
    }
    public int getAvailableTickets(){
        return availableTickets;
    }
}
