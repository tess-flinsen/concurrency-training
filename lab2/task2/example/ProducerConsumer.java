package lab2.task2.example;

public class ProducerConsumer {
    public static void main(String[] args) {
        Drop drop = new Drop();
        Producer producer = new Producer(drop);
        Consumer consumer = new Consumer(drop);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}