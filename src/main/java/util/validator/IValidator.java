package util.validator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface IValidator<T> {


    @Contract(pure = true)
    boolean validate(T o);

    @Contract(pure = true)
    default <R> @NotNull IValidator<T> thenValidating(
             Function<T, R> keyExtractor,
             Predicate<R> keyValidator
    ){
        return (o) -> this.validate(o) && validating(keyExtractor, keyValidator).validate(o);
    }

    @Contract(pure = true)
    static <T, R> @NotNull IValidator<T> validating(
            Function<T, R> keyExtractor,
            Predicate<R> keyValidator
    ){
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(keyValidator);
        return (o) -> keyValidator.test(keyExtractor.apply(o));
    }
}
