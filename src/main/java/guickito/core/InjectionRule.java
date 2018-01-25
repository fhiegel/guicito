package guickito.core;

import java.lang.reflect.Field;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

public class InjectionRule implements MethodRule {

    private final AnnotatedFieldsGuiceInjecting injecting;

    InjectionRule(AnnotatedFieldsGuiceInjecting injecting) {
        this.injecting = injecting;
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object testInstance) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                injectTestInstance(testInstance);
                base.evaluate();
                releaseTestInstance();
            }
        };
    }

    private void injectTestInstance(Object testInstance) {
        Field[] fields = testInstance.getClass().getDeclaredFields();
        injecting.factory().apply(testInstance).applyOn(fields);
    }

    private void releaseTestInstance() {
        Mockito.validateMockitoUsage();
    }

}