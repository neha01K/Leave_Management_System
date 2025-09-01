package queue;

import java.util.ArrayDeque;

public class DequeueDemo {
    public static void main(String[] args) {

        //ArrayDeque extends the Dequeue interface
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        ad.offer(3);
        ad.offer(4);
        ad.offer(5);
        ad.offer(6);
        ad.offer(7);

        System.out.println(ad);
        System.out.println("peek: "+ad.peek());
        System.out.println(ad.pollLast());



    }
}
