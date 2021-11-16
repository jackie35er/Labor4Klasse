package Labor3.primzahlen;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class PrimeFinder {

    private final SortedSet<Long> primes = new TreeSet<>();
    private ThreadPoolExecutor executor;
    private final int delay;
    private final long fromInclusive;
    private final long toExclusive;

    public PrimeFinder(int delay, int fromInclusive, int toExclusive) {
        if (toExclusive <= fromInclusive) throw new IllegalArgumentException();
        this.delay = delay;
        this.fromInclusive = fromInclusive;
        this.toExclusive = toExclusive;
    }

    public int countRunningCheckers() {
        return executor.getActiveCount();
    }

    /**
     * Teilt alle Zahlen in [from, to[ auf Threads auf,
     * welche die Primalität der jeweiligen Zahl bestimmen
     */
    public void findPrimes() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) (toExclusive - fromInclusive));
        Map<Long, Future<Boolean>> potentialPrimes = new TreeMap<>();

        for (long i = fromInclusive; i < toExclusive; i++) {
            potentialPrimes.put(i, executor.submit(new PrimeChecker(i, delay)));
        }

        Thread daemon = new Thread(() -> {
            while(countRunningCheckers() != 0) {
                System.out.println(countRunningCheckers());
                printPrimesAndSleep(potentialPrimes);
            }
            System.out.println("-----------------------------------");
            printPrimesAndSleep(potentialPrimes);
            executor.shutdown();
        });
        daemon.setDaemon(true);
        daemon.start();
    }

    public Stream<Long> getPrimes() {
        return primes.stream();
    }

    private void printPrimesAndSleep(Map<Long, Future<Boolean>> potentialPrimes){
        System.out.print("[ ");
        for (Map.Entry<Long, Future<Boolean>> entry : potentialPrimes.entrySet()) {
            try {
                if (entry.getValue().isDone() && entry.getValue().get()) {
                    primes.add(entry.getKey());
                    System.out.print(entry.getKey() + " ");
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalArgumentException();
            }
        }
        System.out.print("]" + "\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrimeFinder primes =  new PrimeFinder(1300,10,50);
        primes.findPrimes();
    }
}
