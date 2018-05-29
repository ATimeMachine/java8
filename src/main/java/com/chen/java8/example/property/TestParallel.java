package com.chen.java8.example.property;

import org.junit.Test;

import java.util.function.Function;

/**
 * FileName: TestParallel
 * Author:   SunEee
 * Date:     2018/5/29 11:04
 * Description: 合理使用平行和提高性能
 */
public class TestParallel {
    public long sumAll(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            Long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }

    @Test
    public void test1() {
       /* System.out.println(sumAll((i) -> {
            long sum = 0L;
            for (long j = 0L; j < i; j++) {
                sum += j;
            }
            System.out.println(sum);
            return sum;
        }, 10_000_000));
*/
        System.out.println(sumAll(ParallelStreams::sequentialSum, 10_000_000));
    }

    @Test
    public void test2() {
        System.out.println(sumAll(ParallelStreams::iterativeSum, 10_000_000));
    }

    @Test
    public void test3() {
        System.out.println(sumAll(ParallelStreams::parallelSum, 10_000_000));
    }

    @Test
    public void test4() {
        System.out.println(sumAll(ParallelStreams::sequentialSum2, 10_000_000));
    }

    @Test
    public void test5() {
        System.out.println(sumAll(ParallelStreams::paralleSum2, 10_000_000));
    }
}
