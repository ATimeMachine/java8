package com.chen.java8.example.futureupdate;

import java.util.Random;

/**
 * FileName: Store
 * Author:   SunEee
 * Date:     2018/6/1 10:02
 * Description: 商店
 */
public class Store {

    private final String name;
    private static final Random random = new Random();

    Store(String name) {
        this.name = name;
    }

    public String getPrice(String product) {
        System.out.println(Thread.currentThread().getName());
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s",name,price,code);

    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        int delay = 500 + random.nextInt(2000);//随机延时
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
