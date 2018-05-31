package com.chen.java8.example.paralleImportant;

import java.util.function.Function;
import java.util.stream.LongStream;

/**
 * FileName: TestParallel
 * Author:   SunEee
 * Date:     2018/5/29 11:04
 * Description: 合理使用平行和提高性能
 */
public class TestParallel {
    public static void main(String[] args) {
      System.out.println(sumAll(new Function<Boolean, String>() { //原始方法
            @Override
            public String apply(Boolean b) {
                if (b) {
                    return "success";
                }
                return "false";
            }
        },true));

        TestFunction testFunction = new TestFunction();
        System.out.println(sumAll(testFunction,10000L)); //继承接口

       /* System.out.println(sumAll(MyParallelStreams::parallelSum, 10_000_000L));//lambda
        System.out.println(sumAll(MyParallelStreams::paralleSum2, 10_000_000L));

        System.out.println(sumAll(MyParallelStreams::iterativeSum, 10_000_000L));

        System.out.println(TestParallel.sumAll(MyParallelStreams::sequentialSum, 10_000_000L));
        System.out.println(sumAll(MyParallelStreams::sequentialSum2, 10_000_000L));*/

        //方法使用的参数和传进去的参数不一样，可以将值这样传入
        String s = "abcdefg";
        System.out.println(sumAll((a) ->new MyParallelStreams().paralleSum3(a,s) , 10_000_000L));

        System.out.println(sumAll((a) ->new MyParallelStreams().paralleSum4(a) , 10_000_000L));




    }

   /* public static long sumAll(Function<Long, Long> adder, long n) {
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
    }*/

   //升级版
    public static <T, R> long sumAll(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + result);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static class TestFunction implements Function<Long,Long>{

        @Override
        public Long apply(Long n) {
            return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        }
    }

}
