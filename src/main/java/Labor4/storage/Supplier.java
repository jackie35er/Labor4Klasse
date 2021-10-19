package Labor4.storage;


import lombok.SneakyThrows;
import util.Helper;

import java.util.Random;

public class Supplier implements Runnable {

    private Storage storage;
    private final int reps;

    private final int amountFrom, amountTo;
    private final int delayFromS, delayToS;


    public Supplier(Storage storage, int reps,
                    int delayFromS, int delayToS,
                    int amountFrom, int amountTo) {
        this.storage = storage;
        this.reps = reps;
        this.delayFromS = delayFromS;
        this.delayToS = delayToS;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
    }

    public Supplier(Storage storage, int reps, int amount) {
        this(storage,reps,0,0,amount,amount);
    }

    @Override
    public void run() {
        for(int i = 0; i < reps; i++) {
            int amount = getSupplyAmount();
            try {
                storage.supply(new Good(amount));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int sleepTime = getDelayTime();
            Helper.sleep(sleepTime * 1000);
        }
        storage.notifySupplierDone(this);
    }

    private int getSupplyAmount(){
        Random rand = new Random();
        if(amountTo == amountFrom){
            return amountTo;
        }
        return rand.nextInt(amountTo - amountFrom) + amountFrom;
    }

    private int getDelayTime(){
        Random rand = new Random();
        if(delayToS == delayFromS){
            return delayToS;
        }
        return rand.nextInt(delayToS - delayFromS) + delayFromS;
    }
}
