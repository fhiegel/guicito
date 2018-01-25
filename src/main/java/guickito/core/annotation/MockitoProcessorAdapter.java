package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.mockito.internal.configuration.FieldAnnotationProcessor;

final class MockitoProcessorAdapter<A extends Annotation> implements AnnotatedFieldValueProvider<A> {

    private final FieldAnnotationProcessor<A> mockitoProcessor;

    MockitoProcessorAdapter(FieldAnnotationProcessor<A> fieldAnnotationProcessor) {
        this.mockitoProcessor = fieldAnnotationProcessor;
    }

    @Override
    public Object get(A annotation, Field field) {
        return mockitoProcessor.process(annotation, field);
    }
}