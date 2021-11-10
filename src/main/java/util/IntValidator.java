package util;

import java.util.function.ToIntFunction;

public class IntValidator<T> extends Validator<T,Integer> {

    protected IntValidator(ToIntFunction<T> keyExtractor) {
        super(keyExtractor::applyAsInt);
    }

    protected IntValidator(ToIntFunction<T> keyExtractor, IValidator<T> validator) {
        super(keyExtractor::applyAsInt, validator);
    }

    public IntValidator<T> inRange(int minInclusive, int maxExclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxExclusive);
        return this;
    }

    public IntValidator<T> inRangeExclusive(int minExclusive, int maxExclusive){
        super.thenVaildating(n -> minExclusive <= n && n < maxExclusive);
        return this;
    }

    public IntValidator<T> inRangeInclusive(int minInclusive, int maxInclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxInclusive);
        return this;
    }

    public IntValidator<T> greaterThan(double value){
        super.thenVaildating(n -> n > value);
        return this;
    }

    public IntValidator<T> lessThan(double value){
        super.thenVaildating(n -> n < value);
        return this;
    }

    public IntValidator<T> equal(double value){
        super.thenVaildating(n -> n == value);
        return this;
    }

    public IntValidator<T> greaterOrEqual(double value){
        super.thenVaildating(n -> n >= value);
        return this;
    }

    public IntValidator<T> lessOrEqual(double value) {
        super.thenVaildating(n -> n <= value);
        return this;
    }

        public IntValidator<T> positiv(){
        return this.greaterThan(0);
    }

    public IntValidator<T> negativ(){
        return this.lessThan(0);
    }
}
