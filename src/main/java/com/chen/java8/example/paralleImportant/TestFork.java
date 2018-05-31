package com.chen.java8.example.paralleImportant;

/**
 * FileName: TestFork
 * Author:   SunEee
 * Date:     2018/5/30 9:40
 * Description: 测试分支/合并
 */
public class TestFork {
    public static void main(String[] args) {
        System.out.println(TestParallel.sumAll(ForkJoinSum::forkJoinSum,10_000_000L));
    }


}
