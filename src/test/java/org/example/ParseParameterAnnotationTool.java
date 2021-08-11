package org.example;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.Before;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.stream.Stream;

import static org.eclipse.microprofile.config.inject.ConfigProperty.UNCONFIGURED_VALUE;

public class ParseParameterAnnotationTool {

    @Before
    public static void parseInjectAnnotation(Config config, Object bean,String fieldName) throws Throwable {
        Class beanClass = bean.getClass();
        Field[] configPropertyFields = findConfigPropertyFields(beanClass);
        Field relatedField = Stream.of(configPropertyFields).filter(field -> fieldName.equals(field.getName())).findFirst()
                .orElse(null);

        // 获取注解，通过注解中的内容来找到相应的配置数据
        // 然后通过反射，setProperty到指定的对象中，因为这里测试代码，只要只当到当前对象就可以了
        ConfigProperty configProperty = relatedField.getAnnotation(ConfigProperty.class);
        // 获取Config注解中的配置数据
        String defaultValue = configProperty.defaultValue();
        String propertyName = configProperty.name();
        parseandInjectConfig(config, relatedField, propertyName, defaultValue, bean);
    }


    private static Field[] findConfigPropertyFields(Class<?> beanClass) {
        Field[] declaredFields = beanClass.getDeclaredFields();
        // filter condition:
        // 1. non-static 因为和bean的生命周期相关，所以不能为static的
        // 2. non-final  因为有可能当前类为动态代理生成的对象，所以不能是final的属性
        // 3. annotated @javax.inject.Inject   需要被这两个注解发标识的属性
        // 4. annotated @org.eclipse.microprofile.config.inject.ConfigProperty

        return Stream.of(declaredFields).filter(field -> {
            int mods = field.getModifiers();
            return !Modifier.isStatic(mods) && !Modifier.isFinal(mods);
        }).filter(field -> {
            return field.isAnnotationPresent(Inject.class) && field.isAnnotationPresent(ConfigProperty.class);
        }).toArray(Field[]::new);
    }

    private static void parseandInjectConfig(Config config, Field field, String propertyName, String defaultValue, Object bean) throws IllegalAccessException {
        if (field.getGenericType() instanceof ParameterizedType) {

            //TODO convert Type Method 提供一个获取属性的类型，通过属性类型来convert的方法
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            // 通过config全局的配置对象来获取配置
            String somePort = config.getValue(propertyName, String.class);
            Class fieldType = (Class) parameterizedType.getRawType();
            // convertedType == java.lang.Integer.class
            Class convertedType = (Class) parameterizedType.getActualTypeArguments()[0];
            Optional propertyValue = null;
            if (Optional.class.equals(fieldType)) {
                if (somePort == null && UNCONFIGURED_VALUE.equals(defaultValue)) {
                    propertyValue = Optional.empty();
                }
                if (somePort == null) {
                    somePort = defaultValue;
                }
                propertyValue = Optional.of(somePort);
            }

            // 最后通过反射setProperty到对象中
            if (propertyValue.isPresent()) {
                field.setAccessible(true);
                field.set(bean, propertyValue);
            }
        }
    }


}
