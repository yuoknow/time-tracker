package yu.know.timetracker.core;

import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@NonNull
public sealed interface Result<T> permits Result.Failure, Result.Success {

    default <R, E> Result<E> merge(Result<R> another, BiFunction<T, R, E> mapping) {
        return switch (this) {
            case Failure failure -> failure;
            case Success(T obj) -> another.mapResult(r -> mapping.apply(obj, r));
        };
    }

    default <E> Result<E> flatMap(Function<T, Result<E>> another) {
        return switch (this) {
            case Failure failure -> failure;
            case Success(T obj) -> another.apply(obj);
        };
    }

    default <E> Result<E> mapResult(Function<T, E> another) {
        return switch (this) {
            case Failure failure -> failure;
            case Success(T obj) -> Result.ok(another.apply(obj));
        };
    }

    default <E> E mapOrThrow(Function<T, E> onSuccess, Function<String, RuntimeException> onFailure) {
        return switch (this) {
            case Failure(Set<ErrorMessage> errors) -> throw onFailure.apply(
                    errors.stream()
                            .map(ErrorMessage::message)
                            .collect(Collectors.joining("; ")));
            case Success(T obj) -> onSuccess.apply(obj);
        };
    }

    record Success<T>(T obj) implements Result<T> {
    }

    record Failure<T>(Set<ErrorMessage> errors) implements Result<T> {
        public String errorText() {
            return errors.stream().map(ErrorMessage::message).collect(Collectors.joining(";"));
        }
    }

    static <T> Success<T> ok(T obj) {
        return new Success<>(obj);
    }

    static <T> Result<T> fromOptional(Optional<T> obj, String errorText) {
        if (obj.isPresent()) {
            return new Success<>(obj.get());
        } else {
            return new Failure<>(Set.of(new ErrorMessage(errorText)));
        }
    }

    static <T> Failure<T> failure(String errorMessage) {
        return new Failure<>(Set.of(new ErrorMessage(errorMessage)));
    }

    static <T> Failure<T> failure(Set<ErrorMessage> errors) {
        return new Failure<>(errors);
    }

    record ErrorMessage(String message) {
    }
}
