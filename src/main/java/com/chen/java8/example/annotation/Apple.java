package com.chen.java8.example.annotation;

/**
 * FileName: Apple
 * Author:   SunEee
 * Date:     2018/7/2 18:12
 * Description: 注解使用
 */
public class Apple {
    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id = 100, name = "红富士",address = "北京红富士大厦")
    private String appleProvider;

}
