package com.chen.java8.example.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * FileName: Shop
 * Author:   SunEee
 * Date:     2018/5/31 13:26
 * Description: 商店
 */
public class Shop {
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public double getPrice(String product) {
        return this.calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = this.calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
