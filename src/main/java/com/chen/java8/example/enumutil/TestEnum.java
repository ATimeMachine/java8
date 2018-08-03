package com.chen.java8.example.enumutil;

/**
 * FileName: TestEnum
 * Author:   SunEee
 * Date:     2018/8/3 18:31
 * Description: 测试枚举
 */
public class TestEnum {
    public static void main(String[] args) {
        SexEnum w = EnumUtil.getEnumObject(SexEnum.class, e -> e.getCode().equals("M"));
        System.out.println(w.getDesc());
        SexEnum sexEnum = EnumUtil.getEnumObject(SexEnum.class, e -> e.getCode().equals("M"));
        System.out.println(sexEnum.getDesc());

    }
}
