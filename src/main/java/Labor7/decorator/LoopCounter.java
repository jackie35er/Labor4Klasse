package Labor7.decorator;

public class LoopCounter implements Counter {

    private int[] values;
    private int index;

    public LoopCounter(int... values) {
        if(values == null || values.length == 0)
            throw new IllegalArgumentException("values cant be null");
        this.values = values.clone();
        index = 0;
    }

    @Override
    public int read() {
        return values[index];
    }

    @Override
    public Counter tick() {
        index = ++index % values.length;
        return this;
    }
}
