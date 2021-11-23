package util.validator.interfaces.numberValidator;

import util.validator.interfaces.Validator;

import java.util.function.Predicate;

public interface NumberValidator<T,R extends Number> extends Validator<T,R> {

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a Validator object with the added condition
     */
    @Override
    NumberValidator<T, R> validating(Predicate<R> isValid);

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     *
     * @return a Validator where the next Condition gets inversed
     * @see Validator#validating(Predicate)
     */
    @Override
    NumberValidator<T, R> not();

    /**
     * checks if the Value is within normal range of the given values
     * this function is true , when x = the extracted key
     *
     * min <= x < max
     * 
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> inRange(R min, R max);

    /**
     * checks if the Value is within exclusive range of the given values
     * this function is true , when x = the extracted key
     *
     * min < x < max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> inRangeExclusive(R min, R max);

    /**
     * checks if the Value is within inclusive range of the given values
     * this function is true , when x = the extracted key
     *
     * min <= x <= max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> inRangeInclusive(R min, R max);

    /**
     * checks if the extracted key is bigger than the value
     * this function is true , when x = the extracted key
     *
     * value < x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> greaterThan(R value);

    /**
     * checks if the extracted key is bigger or equal than the value
     * this function is true , when x = the extracted key
     *
     * value <= x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> greaterOrEqualThan(R value);

    /**
     * checks if the extracted key is smaller than the value
     * this function is true , when x = the extracted key
     *
     * value > x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> lesserThan(R value);

    /**
     * checks if the extracted key is smaller or equal than the value
     * this function is true , when x = the extracted key
     *
     * value >= x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    NumberValidator<T,R> lesserOrEqualThan(R value);

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     *
     *  x > 0
     *
     * @return an instance with this condition added
     */
    NumberValidator<T,R> positive();

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     *
     *  x > 0
     *
     * @return an instance with this condition added
     */
    NumberValidator<T,R> negative();

    /**
     * checks if the extracted key is equal to the value
     * this function is true , when x = the extracted key
     *
     * x == value
     *
     * @param value the value to be compared to the extraceted key
     * @return an instance with this condition added
     */
    NumberValidator<T,R> equal(R value);

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    NumberValidator<T,R> isZero();

}
