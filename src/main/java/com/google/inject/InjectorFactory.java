package com.google.inject;

import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

/**
 * Factory to create Guice Injector with supplied Stage and List of Modules.
 */
public interface InjectorFactory {

    Injector create(Stage stage, List<Module> modules);

}
