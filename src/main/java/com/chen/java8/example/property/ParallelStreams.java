package com.chen.java8.example.property;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * FileName: ParallelStreams
 * Author:   SunEee
 * Date:     2018/5/29 10:49
 * Description:
 */
public class ParallelStreams {
    public static long sequentialSum(long n) {
        //return Stream.iterate(1L,i -> i+1).limit(10).collect(Collectors.reducing(0L,Long::sum));
        return Stream.iterate(0L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    public static long sequentialSum2(long n) {
        return LongStream.rangeClosed(1,n).reduce(0L,Long::sum);
    }


    public static long iterativeSum(long n){
        long sum = 0L;
        for (long i = 0L; i < n; i++) {
             sum += i;
            
        }
        return sum;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(0L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long paralleSum2(long n) {
        return LongStream.rangeClosed(1,n).parallel().reduce(0L,Long::sum);
    }

}
