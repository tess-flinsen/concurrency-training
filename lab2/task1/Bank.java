package lab2.task1;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    
    private final Lock lock = new ReentrantLock();
    private final Condition sufficientFunds = lock.newCondition();
    private final AtomicIntegerArray atomicAccounts;

    public Bank(int n, int initialBalance){
        accounts = new int[n];
        atomicAccounts = new AtomicIntegerArray(n);
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
            atomicAccounts.set(i, initialBalance);
        }
        ntransacts = 0;
    }
    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) test();
    }

    public synchronized void syncTransfer(int from, int to, int amount) {
        if (accounts[from] < amount) return; 

        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;

        if (ntransacts % NTEST == 0) test();
    }
    
    public synchronized void waitTransfer(int from, int to, int amount) {
        while(accounts[from] < amount) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        notifyAll();
        if (ntransacts % NTEST == 0) test();
    }

    public void lockTransfer(int from, int to, int amount) {
        lock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();  
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0) test();
            sufficientFunds.signalAll();  
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("transferWithLock interrupted: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void atomicTransfer(int from, int to, int amount) {
        try {
            while (true) {
                int fromBalance = atomicAccounts.get(from);
                if (fromBalance < amount)
                    return; 

                if (atomicAccounts.compareAndSet(from, fromBalance, fromBalance - amount)) {
                    //It ensures that the account balance is only updated if it hasn't changed since it was last read. 
                    //If another thread has modified the balance in the meantime, the operation fails, and the loop will retry.
                    atomicAccounts.addAndGet(to, amount); 
                    break;
                }
            }
            ntransacts++;
            if (ntransacts % NTEST == 0) test();
        } catch (Exception e) {
            System.err.println("Atomic Transfer error: " + e.getMessage());
        }
    }
    public void test()
    {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    }
    public int size(){
        return accounts.length;
    }
}
