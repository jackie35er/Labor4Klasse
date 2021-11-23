package util.validator.interfaces;


import util.validator.implementaions.DefaultValidator;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Validator<T,R> {

    /**
     * Returns a default instance of Validator
     * @param keyExtractor the keyExtractor to get the key
     * @param <T> The type of object passed to the extractor
     * @param <R> The type of object returned by the extractor
     * @return a default instance of Validator
     */
    static <T,R> Validator<T,R> getInstance(Function<T,R> keyExtractor){
        return new DefaultValidator<>(keyExtractor);
    }

    /**
     * adds a condition to the validator
     * @param isValid the condition
     * @return a Validator object with the added condition
     */
    Validator<T,R> validating(Predicate<R> isValid);

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     * @see util.validator.interfaces.Validator#validating(Predicate)
     * @return a Validator where the next Condition gets inversed
     */
    Validator<T,R> not();

    /**
     * After building the Validator this method returns if the given object matches the given conditions
     * @param t the given object
     * @return true if the object matches the conditions
     */
    boolean validate(T t);

    /**After building the Validator this method returns, if the given objects, in the collection, match the given conditions
     * @see util.validator.interfaces.Validator#validate(Object)
     * @param tCollection a collection of T
     * @return true if all Objects match
     */
    boolean allValid(Collection<T> tCollection);

    /**
     * Returns the <code>t</code> if <code>t</code> matches the validators conditions. Otherwise it returns <code>other</code>
     * @param t the value to validate
     * @param other the value to return if <code>t</code> is not valid
     * @return <code>t</code> if validator matches, otherwise <code>other</code>
     */
    T validOr(T t,T other);

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     * @param t the value to validate
     * @return the value
     * @throws Exception if the value is invalid
     */
    T validOrThrow(T t) throws Exception;

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     * @param t the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @param <X> the type of exception
     * @return the value
     * @throws X if the value is invalid
     */
    <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X;

    /**
     * returns a new Validator object, with a new key, but still kepping the old conditions
     * @param keyExtractor the key extractor to get the key
     * @param <U> the new key Type
     * @return a Validator instance with the new key
     */
    <U> Validator<T,U> key(Function<T,U> keyExtractor);
}
