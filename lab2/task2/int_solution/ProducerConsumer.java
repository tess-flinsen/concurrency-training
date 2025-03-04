package lab2.task2.int_solution;

public class ProducerConsumer {
    public static void main(String[] args) {
        int size = 5000; //100 //5000
        IntDrop drop = new IntDrop();
        Producer producer = new Producer(drop, size);
        Consumer consumer = new Consumer(drop);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}