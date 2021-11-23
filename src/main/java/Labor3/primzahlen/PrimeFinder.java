package Labor3.primzahlen;

import util.Helper;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimeFinder {

    private final SortedSet<Long> primes = new TreeSet<>();
    private ThreadPoolExecutor executor;
    private final int delay;
    private final int from;
    private final int to;

    public PrimeFinder(int delay, int from, int to) {
        if (to <= from) throw new IllegalArgumentException();
        this.delay = delay;
        this.from = from;
        this.to = to;
    }

    public int countRunningCheckers() {
        return executor.getActiveCount();
    }

    public void findPrimes() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) (to - from));
        Map<Long, Future<Boolean>> potentialPrimes = new TreeMap<>();

        IntStream.range(from,to)
                .forEach((i) -> potentialPrimes.put(
                        (long) i,
                        executor.submit(new PrimeChecker(i, delay)))
                );

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
        Helper.sleep(1000);
    }

    public static void main(String[] args) {
        PrimeFinder primes =  new PrimeFinder(1300,10,50);
        primes.findPrimes();
    }
}
