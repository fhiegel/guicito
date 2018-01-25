package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

class AnnotatedFieldProcessors {

    private final static FieldValueProcessor NOOP_PROCESSOR = (annotation, field) -> {
    };

    private final Map<Class<? extends Annotation>, FieldValueProcessor> processorsByAnnotation = new HashMap<Class<? extends Annotation>, FieldValueProcessor>();

    public <A extends Annotation> FieldValueProcessor getProcessor(A annotation) {
        return findAnnotatedProcessor(annotation);
    }

    <A extends Annotation> FieldValueProcessor register(Class<A> annotationClass, FieldValueProcessor processor) {
        return processorsByAnnotation.put(annotationClass, processor);
    }
    

    private <A extends Annotation> FieldValueProcessor findAnnotatedProcessor(A annotation) {
        if (processorsByAnnotation.containsKey(annotation.annotationType())) {
            return processorsByAnnotation.get(annotation.annotationType());
        }
        return noopProcessor();
    }

    private static final <A extends Annotation> FieldValueProcessor noopProcessor() {
        return NOOP_PROCESSOR;
    }

}
