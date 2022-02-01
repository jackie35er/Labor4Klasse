package Labor7.decorator;

public class TypeCounter extends CounterDecorator{
    private int[] values;
    private int toCount;
    private int index = 0;

    public TypeCounter(Counter counter,int toCount,int... values) {
        super(counter);
        if(values == null || values.length == 0)
            throw new IllegalArgumentException("values cant be null");
        this.toCount = toCount;
        this.values = values;
    }

    @Override
    public Counter tick() {
        int value = values[index % values.length];
        index++;
        if(value == toCount)
            super.tick();

        return this;
    }
}
