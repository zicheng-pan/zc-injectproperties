package com.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultConfig implements Config {
    private final ConfigSources configSources;

    //    private final Converters converters;

    public DefaultConfig(ConfigSources configSources) {
        this.configSources = configSources;
    }


    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        Iterator<ConfigSource> iterator = configSources.iterator();
        while (iterator.hasNext()) {
            ConfigSource configSource = iterator.next();
            String value = configSource.getValue(propertyName);
            if (value != null)
                return (T) value;
        }
        return null;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> List<T> getValues(String propertyName, Class<T> propertyType) {
        return Config.super.getValues(propertyName, propertyType);
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.empty();
    }

    @Override
    public <T> Optional<List<T>> getOptionalValues(String propertyName, Class<T> propertyType) {
        return Config.super.getOptionalValues(propertyName, propertyType);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return new Iterable<ConfigSource>() {
            @Override
            public Iterator<ConfigSource> iterator() {
                return configSources.iterator();
            }
        };
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
