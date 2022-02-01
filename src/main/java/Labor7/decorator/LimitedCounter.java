package Labor7.decorator;

public class LimitedCounter extends CounterDecorator {

    private final int limit;

    public LimitedCounter(Counter counter, int limit) {
        super(counter);
        this.limit = limit;
    }

    @Override
    public int read() {
        return super.read() >= limit ? limit : super.read();//in case super.tick() adds more than 1 to its count;
    }

    @Override
    public Counter tick() {
        super.tick();

        return this;
    }
}
