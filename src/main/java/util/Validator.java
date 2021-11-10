package util;


import java.util.Collection;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;


public class Validator<T,R> {
    private final Function<T,R> keyExtractor;
    private IValidator<T> validator;

    protected Validator(Function<T,R> keyExtractor){
        this.keyExtractor = keyExtractor;
        this.validator = (n) -> true;
    }

    protected Validator(Function<T,R> keyExtractor, IValidator<T> validator){
        this.keyExtractor = keyExtractor;
        this.validator = validator;
    }

    public static <T,R> Validator<T,R> withKey(Function<T,R> keyExtractor){
        Objects.requireNonNull(keyExtractor);
        return new Validator<>(keyExtractor);
    }

    public static <T> IntValidator<T> withIntKey(ToIntFunction<T> keyExtractor){
        Objects.requireNonNull(keyExtractor);
        return new IntValidator<>(keyExtractor);
    }

    public static <T> DoubleValidator<T> withDoubleKey(ToDoubleFunction<T> keyExtractor){
        Objects.requireNonNull(keyExtractor);
        return new DoubleValidator<>(keyExtractor);
    }

    public static <T> LongValidator<T> withLongKey(ToLongFunction<T> keyExtractor){
        Objects.requireNonNull(keyExtractor);
        return new LongValidator<>(keyExtractor);
    }

    public static <T> StringValidator<T> withStringKey(ToStringFunction<T> keyExtractor){
        Objects.requireNonNull(keyExtractor);
        return new StringValidator<>(keyExtractor);
    }

    public Validator<T,R> thenVaildating(Predicate<R> keyValidator){
        this.validator = validator.thenValidating(keyExtractor,keyValidator);
        return this;
    }

    public <U> Validator<T,U> key(Function<T,U> keyExtractor){
        return new Validator<>(keyExtractor,this.validator);
    }

    public IntValidator<T> intKey(ToIntFunction<T> keyExtractor){
        return new IntValidator<>(keyExtractor);
    }

    public DoubleValidator<T> doubleKey(ToDoubleFunction<T> keyExtractor){
        return new DoubleValidator<>(keyExtractor);
    }

    public LongValidator<T> longKey(ToLongFunction<T> keyExtractor){
        return new LongValidator<>(keyExtractor);
    }

    public StringValidator<T> stringKey(ToStringFunction<T> keyExtractor){
        return new StringValidator<>(keyExtractor);
    }

    public boolean validate(T o){
        return this.validator.validate(o);
    }

    public boolean validateAll(Collection<T> tCollection){
        Objects.requireNonNull(tCollection);
        return tCollection.stream().allMatch(this::validate);
    }

    public Collection<T> filterInvalid(Collection<T> tCollection){
        Objects.requireNonNull(tCollection);
        return tCollection.stream().filter(this::validate).collect(Collectors.toList());
    }

    public T validOr(T toValidate, T other){
        return this.validator.validate(toValidate) ? toValidate : other;
    }

    public T validOrThrow(T toValidate){
        if(validator.validate(toValidate)){
            return toValidate;
        }
        throw new InvalidValueExecption("Value is invalid: " + toValidate);
    }

    public <X extends Throwable> boolean validOrThrow(T toValidate, Supplier<? extends X> throwableSupplier) throws X {
        if(validator.validate(toValidate)){
            return true;
        }
        throw throwableSupplier.get();
    }

    public static class InvalidValueExecption extends RuntimeException{
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
