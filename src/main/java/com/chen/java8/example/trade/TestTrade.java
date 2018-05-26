package com.chen.java8.example.trade;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: TestTrade
 * Author:   SunEee
 * Date:     2018/5/26 11:13
 * Description:
 */
public class TestTrade {
    public static void main(String[] args) {



    }

    private static List<Transaction> getTransactions() {
        Trader rao = new Trader("Rao", "shanghai");
        Trader tom = new Trader("Tom", "huNan");
        Trader li = new Trader("Li", "huBei");
        Trader zi = new Trader("Zi", "aiQing");
        Trader hao = new Trader("Hao", "shanghai");

        return Arrays.asList(
                new Transaction(rao, 2010, 500),
                new Transaction(tom, 2013, 600),
                new Transaction(li, 2016, 300),
                new Transaction(zi, 2013, 300),
                new Transaction(hao, 2012, 100),
                new Transaction(tom, 2011, 200),
                new Transaction(zi, 2010, 400)
        );
    }

    @Test
    public void test1() {
        List<Transaction> transactions = getTransactions();

        long a = System.currentTimeMillis();
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getYear() == 2010) {
                result.add(transaction);
            }
        }
        result.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getValue() - t1.getValue();
            }
        });
        //result.sort(Comparator.comparing(Transaction::getValue).reversed());
        long b = System.currentTimeMillis();
        System.out.println(b - a + "---" + result.get(0));
    }


    @Test
    public void test2() {
        List<Transaction> transactions =  getTransactions();
        long c = System.currentTimeMillis();

        List<Transaction> collect = transactions.stream().filter(transaction -> transaction.getYear() == 2010)
                .sorted(Comparator.comparing(Transaction::getValue).reversed()).collect(Collectors.toList());
        long d = System.currentTimeMillis();
        System.out.println(d - c + "---" + collect.get(0));

    }
}
