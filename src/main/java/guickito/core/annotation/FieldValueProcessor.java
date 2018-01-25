package guickito.core.annotation;

import java.lang.reflect.Field;
import java.util.Objects;

@FunctionalInterface
public interface FieldValueProcessor {
    void process(Field field, Object value);

    default FieldValueProcessor andThen(FieldValueProcessor after) {
        Objects.requireNonNull(after);
        return (f, v) -> {
            process(f, v);
            after.process(f, v);
        };
    }
}