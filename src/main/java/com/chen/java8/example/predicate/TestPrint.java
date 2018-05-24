package com.chen.java8.example.predicate;

import com.chen.java8.example.apple.Apple;

import java.util.Arrays;
import java.util.List;

/**
 * FileName: TestPrint
 * Author:   SunEee
 * Date:     2018/5/24 14:37
 * Description:
 */
public class TestPrint {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple("red", 100), new Apple("green", 50));
        AppleWeightPrint appleWeightPrint = new AppleWeightPrint();
        PrettyPrintApple.prettyPrintApple(apples,appleWeightPrint);
    }
}
