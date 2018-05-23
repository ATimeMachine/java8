package com.chen.java8.example.face;

import java.util.Arrays;
import java.util.List;

/**
 * FileName: TestOrange
 * Author:   SunEee
 * Date:     2018/5/23 10:19
 * Description: test
 */
public class TestOrange {
    public static void main(String[] args) {
        TestOrange testOrange = new TestOrange();
        //new Orange().print();
        //new Eat().print();
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
