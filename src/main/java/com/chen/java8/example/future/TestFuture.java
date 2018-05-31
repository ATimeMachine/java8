package com.chen.java8.example.future;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * FileName: TestFuture
 * Author:   SunEee
 * Date:     2018/5/31 13:52
 * Description:
 */
public class TestFuture {
    List<Shop> shops = Arrays.asList(new Shop("A"),
            new Shop("B"),
            new Shop("C"),
            new Shop("D"),
            new Shop("E"),
            new Shop("F"),
            new Shop("G"),
            new Shop("H"),
            new Shop("I")
    );

    //使用合适的线程池
    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread();
            //System.out.println(shops.size());
            thread.setDaemon(true); //设置为守护线程，这种方式可以让程序关停。
            return thread;
        }
    });


    public static void main(String[] args) {
        TestFuture testFuture = new TestFuture();
        printTime((a) ->a.findPrice3("myPhone27S"),testFuture );//使用顺序执行的异步执行
    }

    @Test
    public void test1() {
        Shop shop = new Shop("Tom");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("调用时间：" + invocationTime);

        //做其他事情
        for (int i = 0; i < 10; i++) {
            System.out.print("做其他事情" + i + "->");
        }
        System.out.println();
        try {
            //最多等待1秒钟，否则退出，虽然是好，但是无法知道发生什么错误，所以一般是获取异步的异常并抛出
            //Double price = futurePrice.get(1,TimeUnit.SECONDS);

            Double price = futurePrice.get();
            System.out.printf("Price is %.4f%n", price);
        } catch (InterruptedException | ExecutionException e) { // TimeoutException
            e.printStackTrace();
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("最后时间：" + retrievalTime);

    }

    @Test
    public void test2() {
        TestFuture testFuture = new TestFuture();
        //printTime(() ->findPrice1("myPhone27S") );//顺序执行 4S
        printTime((a) ->findPrice2("myPhone27S"), testFuture);//并行流 1S
        printTime((a) ->findPrice3("myPhone27S"),testFuture);//使用顺序执行的异步执行
    }

    public static  <T,R> void printTime(Function<T,R > function, T input) {
        long start = System.nanoTime();
        System.out.println(function.apply(input)); //顺序执行 4S
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("调用时间：" + invocationTime);
    }

    public List<String> findPrice1(String product) {
        return shops.stream().map( shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPrice2(String product) {
        return shops.parallelStream().map( shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPrice3(String product){
        //必须分开两个流，因为新的CompletableFuture只有在前一个操作完成后才创建，要不然等同与顺序执行
        List<CompletableFuture<String>> futureList = shops.stream().
                map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product)))
                .collect(Collectors.toList());

       return futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


    public List<String> findPrice4(String product){ //上面方法整理
        List<CompletableFuture<String>> futureList = shops.stream().map(shop -> shop.getResult(product, shop)).collect(Collectors.toList());
        return futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
