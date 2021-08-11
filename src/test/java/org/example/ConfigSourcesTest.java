package org.example;

import com.configuration.microprofile.config.DefaultConfig;
import com.configuration.microprofile.config.annotation.ConfigSource;
import com.configuration.microprofile.config.annotation.ConfigSourceFactory;
import com.configuration.microprofile.config.annotation.ConfigSources;
import com.configuration.microprofile.config.annotation.DefaultConfigSourceFactory;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ConfigSources({
        @ConfigSource(ordinal = 200, resource = "classpath:/META-INF/test01.properties"),
        @ConfigSource(ordinal = 100, resource = "classpath:/META-INF/default.properties"),
        @ConfigSource(ordinal = 400, resource = "system:/systemproperties"),
        @ConfigSource(ordinal = 300, resource = "classpath:/META-INF/test02.properties")
})
public class ConfigSourcesTest {

    @Inject
    @ConfigProperty(name = "test.name", defaultValue = "defaultValue")
    private Optional<String> testName;

    @Before
    public void initConfigSourceFactory() throws Throwable {
        initSystemConfig();
        ConfigSources configSources = getClass().getAnnotation(ConfigSources.class);
        ConfigSource[] items = configSources.value();
        ArrayList<org.eclipse.microprofile.config.spi.ConfigSource> list = new ArrayList<>();
        for (ConfigSource configSource : items) {
            String name = configSource.name();
            int ordinal = configSource.ordinal();
            String encoding = configSource.encoding();
            String resource = configSource.resource();
            URL resourceURL = new URL(resource);
            Class<? extends ConfigSourceFactory> configSourceFactoryClass = configSource.factory();
            if (ConfigSourceFactory.class.equals(configSourceFactoryClass)) {
                configSourceFactoryClass = DefaultConfigSourceFactory.class;
            }

            ConfigSourceFactory configSourceFactory = configSourceFactoryClass.newInstance();
            org.eclipse.microprofile.config.spi.ConfigSource source =
                    configSourceFactory.createConfigSource(name, ordinal, resourceURL, encoding);
            list.add(source);
        }

        org.eclipse.microprofile.config.spi.ConfigSource[] sources = list.toArray(new org.eclipse.microprofile.config.spi.ConfigSource[list.size() - 1]);
        Config configByAnnotation = createConfigByAnnotation(sources);

        String value = configByAnnotation.getValue("test.name", String.class);
//        System.out.println(value);
        ParseParameterAnnotationTool.parseInjectAnnotation(configByAnnotation,this,"testName");
        System.out.println(this.testName);
    }





    private void initSystemConfig() {
        System.setProperty("test.name","systemName");
    }

    private Config createConfigByAnnotation(org.eclipse.microprofile.config.spi.ConfigSource... sources) {
        com.configuration.microprofile.config.ConfigSources configSources = new com.configuration.microprofile.config.ConfigSources(
                Thread.currentThread().getContextClassLoader()
        );
        configSources.addConfigSources(of(sources));
        return new DefaultConfig(configSources);
    }

    public static <T> T[] of(T... t) {
        return t;
    }

    @Test
    public void test() {

    }

}
