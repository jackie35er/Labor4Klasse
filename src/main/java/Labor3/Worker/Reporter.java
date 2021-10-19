package Labor3.Worker;

import util.Helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reporter extends Thread {
    private List<Worker> workers;
    private int count;

    public Reporter(int count) {
        this.workers = Stream.generate(Worker::new)
                .limit(count)
                .collect(Collectors.toList());
        this.count = count;
    }


    @Override
    public void run() {
        workers.forEach(Thread::start);
        System.out.println("St 1|St 2|St 3|Done");
        Map<WorkerState,Long> stateCounts = getStateCounts();
        while (stateCounts.getOrDefault(WorkerState.DONE, 0L) != count) {
            System.out.printf("%4d|%4d|%4d|%4d\n",
                    stateCounts.getOrDefault(WorkerState.STATE1,0L),
                    stateCounts.getOrDefault(WorkerState.STATE2,0L),
                    stateCounts.getOrDefault(WorkerState.STATE3,0L),
                    stateCounts.getOrDefault(WorkerState.DONE,0L));
            Helper.sleep(1000);
            stateCounts = getStateCounts();
        }
        System.out.printf("%4d|%4d|%4d|%4d\n",
                stateCounts.getOrDefault(WorkerState.STATE1,0L),
                stateCounts.getOrDefault(WorkerState.STATE2,0L),
                stateCounts.getOrDefault(WorkerState.STATE3,0L),
                stateCounts.getOrDefault(WorkerState.DONE,0L));

    }

    private Map<WorkerState, Long> getStateCounts() {

        return workers.stream()
                .collect(Collectors.groupingBy(Worker::getWorkerState,Collectors.counting()));
    }

}
