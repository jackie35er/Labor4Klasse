package Labor3.Worker;


public class Worker extends Thread {
    private WorkerState workerState;

    public Worker() {
        this.workerState = WorkerState.NOT_STARTED;
    }

    @Override
    public void run() {
        if (this.workerState != WorkerState.NOT_STARTED)
            throw new IllegalStateException("Unexpected Value" + this.workerState);
        this.workerState =  WorkerState.getNextState(this.workerState);

        while (this.workerState != WorkerState.DONE) {
            workerState.work();
            this.workerState = WorkerState.getNextState(this.workerState);
        }
    }

    public WorkerState getWorkerState() {
        return workerState;
    }
}
