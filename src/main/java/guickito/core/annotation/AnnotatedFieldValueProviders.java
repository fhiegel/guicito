package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class AnnotatedFieldValueProviders {

    private final static AnnotatedFieldValueProvider<?> DEFAULT_VALUE_PROVIDER = (annotation, field) -> null;

    private final Map<Class<? extends Annotation>, AnnotatedFieldValueProvider<?>> providersByAnnotation = new HashMap<Class<? extends Annotation>, AnnotatedFieldValueProvider<?>>();

    <A extends Annotation> AnnotatedFieldValueProvider<?> register(Class<A> annotationClass, AnnotatedFieldValueProvider<A> processor) {
        return providersByAnnotation.put(annotationClass, processor);
    }

    public <A extends Annotation> FieldValueProvider getProvider(A annotation) {
        return field -> getValue(annotation, field);
    }

    private <A extends Annotation> Object getValue(A annotation, Field field) {
        return findAnnotatedProvider(annotation).get(annotation, field);
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> AnnotatedFieldValueProvider<A> findAnnotatedProvider(A annotation) {
        if (providersByAnnotation.containsKey(annotation.annotationType())) {
            return (AnnotatedFieldValueProvider<A>) providersByAnnotation.get(annotation.annotationType());
        }
        return defaultProvider();
    }

    @SuppressWarnings("unchecked")
    private static final <A extends Annotation> AnnotatedFieldValueProvider<A> defaultProvider() {
        return (AnnotatedFieldValueProvider<A>) DEFAULT_VALUE_PROVIDER;
    }

}
