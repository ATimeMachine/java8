package com.chen.java8.example.predicate;

import com.chen.java8.example.apple.Apple;

/**
 * FileName: AppleWeightPrint
 * Author:   SunEee
 * Date:     2018/5/24 14:43
 * Description:
 */
public class AppleWeightPrint implements AppleFormatter {
    @Override
    public String accept(Apple apple) {
        return apple.getWeight().toString();
    }
}
