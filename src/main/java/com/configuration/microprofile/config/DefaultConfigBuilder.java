package com.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        this.configSources = new ConfigSources(classLoader);
    }

    @Override
    public ConfigBuilder addDefaultSources() {
        return null;
    }

    @Override
    public ConfigBuilder addDiscoveredSources() {
        return null;
    }

    @Override
    public ConfigBuilder addDiscoveredConverters() {
        return null;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        return null;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        return null;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        return null;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        return null;
    }

    @Override
    public Config build() {
        ConfigSources configSources = new ConfigSources(null);
        configSources.addDefaultSources();
        return new DefaultConfig(configSources);
    }
}
