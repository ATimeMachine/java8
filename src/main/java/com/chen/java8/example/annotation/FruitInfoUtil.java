package com.chen.java8.example.annotation;

import java.lang.reflect.Field;

/**
 * FileName: FruitInfoUtil
 * Author:   SunEee
 * Date:     2018/7/2 17:57
 * Description: 注解处理器
 */
public class FruitInfoUtil {

    public static void getFruitInfo(Class<?> clazz) {
        String strFruitName = "水果名称：";
        String strFruitColor = "水果颜色：";
        String strFruitProvicer = "供应商信息：";

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                String value = fruitName.value();
                System.out.println(strFruitName + value);
            } else if (field.isAnnotationPresent(FruitColor.class)) {
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                FruitColor.Color color = fruitColor.fruitColor();
                System.out.println(strFruitColor + color);
            } else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                int id = fruitProvider.id();
                String name = fruitProvider.name();
                String address = fruitProvider.address();
                System.out.println(strFruitProvicer + id + "---" + name + "---" + address);
            }


        }
    }
}
