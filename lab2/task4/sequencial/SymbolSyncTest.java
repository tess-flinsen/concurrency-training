package lab2.task4.sequencial;

public class SymbolSyncTest implements Runnable {
    private final char symbol;
    private final Sync sync;
    private final int controlValue;

    public SymbolSyncTest(char s, Sync turn, int control) {
        symbol = s;
        this.sync = turn;
        this.controlValue = control;
    }
    @Override
    public void run() {
        while(!sync.shouldStop()){
            //sync.print(symbol);
            sync.waitAndChange(controlValue, symbol);
        }
    }
}