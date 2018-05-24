package com.chen.java8.example.predicate;

import com.chen.java8.example.apple.Apple;

/**
 * FileName: AppleColorPrint
 * Author:   SunEee
 * Date:     2018/5/24 14:42
 * Description:
 */
public class AppleColorPrint implements AppleFormatter {
    @Override
    public String accept(Apple apple) {
        return apple.getColor();
    }
}
