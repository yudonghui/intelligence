package com.ydh.intelligence.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Date:2023/3/14
 * Time:9:59
 * author:ydh
 */
public class AnnotationAnalysis {
    public static void bind(final Object object) {
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                ApiToken apiTokenAnnotation = field.getAnnotation(ApiToken.class);
                if (apiTokenAnnotation!=null){
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    try {
                        /*Constructor<?> constructor = type.getConstructor();
                        field.set(object, constructor.newInstance());*/
                        Constructor<?> constructor = type.getConstructor(String.class);
                        field.set(object, constructor.newInstance("阿斯顿法师法第三代"));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }
}
