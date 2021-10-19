package Labor4.storage;

public class Supplier implements Runnable {

    private Storage storage;
    private int reps;

    public Supplier(Storage storage, int reps,
                    int delayFromS, int delayToS,
                    int amountFrom, int amountTo) {
    }

    public Supplier(Storage storage, int reps, int amount) {
    }

    @Override
    public void run() {
    }
}
