package guickito.core;

import java.util.function.Supplier;

import com.google.inject.Injector;
import com.google.inject.InjectorFactory;
import com.google.inject.InjectorFactoryImpl;
import com.google.inject.Stage;

class InjectionRuleSupport {
    private static final InjectorFactory injectorFactory = new InjectorFactoryImpl();

    private Injector injector;
    private boolean injected;
    private Supplier<Injector> injectorProvider;

    InjectionRuleSupport(ModulesDescriptor application) {
        this(() -> createInjector(application));
    }

    private InjectionRuleSupport(Supplier<Injector> injectorProvider) {
        this.injectorProvider = injectorProvider;
    }

    private static Injector createInjector(ModulesDescriptor application) {
        return injectorFactory.create(Stage.DEVELOPMENT, application.resolveModules());
    }

    public InjectionRule rule() {
        injected = true;
        return new InjectionRule(new AnnotatedFieldsGuiceInjecting(injector));
    }

    public void initialize() {
        injector = injectorProvider.get();
    }

    public void release() {
        if (!injected) {
            throw new RuntimeException("Not injected.");
        }
    }

}