package Labor3.piCalculator;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class PiCalculator {

    public static double calcPiWithNThreads(int limit, int threadCnt) throws InterruptedException, ExecutionException {
        LinkedList<Integer> calculationCounts = new LinkedList<>(DistributionCalculator.calculateOperations(limit, threadCnt));
        List<Callable<Double>> tasks = new ArrayList<>();

        int min = 0;
        int max = -1;
        for (int i = 0; i < threadCnt; i++) {
            int currentLimit = calculationCounts.remove();
            max += currentLimit;
            tasks.add(new PartialSum(min, max));
            System.out.printf("%d.Thread[%5d<->%5d] -> %d\n",i,min,max,currentLimit);
            min += currentLimit;
        }

        ExecutorService threadpool = Executors.newFixedThreadPool(threadCnt);
        List<Future<Double>> results = threadpool.invokeAll(tasks);

        double sum = 0;
        for(var value : results){
            sum += value.get();
        }

        threadpool.shutdown();
        return sum * 4;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(calcPiWithNThreads(100000,7));
    }
}
