package util;

import java.util.function.ToDoubleFunction;

public class DoubleValidator<T> extends Validator<T,Double> {

    protected DoubleValidator(ToDoubleFunction<T> keyExtractor) {
        super(keyExtractor::applyAsDouble);
    }

    protected DoubleValidator(ToDoubleFunction<T> keyExtractor, IValidator<T> validator) {
        super(keyExtractor::applyAsDouble, validator);
    }

    public DoubleValidator<T> inRange(double minInclusive, double maxExclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxExclusive);
        return this;
    }

    public DoubleValidator<T> inRangeExclusive(double minExclusive, double maxExclusive){
        super.thenVaildating(n -> minExclusive <= n && n < maxExclusive);
        return this;
    }

    public DoubleValidator<T> inRangeInclusive(double minInclusive, double maxInclusive){
        super.thenVaildating(n -> minInclusive <= n && n < maxInclusive);
        return this;
    }

    public DoubleValidator<T> greaterThan(double value){
        super.thenVaildating(n -> n > value);
        return this;
    }

    public DoubleValidator<T> lessThan(double value){
        super.thenVaildating(n -> n < value);
        return this;
    }

    public DoubleValidator<T> equal(double value){
        super.thenVaildating(n -> n == value);
        return this;
    }

    public DoubleValidator<T> greaterOrEqual(double value){
        super.thenVaildating(n -> n >= value);
        return this;
    }

    public DoubleValidator<T> lessOrEqual(double value){
        super.thenVaildating(n -> n <= value);
        return this;
    }

    public DoubleValidator<T> positiv(){
        return this.greaterThan(0);
    }

    public DoubleValidator<T> negativ(){
        return this.lessThan(0);
    }

}
