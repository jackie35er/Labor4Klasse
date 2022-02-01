package Labor7.decorator;

public class BaseCounter implements Counter {

    private final int base;
    private int countBase10;

    public BaseCounter(int base) {
        if(base >= 10 || base < 2)
            throw new IllegalArgumentException("Base must be in range 2-10");
        this.base = base;
        countBase10 = 0;
    }

    public int read() {
        return convertToBase(countBase10);
    }

    private int convertToBase(int n) {
        int sum = 0;
        for(int i = 0; n > 0; i++){
            sum += n % base * Math.pow(10,i);
            n = n / base;
        }
        return sum;
    }

    public Counter tick() {
        countBase10++;
        return this;
    }
}
