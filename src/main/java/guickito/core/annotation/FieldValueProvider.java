package guickito.core.annotation;

import java.lang.reflect.Field;
import java.util.function.Consumer;

@FunctionalInterface
public interface FieldValueProvider {
    Object get(Field field);

    default Consumer<Field> proceededBy(FieldValueProcessor process) {
        return field -> process.process(field, get(field));
    }
}