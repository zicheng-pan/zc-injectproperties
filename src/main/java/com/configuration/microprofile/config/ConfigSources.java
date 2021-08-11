package com.configuration.microprofile.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;
import java.util.function.Consumer;

import static java.util.stream.Stream.of;

public class ConfigSources implements Iterable<ConfigSource> {


    private List<ConfigSource> configSources = new SortedConfigSources(ConfigSourceOrdinalComparator.INSTANCE);

    private final ClassLoader classLoader;

    public ConfigSources(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void addDefaultSources() {
        addConfigSources(JavaSystemPropertiesConfigSource.class
//                OperationSystemEnvironmentVariablesConfigSource.class,
//                DefaultResourceConfigSources.class
        );
    }

    public void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
        addConfigSources(
                of(configSourceClasses)
                        .map(this::initConfgInstance)
                        .toArray(ConfigSource[]::new)
        );
    }

    public void addConfigSources(ConfigSource[] configSources) {
//        Stream.of(configSources).forEach(this.configSources::add);
        for (ConfigSource configSource : configSources)
            this.configSources.add(configSource);
    }


    private ConfigSource initConfgInstance(Class<? extends ConfigSource> aClass) {
        ConfigSource instance = null;
        try {
            instance = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        return instance;
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return configSources.iterator();
    }

    @Override
    public void forEach(Consumer<? super ConfigSource> action) {
        configSources.forEach(action);
    }

    @Override
    public Spliterator<ConfigSource> spliterator() {
        return Iterable.super.spliterator();
    }
}
