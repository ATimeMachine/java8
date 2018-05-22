package com.chen.java8.example.apple;

import java.util.Arrays;
import java.util.List;

/**
 * FileName: Test
 * Author:   SunEee
 * Date:     2018/5/22 17:11
 * Description: Test
 */
public class TestApple {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple("red", 120), new Apple("green", 150));
        //List<Apple> appleList = AppleFilter.filterApples(apples, Apple::isGreenApple);
        List<Apple> appleList = AppleFilter.filterApples(apples, (Apple a) -> "green".equals(a.getColor()));
        appleList.forEach(apple -> System.out.println(apple.toString()));
    }
}
