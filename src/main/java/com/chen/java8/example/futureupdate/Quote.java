package com.chen.java8.example.futureupdate;

/**
 * FileName: Quote
 * Author:   SunEee
 * Date:     2018/6/1 10:11
 * Description:
 */
public class Quote {
    private final String storeName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String storeName, double price, Discount.Code discountCode) {
        this.storeName = storeName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String[] split = s.split(" : ");
        String storeName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);
        return new Quote(storeName, price, code);
    }

    public String getStoreName() {
        return storeName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }

}
