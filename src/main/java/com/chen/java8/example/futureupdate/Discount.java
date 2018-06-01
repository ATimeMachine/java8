package com.chen.java8.example.futureupdate;

/**
 * FileName: Discount
 * Author:   SunEee
 * Date:     2018/6/1 9:57
 * Description: 折扣
 */
public class Discount {
    public enum Code{
        NONE(0),SILVER(5),GOLD(10),PLATINUM(15) , DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getStoreName() + " price is: " + apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static String apply(double price,Code code) {
        double v = price * (100 - code.percentage) / 100;
        Store.delay();
        return String.format("%.2f", v);
    }


}
