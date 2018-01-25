package guickito.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@FunctionalInterface
public interface AnnotatedFieldValueProvider<A extends Annotation> {
    Object get(A annotation, Field field);
}
