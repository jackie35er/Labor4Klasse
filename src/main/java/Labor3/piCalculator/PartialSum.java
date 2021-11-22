package Labor3.piCalculator;

import java.util.concurrent.Callable;

public class PartialSum implements Callable<Double> {

    private double sum;
    private int min;
    private int max;

    public PartialSum(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public Double call() throws Exception {
        double sum = 0;
        int factor = 1;
        for(int i = min; i <= max; i++) {
            sum += (1.0  * factor/(2 * i + 1)) ;
            factor *= -1;
        }
        return sum;
    }
}
