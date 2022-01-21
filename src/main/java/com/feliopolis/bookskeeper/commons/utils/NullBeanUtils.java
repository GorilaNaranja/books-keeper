package com.feliopolis.bookskeeper.commons.utils;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullBeanUtils extends BeanUtilsBean {

    // Singleton pattern thread safe
    private static volatile NullBeanUtils instance;

    public static NullBeanUtils getInstance() {
        if (instance == null) {
            synchronized (NullBeanUtils.class) {
                if (instance == null) {
                    instance = new NullBeanUtils();
                }
            }
        }
        return instance;
    }

    @Override
    public void copyProperty(Object dest, String name, Object value) throws InvocationTargetException, IllegalAccessException {
        if (value == null) return;
        super.copyProperty(dest, name, value);
    }
}
