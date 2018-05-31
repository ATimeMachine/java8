package com.chen.java8.example.paralleImportant;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * FileName: TestFork
 * Author:   SunEee
 * Date:     2018/5/30 9:40
 * Description: 测试分支/合并
 */
public class TestFork {
    public static void main(String[] args) {
        System.out.println(TestParallel.sumAll(TestFork::forkJoinSum,10_000_000L));
    }

    public static long forkJoinSum(Long n) {
        long[] longs = LongStream.rangeClosed(0, n).toArray();
        ForkJoinSum task = new ForkJoinSum(longs);
        return new ForkJoinPool().invoke(task);
    }
}
