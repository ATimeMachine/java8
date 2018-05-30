package com.chen.java8.example.observer;

/**
 * FileName: SouthNews
 * Author:   SunEee
 * Date:     2018/5/30 17:33
 * Description: 南方新闻
 */
public class SouthNews implements Observer{

    @Override
    public void notify(String tweet) {
        if (null != tweet && tweet.contains("南方")) {
            System.out.println("南方新闻：     " + tweet);
        }
    }
}
