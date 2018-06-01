package com.chen.java8.example.futureupdate;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FileName: TestFutureUpdate
 * Author:   SunEee
 * Date:     2018/6/1 10:26
 * Description:
 */
public class TestFutureUpdate {
    private List<Store> stores = Arrays.asList(new Store("A"),
            new Store("B"),
            new Store("C"),
            new Store("D"),
            new Store("E")/*,
            new Store("F"),
            new Store("G"),
            new Store("H"),
            new Store("I")*/
    );

    //使用合适的线程池
    private final Executor executor = Executors.newFixedThreadPool(Math.min(stores.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r); //一定要传r进去
            //System.out.println(shops.size());
            thread.setDaemon(true); //设置为守护线程，这种方式可以让程序关停。
            return thread;
        }
    });
    @Test
    public void test1() {
        //printTime(() -> findPrices("myPhone27S"));
        printTime(() -> findPriceAsync("myPhone27S"));
    }

    private static  <T> void printTime(Supplier<T> t) {
        long start = System.nanoTime();
        System.out.println(t.get());
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("调用时间：" + invocationTime);
    }

    public List<String> findPrices(String product) { //查找价格并计算折后价格
       return stores.stream().map(store -> store.getPrice(product)).map(Quote::parse)
               .map(Discount::applyDiscount).collect(Collectors.toList());
    }

    public List<String> findPriceAsync(String product) {
        List<CompletableFuture<String>> futureList = stores.stream()
                .map(store -> CompletableFuture.supplyAsync(() -> store.getPrice(product), executor))//开启异步
                .map(future -> future.thenApply(Quote::parse)) //与上面同步执行，接着引用别的方法
                //thenCompose是专门将第一个线程的结果作为参数传给第二个异步操作而用
                //开启新的异步
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),executor)))
                .collect(Collectors.toList());
        return futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    //响应式异步
    public Stream<CompletableFuture<String>> findPriceStream(String product) {
        return stores.stream()
                .map(store -> CompletableFuture.supplyAsync(() -> store.getPrice(product),executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),executor)));
    }

    //测试响应式异步
    @Test
    public void test2() {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPriceStream("myPhone27S")
                .map(future -> future.thenAccept(s -> System.out.println( //关键方法：thenAccept()
                        s + "   使用时间是：" + (System.nanoTime() - start) / 1_000_000)))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join(); //获取全部结果 //就获取其中一个最快的结果可以用 anyOf()
        System.out.println("最终用时：" +(System.nanoTime() - start) / 1_000_000);
    }
}
