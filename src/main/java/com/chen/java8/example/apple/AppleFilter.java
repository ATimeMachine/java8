package com.chen.java8.example.apple;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AppleFilter
 * Author:   SunEee
 * Date:     2018/5/22 17:04
 * Description: filter
 */
public class AppleFilter {

    public interface Predicate<T> {
        boolean test(T t);
    }

    static List<Apple> filterApples(List<Apple> apples ,Predicate<Apple> predicate){
        List<Apple> appleList = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)){
                appleList.add(apple);
            }
        }
        return appleList;
    }
}
