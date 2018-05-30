package com.chen.java8.example.observer;

/**
 * FileName: NorthNews
 * Author:   SunEee
 * Date:     2018/5/30 17:30
 * Description: 北方新闻
 */
public class NorthNews implements Observer {
    @Override
    public void notify(String tweet) {
        if (null != tweet && tweet.contains("北方")) {
            System.out.println("北方新闻消息：" + tweet);
        }
    }
}
