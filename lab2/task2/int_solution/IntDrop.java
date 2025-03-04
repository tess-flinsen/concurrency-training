package lab2.task2.int_solution;


public class IntDrop {
    // Message sent from producer to consumer.
    private int number;
    //Producer sends a message, consumer receives the message.
    // True if consumer should wait for producer to send message,
    // false if producer should wait for consumer to retrieve message.
    private boolean empty = true;

    public synchronized int take() {
        // Wait until message is available.
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = true; // Toggle status.
        notifyAll(); // Notify producer that status has changed.
        return number;
    }

    public synchronized void put(int number) {
        // Wait until message has been retrieved.
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false; // Toggle status.
        this.number = number;  // Store message.
        notifyAll(); // Notify consumer that status has changed.
    }
}

