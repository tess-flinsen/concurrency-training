package lab2.task2.example;

public class Drop {
    // Message sent from producer to consumer.
    private String message;
    //Producer sends a message, consumer receives the message.
    // True if consumer should wait for producer to send message,
    // false if producer should wait for consumer to retrieve message.
    private boolean empty = true;

    public synchronized String take() {
        // Wait until message is available.
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = true; // Toggle status.
        notifyAll(); // Notify producer that status has changed.
        return message;
    }

    public synchronized void put(String message) {
        // Wait until message has been retrieved.
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false; // Toggle status.
        this.message = message;  // Store message.
        notifyAll(); // Notify consumer that status has changed.
    }
}
