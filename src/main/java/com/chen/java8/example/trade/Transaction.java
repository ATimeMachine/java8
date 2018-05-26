package com.chen.java8.example.trade;

/**
 * FileName: Transaction
 * Author:   SunEee
 * Date:     2018/5/26 11:09
 * Description: 交易
 */
public class Transaction {
    private final Trader trader;

    private final int year;

    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {

        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}
