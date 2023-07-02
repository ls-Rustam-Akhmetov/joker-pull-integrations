package ru.example.instruments.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class NotNullBeanUtils {

    private NotNullBeanUtils() {
    }

    /**
     * Copy not null properties Almost like org.springframework.beans.BeanUtils.copyProperties
     */
    public static void copyNotNullProperties(Object source, Object target,
                                             String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ArrayUtils
                .addAll(ignoreProperties, getNullPropertyNames(source)));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
