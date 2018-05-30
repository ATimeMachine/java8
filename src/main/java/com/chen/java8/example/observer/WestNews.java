package com.chen.java8.example.observer;

/**
 * FileName: WestNews
 * Author:   SunEee
 * Date:     2018/5/30 17:35
 * Description:
 */
public class WestNews implements Observer {
    @Override
    public void notify(String tweet) {
        if (null != tweet && tweet.contains("西方")) {
            System.out.println("西方消息" + tweet);
        }
    }
}
