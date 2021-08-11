package com.configuration.microprofile.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MapBasedConfigSource implements ConfigSource {


    private final String name;

    private final int ordinal;

    private final Map<String,String> configData;

    public MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.configData = new HashMap<>();
    }

    @Override
    public Set<String> getPropertyNames() {
        return getConfigData().keySet();
    }

    @Override
    public String getValue(String propertyName) {
        if (propertyName.equals(CONFIG_ORDINAL))
            return String.valueOf(ordinal);
        return getConfigData().getOrDefault(propertyName,null);
    }

    @Override
    public String getName() {
        return name;
    }

    protected abstract void prepareConfigData(Map configData);

    protected Map<String, String> getConfigData() {
        try {
            if (configData.isEmpty()) {
                prepareConfigData(configData);
            }
        } catch (Throwable cause) {
            throw new IllegalStateException("准备配置数据发生错误", cause);
        }
        return configData;
    }
}
