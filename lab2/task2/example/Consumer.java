package lab2.task2.example;
import java.util.Random;

public class Consumer implements Runnable{
    private Drop drop;
    public Consumer(Drop drop) {
        this.drop = drop;
    }
    public void run() {
        Random random = new Random();
        for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}
