package Labor3.Worker;

import java.util.Random;

public enum WorkerState {
    NOT_STARTED(0,0),
    STATE1(3,7),
    STATE2(6,10),
    STATE3(2,10),
    DONE(0,0);

    private int min;
    private int max;
    private Random random;

    WorkerState(int min, int max){
        this.min = min;
        this.max = max;
        this.random = new Random();

    }


    public void work() {
        int time = random.nextInt(max-min) + min;
        Helper.sleep(time * 1000);
    }

    public static WorkerState getNextState(WorkerState workerState) {
        return switch (workerState) {
            case NOT_STARTED -> STATE1;
            case STATE1 -> STATE2;
            case STATE2 -> STATE3;
            case STATE3 -> DONE;
            default -> throw new IllegalStateException("Unexpected value: " + workerState);
        };
    }
}
