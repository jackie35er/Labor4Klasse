package Labor2.streaming;


import java.math.BigInteger;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Streaming {

    public static int[] a() {
        return IntStream.range(1,20)
                .filter(n -> n % 2 == 1)
                .map(n -> n * n)
                .toArray();
    }

    public static double b() {
        return IntStream.rangeClosed(1,100)
                .mapToDouble(n -> 1/(((double)n + 1) * (n+2)))
                .sum();
    }

    public static int[] c() {
        return IntStream.generate(new RandomSupplier(46))
                .distinct()
                .limit(6)
                .sorted()
                .toArray();
    }

    public static long d() {
        return LongStream.rangeClosed(1,20)
                .reduce(1,(oldValue, newValue) -> oldValue * newValue);
    }

    public static long e() {
        return IntStream.rangeClosed(1,1000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining())
                .chars()
                .filter(n -> n == '1')
                .count();
    }

    public static BigInteger f() {
        Optional<BigInteger> op = Stream.generate(new FactorialSupplier())
                                    .filter(n -> n.toString().length() > 20)
                                    .findFirst();
        return op.orElseThrow();
    }

    public static int g() {
        String minString = "1" + Stream.generate(() ->"0").limit(10000).collect(Collectors.joining());
        BigInteger min = new BigInteger(minString);
        FactorialSupplier fs = new FactorialSupplier();
        boolean match = Stream.generate(fs)
                .anyMatch(n -> n.compareTo(min) > 0);
        if(match){
            return fs.getI();
        }
        else{
            return 0;
        }
    }

    public static int h() {
        FibonacciSupplier fs = new FibonacciSupplier();
        Optional<BigInteger> op = Stream.generate(fs)
                .filter(n -> n.toString().length() == 1000)
                .findFirst();
        if(op.isPresent()){
            return fs.i;
        }
        else{
            return 0;
        }
    }

    private static class RandomSupplier implements IntSupplier {
        private final int bound;
        private final Random r;

        public RandomSupplier(int bound){
            this.r = new Random();
            this.bound = bound;
        }
        @Override
        public int getAsInt() {
            return r.nextInt(bound - 1) + 1;
        }
    }

    public static class FibonacciSupplier implements Supplier<BigInteger>{

        private int i = 2;
        private BigInteger oldValue = BigInteger.ONE;
        private BigInteger newValue = BigInteger.ONE;

        @Override
        public BigInteger get() {
            BigInteger erg = newValue.add(oldValue);
            oldValue = newValue;
            newValue = erg;
            i++;
            return erg;
        }
    }

}
