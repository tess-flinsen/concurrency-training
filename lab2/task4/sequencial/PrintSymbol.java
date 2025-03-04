package lab2.task4.sequencial;

public class PrintSymbol {

    public static void main(String[] args) throws InterruptedException {
        int symbolsPerLine = 99;
        int numLines = 90;
        Sync turn = new Sync(numLines, symbolsPerLine);
        int controlValue = 0;

        Thread first = new Thread(new SymbolSyncTest('|', turn, controlValue) );
        Thread second = new Thread(new SymbolSyncTest('\\', turn, ++controlValue) );
        Thread third = new Thread(new SymbolSyncTest('/', turn, ++controlValue) );

        first.start();
        second.start();
        third.start();

        first.join();
        second.join();
        third.join();
    }
}