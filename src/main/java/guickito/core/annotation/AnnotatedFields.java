package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;

public class AnnotatedFields {

    private final AnnotatedFieldValueProviders providers = new AnnotatedFieldValueProviders();
    private final AnnotatedFieldProcessors processors = new AnnotatedFieldProcessors();

    public <A extends Annotation> AnnotatedFieldValueProviderRegister<A, AnnotatedFieldProcessorRegister<AnnotatedFields>> annotatedBy(
            Class<A> annotationClass) {
        return new AnnotatedFieldValueProviderRegister<>(registerProvider(annotationClass), processorsStep(annotationClass));
    }

    private <A extends Annotation> Consumer<AnnotatedFieldValueProvider<A>> registerProvider(Class<A> annotationClass) {
        return provider -> providers.register(annotationClass, provider);
    }

    private <A extends Annotation> AnnotatedFieldProcessorRegister<AnnotatedFields> processorsStep(Class<A> annotationClass) {
        return new AnnotatedFieldProcessorRegister<>(registerProcessor(annotationClass), this);
    }

    private <A extends Annotation> Consumer<FieldValueProcessor> registerProcessor(Class<A> annotationClass) {
        return processor -> processors.register(annotationClass, processor);
    }

    public Consumer<Field> annotatedBy(Annotation annotation) {
        return getProvider(annotation).proceededBy(getProcessor(annotation));
    }

    private FieldValueProvider getProvider(Annotation annotation) {
        return providers.getProvider(annotation);
    }

    private FieldValueProcessor getProcessor(Annotation annotation) {
        return processors.getProcessor(annotation);
    }

    public void applyOn(Field[] fields) {
        applyOn(Arrays.asList(fields));
    }

    private void applyOn(Iterable<Field> fields) {
        for (Field field : fields) {
            applyOnField(field);
        }
    }

    private void applyOnField(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            annotatedBy(annotation).accept(field);
        }
    }

}