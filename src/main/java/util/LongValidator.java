package util;

import java.util.function.ToLongFunction;

public class LongValidator<T> extends Validator<T,Long> {

    protected LongValidator(ToLongFunction<T> keyExtractor) {
        super(keyExtractor::applyAsLong);
    }

    protected LongValidator(ToLongFunction<T> keyExtractor, IValidator<T> validator) {
        super(keyExtractor::applyAsLong, validator);
    }

    public LongValidator<T> inRange(int minInclusive, int maxExclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxExclusive);
        return this;
    }

    public LongValidator<T> inRangeExclusive(int minExclusive, int maxExclusive){
        super.thenVaildating(n -> minExclusive <= n && n < maxExclusive);
        return this;
    }

    public LongValidator<T> inRangeInclusive(int minInclusive, int maxInclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxInclusive);
        return this;
    }

    public LongValidator<T> greaterThan(double value){
        super.thenVaildating(n -> n > value);
        return this;
    }

    public LongValidator<T> lessThan(double value){
        super.thenVaildating(n -> n < value);
        return this;
    }

    public LongValidator<T> equal(double value){
        super.thenVaildating(n -> n == value);
        return this;
    }

    public LongValidator<T> greaterOrEqual(double value){
        super.thenVaildating(n -> n >= value);
        return this;
    }

    public LongValidator<T> lessOrEqual(double value) {
        super.thenVaildating(n -> n <= value);
        return this;
    }

    public LongValidator<T> positiv(){
        return this.greaterThan(0);
    }

    public LongValidator<T> negativ(){
        return this.lessThan(0);
    }
}
