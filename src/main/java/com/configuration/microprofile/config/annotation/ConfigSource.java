package com.configuration.microprofile.config.annotation;

import java.lang.annotation.*;
import java.net.URL;

import static org.eclipse.microprofile.config.spi.ConfigSource.DEFAULT_ORDINAL;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ConfigSources.class)
public @interface ConfigSource {

    /**
     * The name of {@link org.eclipse.microprofile.config.spi.ConfigSource}
     *
     * @see {@link org.eclipse.microprofile.config.spi.ConfigSource#getName()}
     */
    String name() default "";

    /**
     * The ordinal of {@link org.eclipse.microprofile.config.spi.ConfigSource}
     *
     * @see {@link org.eclipse.microprofile.config.spi.ConfigSource#getOrdinal()}
     */
    int ordinal() default DEFAULT_ORDINAL;

    /**
     * The source resource of configuration
     *
     * @return the string representing of {@link URL}
     */
    String resource();

    /**
     * The encoding of resource content
     *
     * @return default "UTF-8"
     */
    String encoding() default "UTF-8";

    /**
     * The factory to create {@link org.eclipse.microprofile.config.spi.ConfigSource}
     *
     * @return the factory class
     * @see DefaultConfigSourceFactory
     */
    Class<? extends ConfigSourceFactory> factory() default ConfigSourceFactory.class;
}
