package Labor3.pi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PiCalculator {

    public static double calcPiWithNThreads(int limit, int threadCnt) {
        if(threadCnt < 1) throw new IllegalArgumentException();

        ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
        int numbersPerThread = limit / threadCnt;
        List<Future<Double>> partialSums = new ArrayList<>();
        int i;
        for(i = 0; i < threadCnt - 1; i++){
            System.out.println("Thread-" + i + ": [" + i * numbersPerThread + " , " +  (i + 1) * numbersPerThread + " ] =>" + numbersPerThread);
            partialSums.add(executor.submit(
                    new PartialSum(i * numbersPerThread, (i + 1) * numbersPerThread)
            ));
        }
        System.out.println("Thread-" + i + ": [" + i * numbersPerThread + " , " + limit + " ] =>" + (limit - i * numbersPerThread));
        partialSums.add(executor.submit(new PartialSum(i * numbersPerThread, limit))); //Um Rundungsfehler auszugleichen

        double sum = 0;
        for(Future<Double> future : partialSums){
            try {
                sum += future.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalArgumentException();
            }
        }
        executor.shutdown();
        return sum * 4;
    }

    public static void main(String[] args) {
        System.out.println("Ergebnis: " + PiCalculator.calcPiWithNThreads(100_000, 10));
    }
}
