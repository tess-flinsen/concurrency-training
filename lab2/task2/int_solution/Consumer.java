package lab2.task2.int_solution;

import java.util.Random;

public class Consumer implements Runnable {
    private IntDrop drop;

    public Consumer(IntDrop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        int num;
        while ((num = drop.take()) != -1) { 
            System.out.println("Number received: " + num);
            try {
                Thread.sleep(random.nextInt(100)); 
            } catch (InterruptedException e) {}
        }
        System.out.println("Consumer received sentinel. Terminating.");
    }
}
