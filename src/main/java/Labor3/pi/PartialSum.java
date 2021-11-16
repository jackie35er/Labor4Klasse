package Labor3.pi;

import java.util.concurrent.Callable;

public class PartialSum implements Callable<java.lang.Double> {

    private final int minInclusive;
    private final int maxExclusive;

    public PartialSum(int minInclusive, int maxExclusive) {
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    @Override
    public java.lang.Double call() throws Exception {
        double sum = 0.0;
        for(int i = minInclusive; i < maxExclusive; i++){
            double curr = 1.0/(2 * i + 1);
            sum += i % 2 == 0 ? curr : -curr; //statt Math.pow, wegen kuerzerer Laufzeit
        }
        return sum;
    }
}
