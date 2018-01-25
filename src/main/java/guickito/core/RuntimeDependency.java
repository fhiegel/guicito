package guickito.core;

import com.google.inject.Module;

@FunctionalInterface
public interface RuntimeDependency {
    Module resolve();
}