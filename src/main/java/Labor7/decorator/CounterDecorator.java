package Labor7.decorator;

import java.util.Objects;

public abstract class CounterDecorator implements Counter {

    private final Counter counter;

    public CounterDecorator(Counter counter) {
        if(counter == null)
            throw new IllegalArgumentException("counter cant be null");
        this.counter = counter;
    }

    @Override
    public int read() {
        return counter.read();
    }

    @Override
    public Counter tick() {
        return counter.tick();
    }
}
