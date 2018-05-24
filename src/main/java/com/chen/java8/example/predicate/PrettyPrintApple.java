package com.chen.java8.example.predicate;

import com.chen.java8.example.apple.Apple;

import java.util.List;

/**
 * FileName: PrettyPrintApple
 * Author:   SunEee
 * Date:     2018/5/24 14:38
 * Description:
 */
public class PrettyPrintApple {
    public static void prettyPrintApple(List<Apple> apples,AppleFormatter appleFormatter) {
        for (Apple apple : apples) {
            String accept = appleFormatter.accept(apple);
            System.out.println(accept);
        }
    }
}
