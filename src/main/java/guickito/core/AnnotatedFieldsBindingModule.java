package guickito.core;

import static org.mockito.Mockito.spy;

import java.lang.reflect.Field;

import guickito.core.annotation.AnnotatedFields;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import com.google.inject.AbstractModule;

class AnnotatedFieldsBindingModule extends AbstractModule {

    private Class<?> testClass;

    AnnotatedFieldsBindingModule(Class<?> testClass) {
        this.testClass = testClass;
    }

    private AnnotatedFields fields() {
        return new AnnotatedFields();
    }

    @Override
    public void configure() {
        AnnotatedFields fieldsProcessor = fields()
                .annotatedBy(Mock.class)
                .areProvidedByMockito(new MockAnnotationProcessor())
                .then(this::bindField)

                .annotatedBy(Spy.class)
                .areProvidedBy(field -> spy(field.getType()))
                .then(this::bindField);

        fieldsProcessor.applyOn(testClass.getDeclaredFields());
    }

    private void bindField(Field field, Object fieldValue) {
        bindField(field.getType(), fieldValue);
    }

    @SuppressWarnings("unchecked")
    private <F> void bindField(Class<F> declaringClass, Object fieldValue) {
        bind(declaringClass).toInstance((F) fieldValue);
    }

}