package Labor4.storage;

import org.jetbrains.annotations.NotNull;
import util.Helper;

import java.util.Random;

public class Consumer implements Runnable {

    private Storage storage;
    private int amount;
    private int delayFromS, delayToS;


    public Consumer(@NotNull Storage storage, int delayFromS, int delayToS, int amount) {
        this.storage = storage;
        this.amount = amount;
        this.delayFromS = delayFromS;
        this.delayToS = delayToS;
    }

    public Consumer(@NotNull Storage storage, int amount) {
        this(storage,0,0,amount);
    }


    @Override
    public void run() {
        while(storage.hasSupplier()){
            try {
                if(storage.size() >= amount)
                    storage.get(amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int delay = getDelayTime();
            Helper.sleep(delay);
        }
        try {
            storage.get(storage.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getDelayTime(){
        Random rand = new Random();
        if(delayToS == delayFromS){
            return delayToS;
        }
        return rand.nextInt(delayToS - delayFromS) + delayFromS;
    }
}
