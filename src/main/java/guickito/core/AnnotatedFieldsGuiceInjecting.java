package guickito.core;

import java.lang.reflect.Field;
import java.util.function.Function;

import javax.inject.Inject;

import guickito.core.annotation.AnnotatedFields;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.configuration.CaptorAnnotationProcessor;

import com.google.inject.Injector;

class AnnotatedFieldsGuiceInjecting {

    private Injector guiceInjector;

    AnnotatedFieldsGuiceInjecting(Injector guiceInjector) {
        this.guiceInjector = guiceInjector;
    }

    public Function<Object, AnnotatedFields> factory() {
        return testInstance -> fields()
                .annotatedBy(Mock.class)
                .areProvidedBy((annotation, field) -> getResetMock(field, guiceInjector))
                .injectOn(testInstance)

                .annotatedBy(Spy.class)
                .areProvidedBy((annotation, field) -> getResetMock(field, guiceInjector))
                .injectOn(testInstance)

                .annotatedBy(Captor.class)
                .areProvidedByMockito(new CaptorAnnotationProcessor())
                .injectOn(testInstance)

                .annotatedBy(Inject.class)
                .areProvidedBy((annotation, field) -> getFieldValue(field, guiceInjector))
                .injectOn(testInstance)

                .annotatedBy(com.google.inject.Inject.class)
                .areProvidedBy((annotation, field) -> getFieldValue(field, guiceInjector))
                .injectOn(testInstance)

                .annotatedBy(InjectMocks.class)
                .areProvidedBy((annotation, field) -> getFieldValue(field, guiceInjector))
                .injectOn(testInstance);
    }

    private AnnotatedFields fields() {
        return new AnnotatedFields();
    }

    private Object getFieldValue(Field field, Injector injector) {
        return injector.getInstance(field.getType());
    }

    private Object getResetMock(Field field, Injector injector) {
        Object fieldValue = getFieldValue(field, injector);
        Mockito.reset(fieldValue);
        return fieldValue;
    }
}