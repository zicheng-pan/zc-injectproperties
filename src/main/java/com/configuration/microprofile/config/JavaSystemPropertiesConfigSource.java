package com.configuration.microprofile.config;

import java.util.Map;

public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {

    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties", 400);
    }

//    public JavaSystemPropertiesConfigSource(String name, int ordinal) {
//        super(name, ordinal);
//    }

    @Override
    protected void prepareConfigData(Map configData) {
        configData.putAll(System.getProperties());
    }
}
