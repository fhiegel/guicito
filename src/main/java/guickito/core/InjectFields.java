package guickito.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import guickito.core.annotation.AnnotatedFields;

class InjectFields {

    private final Iterable<Field> fields;
    private final AnnotatedFields fieldsProcess;

    public InjectFields(Iterable<Field> fields, AnnotatedFields values) {
        this.fields = fields;
        this.fieldsProcess = values;
    }

    public void proceed() {
        process(fields);
    }

    private void process(Iterable<Field> fields) {
        for (Field field : fields) {
            processField(field);
        }
    }

    private void processField(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            fieldsProcess.annotatedBy(annotation).accept(field);
        }
    }

}