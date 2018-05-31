package com.chen.java8.example.future;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * FileName: TestFuture
 * Author:   SunEee
 * Date:     2018/5/31 13:52
 * Description:
 */
public class TestFuture {

    @Test
    public void test1() {
        Shop shop = new Shop();
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("调用时间：" + invocationTime);

        for (int i = 0; i < 10; i++) {
            System.out.print("做其他事情" + i + "->");
        }
        System.out.println();
        try {
            Double price = futurePrice.get();
            System.out.printf("Price is %.2f%n",price);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("最后时间：" + retrievalTime);

    }
}
