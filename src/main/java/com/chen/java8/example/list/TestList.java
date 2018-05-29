package com.chen.java8.example.list;

import java.util.Arrays;
import java.util.List;

/**
 * FileName: TestOrange
 * Author:   SunEee
 * Date:     2018/5/23 10:19
 * Description: list的泛型
 */
public class TestList {
    public static void main(String[] args) {
        TestList testOrange = new TestList();
        List<B> bList = Arrays.asList(new B(),new B(),new B());
        List<C> cList = Arrays.asList(new C(),new C(),new C());

        testOrange.test(bList);
        testOrange.test(cList);

    }

    private <T extends A>  void test(List<T> list){
        for (A t : list) {
            System.out.println(t.getA());
        }
    }

}
