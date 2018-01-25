package guickito.core;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class InjectClassRule implements TestRule {

    private final InjectClassRuleBuilder injectionBuilder;
    private InjectionRuleSupport ruleSupport;

    InjectClassRule(InjectClassRuleBuilder applicationDescriptor) {
        this.injectionBuilder = applicationDescriptor;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        ruleSupport = createHandler(description.getTestClass());
        return new InjectClassStatement(base, ruleSupport);
    }

    private InjectionRuleSupport createHandler(Class<?> testClass) {
        ModulesDescriptor application = getApplication(testClass);
        return new InjectionRuleSupport(application);
    }

    private ModulesDescriptor getApplication(Class<?> testClass) {
        return injectionBuilder.override(new AnnotatedFieldsBindingModule(testClass)).asModules();
    }

    public InjectionRule inject() {
        return ruleSupport.rule();
    }
}
