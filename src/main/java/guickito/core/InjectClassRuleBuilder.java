package guickito.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public class InjectClassRuleBuilder {

    private Module module = Modules.EMPTY_MODULE;
    private Collection<RuntimeDependency> runtimeDependencies = new ArrayList<>();
    private Collection<Module> overloadingModules = new ArrayList<>();

    InjectClassRuleBuilder() {
    }
    
    public InjectClassRuleBuilder add(Module module) {
        this.module = Modules.combine(this.module, module);
        return this;
    }

    public InjectClassRuleBuilder add(Module... modules) {
        return add(Modules.combine(modules));
    }

    public InjectClassRuleBuilder override(Module... overloadingModules) {
        Collections.addAll(this.overloadingModules, overloadingModules);
        return this;
    }

    public InjectClassRuleBuilder withDependency(RuntimeDependency runtimeDependency) {
        runtimeDependencies.add(runtimeDependency);
        return this;
    }

    public InjectClassRule build() {
        return new InjectClassRule(this);
    }

    ModulesDescriptor asModules() {
        return new ModulesDescriptor(module, runtimeDependencies, overloadingModules);
    }

}
