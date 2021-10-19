package Labor4.storage;

public class Consumer implements Runnable {

    private Storage storage;
    private int amount;

    public Consumer(Storage storage, int delayFromS, int delayToS, int amount) {
    }

    public Consumer(Storage storage, int amount) {
    }


    @Override
    public void run() {
    }
}
