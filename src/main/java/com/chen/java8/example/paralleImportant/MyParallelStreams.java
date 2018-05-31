package com.chen.java8.example.paralleImportant;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * FileName: ParallelStreams
 * Author:   SunEee
 * Date:     2018/5/29 10:49
 * Description:
 */
public class MyParallelStreams {
    public static long sequentialSum(long n) { //速度慢
        //return Stream.iterate(1L,i -> i+1).limit(10).collect(Collectors.reducing(0L,Long::sum));
        return Stream.iterate(0L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }


    public static long sequentialSum2(long n) { //不装箱，速度快
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }


    public static long iterativeSum(long n) { //原始
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) { //并行，但是速度慢
        return Stream.iterate(0L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long paralleSum2(long n) { //不装箱并行
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public long paralleSum3(long n, String s) { //多值使用
        //s = "b"; //还可以改变值
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum) + s.charAt(0);
    }

    public long paralleSum4(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

}
