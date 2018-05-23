package com.chen.java8.example.face;

/**
 * FileName: Fruits
 * Author:   SunEee
 * Date:     2018/5/23 10:10
 * Description: 水果
 */
public interface Fruits {

    default void print(){
        System.out.println("Fruits");
    }
}
