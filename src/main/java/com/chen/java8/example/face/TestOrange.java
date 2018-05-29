package com.chen.java8.example.face;

/**
 * FileName: TestOrange
 * Author:   SunEee
 * Date:     2018/5/23 10:19
 * Description: 因为接口可以默认方法，测试多继承
 */
public class TestOrange {
    public static void main(String[] args) {
        new Orange().print();
        new Eat().print();
    }
}
