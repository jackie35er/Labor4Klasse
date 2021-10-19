package Labor2.streaming;

import java.math.BigInteger;
import java.util.function.Supplier;

public class FactorialSupplier implements Supplier<BigInteger> {

    private int i = 0;
    private BigInteger factorial = BigInteger.ONE;

    @Override
    public BigInteger get() {
        i++;
        factorial = factorial.multiply(BigInteger.valueOf(i));
        return factorial;
    }

    public int getI() {
        return i;
    }
}
