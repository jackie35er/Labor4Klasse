package Labor7.decorator;

public class SkipCounter extends CounterDecorator {

    private int skip;


    public SkipCounter(Counter counter, int skip) {
        super(counter);
        if(skip < 0)
            throw new IllegalArgumentException();

        this.skip = skip;
    }

    @Override
    public int read() {

        return super.read();
    }

    @Override
    public Counter tick() {
        for(int i = 0; i < skip + 1; i++){
            super.tick();
        }
        return this;
    }
}
