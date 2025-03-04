package lab2.task2.int_solution;
import java.util.Random;

public class Producer implements Runnable {
    private IntDrop drop;
    private int[] numbers;

    public Producer(IntDrop drop, int size) {
        this.drop = drop;
        // Create an array of numbers 1, 2, ..., size.
        numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }
    }

    public void run() {
        Random random = new Random();
        // Send each number in the array.
        for (int num : numbers) {
            drop.put(num);
            try {
                Thread.sleep(random.nextInt(100)); 
            } catch (InterruptedException e) {}
        }
        // Signal termination with a sentinel value.
        drop.put(-1);
    }
}
