package com.chen.java8.example.annotation;

import java.lang.annotation.*;

/**
 * FileName: FruitColor
 * Author:   SunEee
 * Date:     2018/7/2 17:51
 * Description: 水果颜色注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     */
    enum Color{BLUE,RED, GREEN}

    /**
     * 颜色属性
     * @return 颜色
     */
    Color fruitColor() default Color.GREEN;

}
