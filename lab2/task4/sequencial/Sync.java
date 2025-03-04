package lab2.task4.sequencial;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Sync {
    private int turn = 0;
    private int num = 0;
    private boolean stop = false;
    private final int numLines;
    private final int symbolsPerLine;

    public Sync(int lines, int symb ){
        this.numLines = lines;
        this.symbolsPerLine = symb;
    }
    public synchronized void waitAndChange(int control, char s){
        while (getTurn() != control){
            try{
                wait();
            }catch(InterruptedException e){
                Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.print(s);
        turn++;
        num++;

        if(num%symbolsPerLine == 0){
            System.out.println();
        }
        if(num + 2 == numLines * symbolsPerLine){
            stop = true;
        }
        notifyAll();
    }

    public void print(char s){
        num++;
        System.out.print(s);
        if(num % symbolsPerLine == 0){
            System.out.println();
        }
        if(num + 2 == numLines * symbolsPerLine){
            stop = true;
        }
    }

    int getTurn() {
        return turn % 3;
    }

    boolean shouldStop() {
        return stop;
    }
}