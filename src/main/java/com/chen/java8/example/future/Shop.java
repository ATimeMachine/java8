package com.chen.java8.example.future;

import java.util.Random;
import java.util.concurrent.*;


/**
 * FileName: Shop
 * Author:   SunEee
 * Date:     2018/5/31 13:26
 * Description: 商店
 */
public class Shop {
    private final String name;

    Shop(String name) {
        this.name = name;
    }



    public double getPrice(String product) {
        return this.calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>(); //异步对象实例
        new Thread(() -> {
            try {
                double price = this.calculatePrice(product);
                futurePrice.complete(price);// 经过计算后，设置返回值
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);//有异常抓住并抛出
            }
        }).start();
        return futurePrice; //无需等待，直接返回Future对象，值在上面设置进去
    }

    public CompletableFuture<Double> getPriceSupplyAsync(String product) { //升级版
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public CompletableFuture<String> getResult(String product,Shop shop,Executor executor) {
        if (null != executor){
            return CompletableFuture.supplyAsync(() -> getShopInfo(product,shop),executor);//使用自定义的线程池
        }else {
            return CompletableFuture.supplyAsync(() -> getShopInfo(product,shop));//使用自定义的线程池
        }
    }

    private String getShopInfo(String product,Shop shop) {
        //很多操作
        return  shop.getName() + " price is " + shop.getPrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public String getName() {
        return name;
    }

}
