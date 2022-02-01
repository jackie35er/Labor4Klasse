package Labor7.decorator;

public class SimpleCounter implements Counter {

    private int count;

    public SimpleCounter() {
        this(0);
    }

    public SimpleCounter(int count) {
        this.count = count;
    }


    @Override
    public int read() {
        return count;
    }

    @Override
    public Counter tick() {
        count++;
        return this;
    }
}
