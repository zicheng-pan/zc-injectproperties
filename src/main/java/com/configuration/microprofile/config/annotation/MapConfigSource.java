package com.configuration.microprofile.config.annotation;

import com.configuration.microprofile.config.MapBasedConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Properties;

public class MapConfigSource extends MapBasedConfigSource {
    private final Properties properties;

    public MapConfigSource(String actualName, int ordinal, Properties properties) {
        super(actualName, ordinal);
        this.properties = properties;
    }

    @Override
    protected void prepareConfigData(Map configData) {
        configData.putAll(this.properties);
    }
}
