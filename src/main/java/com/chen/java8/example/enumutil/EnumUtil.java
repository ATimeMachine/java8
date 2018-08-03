package com.chen.java8.example.enumutil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * FileName: EnumConstant
 * Author:   SunEee
 * Date:     2018/8/3 14:17
 * Description: 枚举常量
 */
public class EnumUtil {
    private static Map<Class<?>, Object> enumMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getEnumObject(Class<T> clazz, Predicate<T> predicate) {
        if (clazz == null) {
            //logger.error();
            return null;
        }
        if (!clazz.isEnum()) { //不是枚举
            //logger.error();
            return null;
        }
        Object obj = enumMap.get(clazz);
        T[] ts;
        if (null == obj) {
            ts = putEnumToMap(clazz);
        }else{
            ts = (T[]) obj;
        }
        Optional<T> optional = Arrays.stream(ts).filter(predicate).findAny();
        return optional.orElse(null);
    }
    @SuppressWarnings("unchecked")
    private static <T> T[] putEnumToMap(Class<T> clazz) {
        T[] ts = null;
        try {
            Method method = clazz.getMethod("values");//枚举的方法，获取所有枚举
            ts = (T[]) method.invoke(null);
            enumMap.put(clazz,ts);
        } catch (Exception e) {
            //logger.error();
        }
        return ts;
    }


}
