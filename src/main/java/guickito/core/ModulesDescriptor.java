package guickito.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Module;
import com.google.inject.util.Modules;

class ModulesDescriptor {

    private final Module module;
    private final Collection<RuntimeDependency> runtimeDependencies;
    private final Collection<Module> overridingModules;

    ModulesDescriptor(Module module, Collection<RuntimeDependency> dependencies, Collection<Module> overridingModules) {
        this.module = module;
        this.runtimeDependencies = dependencies;
        this.overridingModules = overridingModules;
    }

    public List<Module> resolveModules() {
        Module mainModule = Modules.combine(module, resolveDependencies());
        Module completeModule = Modules.override(mainModule).with(overridingModules);
        return Collections.singletonList(completeModule);
    }

    private Module resolveDependencies() {
        return Modules.combine(runtimeDependencies.stream().map(RuntimeDependency::resolve).collect(Collectors.toList()));
    }

}
