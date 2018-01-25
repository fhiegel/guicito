package com.google.inject;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class InjectorFactoryImpl implements InjectorFactory {

    @Override
    public Injector create(Stage stage, List<Module> modules) {
        return Guice.createInjector(stage, modules);
    }

}
