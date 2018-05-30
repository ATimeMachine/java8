package com.chen.java8.example.paralle;

import java.util.concurrent.RecursiveTask;

/**
 * FileName: ForkJoinSumCalculator
 * Author:   SunEee
 * Date:     2018/5/29 14:04
 * Description: 并行
 */
public class ForkJoinSum extends RecursiveTask<Long>{

    public static final long MIN_SIZE  = 1000;
    private final long[] longs;
    private final int start;
    private final int end;

    public ForkJoinSum(long[] longs) {
        this(longs,0,longs.length);
    }

    private ForkJoinSum(long[] longs, int start, int end) {
        this.longs = longs;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= MIN_SIZE) {
            return getaLong();
        }

        ForkJoinSum leftTask = new ForkJoinSum(longs, start, start + length / 2);
        leftTask.fork();//利用FrokJoinPool线程异步执行新任务

        ForkJoinSum rightTask = new ForkJoinSum(longs, start + length / 2, end);
        Long rightResult = rightTask.compute();//同步执行第二个子任务，有可能进一步递归划分
        Long leftResult = leftTask.join();//读取第一个子任务的结果，如果未完成就等待

        return rightResult + leftResult;
    }

    private Long getaLong() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += longs[i];
        }
        return sum;
    }
}
