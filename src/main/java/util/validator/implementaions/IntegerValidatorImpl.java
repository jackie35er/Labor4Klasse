package util.validator.implementaions;

import util.validator.interfaces.Validator;
import util.validator.interfaces.numberValidator.IntegerValidator;
import util.validator.interfaces.numberValidator.NumberValidator;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class IntegerValidatorImpl<T> implements IntegerValidator<T> {

    /**
     * After building the Validator this method returns if the given object matches the given conditions
     *
     * @param t the given object
     * @return true if the object matches the conditions
     */
    @Override
    public boolean validate(T t) {
        return false;
    }

    /**
     * After building the Validator this method returns, if the given objects, in the collection, match the given conditions
     *
     * @param tCollection a collection of T
     * @return true if all Objects match
     * @see Validator#validate(Object)
     */
    @Override
    public boolean allValid(Collection<T> tCollection) {
        return false;
    }

    /**
     * Returns the <code>t</code> if <code>t</code> matches the validators conditions. Otherwise it returns <code>other</code>
     *
     * @param t     the value to validate
     * @param other the value to return if <code>t</code> is not valid
     * @return <code>t</code> if validator matches, otherwise <code>other</code>
     */
    @Override
    public T validOr(T t, T other) {
        return null;
    }

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     *
     * @param t the value to validate
     * @return the value
     * @throws Exception if the value is invalid
     */
    @Override
    public T validOrThrow(T t) throws Exception {
        return null;
    }

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     *
     * @param t                 the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @return the value
     * @throws X if the value is invalid
     */
    @Override
    public <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X {
        return null;
    }

    /**
     * returns a new Validator object, with a new key, but still kepping the old conditions
     *
     * @param keyExtractor the key extractor to get the key
     * @return a Validator instance with the new key
     */
    @Override
    public <U> Validator<T, U> key(Function<T, U> keyExtractor) {
        return null;
    }

    @Override
    public NumberValidator<T, Integer> validating(Predicate<Integer> isValid) {
        return null;
    }

    @Override
    public NumberValidator<T, Integer> not() {
        return null;
    }

    /**
     * checks if the Value is within normal range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min <= x < max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> inRange(Integer min, Integer max) {
        return null;
    }

    /**
     * checks if the Value is within exclusive range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min < x < max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> inRangeExclusive(Integer min, Integer max) {
        return null;
    }

    /**
     * checks if the Value is within inclusive range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min <= x <= max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> inRangeInclusive(Integer min, Integer max) {
        return null;
    }

    /**
     * checks if the extracted key is bigger than the value
     * this function is true , when x = the extracted key
     * <p>
     * value < x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> greaterThan(Integer value) {
        return null;
    }

    /**
     * checks if the extracted key is bigger or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value <= x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> greaterOrEqualThan(Integer value) {
        return null;
    }

    /**
     * checks if the extracted key is smaller than the value
     * this function is true , when x = the extracted key
     * <p>
     * value > x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> lesserThan(Integer value) {
        return null;
    }

    /**
     * checks if the extracted key is smaller or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value >= x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> lesserOrEqualThan(Integer value) {
        return null;
    }

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> positive() {
        return null;
    }

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> negative() {
        return null;
    }

    /**
     * checks if the extracted key is equal to the value
     * this function is true , when x = the extracted key
     * <p>
     * x == value
     *
     * @param value the value to be compared to the extraceted key
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> equal(Integer value) {
        return null;
    }

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    @Override
    public NumberValidator<T, Integer> isZero() {
        return null;
    }
}
