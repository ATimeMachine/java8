package com.chen.java8.example.annotation;

import java.lang.annotation.*;

/**
 * 水果供应者注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
    int id() default -1;

    String name() default "";

    String address() default "";
}
