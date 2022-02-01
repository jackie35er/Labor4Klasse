package Labor7.decorator;

public class MultipleCounter extends CounterDecorator {

    private int multiple;
    private int tickCallsSinceLastSuperTick = 0;

    public MultipleCounter(Counter counter, int multiple) {
        super(counter);
        if(multiple <= 0)
            throw new IllegalArgumentException();

        this.multiple = multiple;
    }


    @Override
    public Counter tick() {
        if(++tickCallsSinceLastSuperTick % multiple != 0)
            return this;
        super.tick();
        return this;
    }
}
