package guickito.core.annotation;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.mockito.internal.util.reflection.FieldSetter;

public class AnnotatedFieldProcessorRegister<NB> {

    private final Consumer<FieldValueProcessor> register;
    private final NB nextBuilder;

    AnnotatedFieldProcessorRegister(Consumer<FieldValueProcessor> register, NB nextBuilder) {
        this.register = register;
        this.nextBuilder = nextBuilder;
    }

    public NB then(FieldValueProcessor first, FieldValueProcessor... processors) {
        FieldValueProcessor result = first;
        for (FieldValueProcessor processor : processors) {
            result = result.andThen(processor);
        }
        return then(result);
    }

    public NB then(FieldValueProcessor processor) {
        register.accept(processor);
        return nextBuilder;
    }

    public NB injectOn(Object instanceToInject) {
        return injectOn(() -> instanceToInject);
    }

    public NB injectOn(Supplier<Object> instanceToInject) {
        return then((field, value) -> FieldSetter.setField(instanceToInject.get(), field, value));
    }

    public static FieldValueProcessor ignoreNullValues(FieldValueProcessor processor) {
        return (field, value) -> Optional.ofNullable(value).ifPresent(v -> processor.process(field, v));
    }

}