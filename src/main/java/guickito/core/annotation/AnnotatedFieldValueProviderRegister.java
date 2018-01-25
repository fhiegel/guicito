package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Consumer;

import org.mockito.internal.configuration.FieldAnnotationProcessor;

public class AnnotatedFieldValueProviderRegister<A extends Annotation, NB> {

    private final Consumer<AnnotatedFieldValueProvider<A>> register;
    private final NB nextBuilder;

    AnnotatedFieldValueProviderRegister(Consumer<AnnotatedFieldValueProvider<A>> register, NB nextBuilder) {
        this.register = register;
        this.nextBuilder = nextBuilder;
    }

    public NB areProvidedBy(FieldValueProvider valueProvider) {
        return areProvidedBy((A annotation, Field field) -> valueProvider.get(field));
    }

    public NB areProvidedBy(AnnotatedFieldValueProvider<A> fieldAnnotationProcessor) {
        register.accept(fieldAnnotationProcessor);
        return nextBuilder;
    }

    /**
     * @see {@link org.mockito.internal.configuration.FieldAnnotationProcessor}
     */
    public NB areProvidedByMockito(FieldAnnotationProcessor<A> fieldAnnotationProcessor) {
        return areProvidedBy(new MockitoProcessorAdapter<>(fieldAnnotationProcessor));
    }
}