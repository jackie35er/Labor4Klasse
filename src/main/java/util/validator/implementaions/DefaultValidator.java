package util.validator.implementaions;

import util.validator.interfaces.Validator;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultValidator<T,R> implements Validator<T,R> {
    private final Function<T,R> keyExtractor;

    private Predicate<T> isValid;
    private boolean inverseNextStatement;

    public DefaultValidator(Function<T, R> keyExtractor){
        this.keyExtractor = keyExtractor;
        isValid = (o) -> true;
    }

    public DefaultValidator(Function<T,R> keyExtractor, Validator<T,?> validator){
        this.keyExtractor = keyExtractor;
        isValid = validator::validate;
    }

    @Override
    public Validator<T,R> validating(Predicate<R> isValid) {
        if(inverseNextStatement){
            inverseNextStatement = false;
            return validating((o) -> !isValid.test(o));
        }

        this.isValid = (o) -> this.validate(o) && isValid.test(keyExtractor.apply(o));
        return this;
    }

    @Override
    public Validator<T, R> not() {
        this.inverseNextStatement = true;
        return this;
    }

    @Override
    public boolean validate(T t) {
        return isValid.test(t);
    }

    @Override
    public boolean allValid(Collection<T> tCollection) {
        Objects.requireNonNull(tCollection);
        return tCollection.stream().allMatch(this::validate);
    }

    @Override
    public T validOr(T t, T other) {
        return this.validate(t) ? t : other;
    }

    @Override
    public T validOrThrow(T t) throws Exception {
        if(this.validate(t))
            return t;
        throw new InvalidValueExecption("Value is invalid: " + t);
    }

    @Override
    public <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X {
        if(this.validate(t))
            return t;
        throw throwableSupplier.get();
    }

    @Override
    public <U> Validator<T,U> key(Function<T,U> keyExtractor) {
        return new DefaultValidator<>(keyExtractor,this);
    }

    static class InvalidValueExecption extends RuntimeException{
        public InvalidValueExecption(){
            super();
        }

        public InvalidValueExecption(String message) {
            super(message);
        }

        public InvalidValueExecption(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidValueExecption(Throwable cause) {
            super(cause);
        }

        protected InvalidValueExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
