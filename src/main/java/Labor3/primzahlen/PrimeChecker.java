package Labor3.primzahlen;

import java.util.concurrent.Callable;

public class PrimeChecker implements Callable<Boolean> {

    private final long candidate;
    private final int delay;

    public PrimeChecker(long candidate, int delay) {
        this.candidate = candidate;
        this.delay = delay;
    }

    @Override
    public Boolean call() throws Exception {
        if(this.candidate < 2) return false;
        for(long i = 2; i * i <= this.candidate; i++){
            Thread.sleep(delay);
            if(this.candidate % i == 0) return false;
        }
        return true;
    }
}
