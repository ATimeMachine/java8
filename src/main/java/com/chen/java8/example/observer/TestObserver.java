package com.chen.java8.example.observer;

import org.junit.Test;

/**
 * FileName: TestObserver
 * Author:   SunEee
 * Date:     2018/5/30 17:41
 * Description:
 */
public class TestObserver {
    public static void main(String[] args) {
        Feed feed = new Feed();
        feed.registerObserver(new NorthNews());
        feed.registerObserver(new SouthNews());
        feed.registerObserver(new WestNews());
        feed.notifyObservers("南方下雪");
    }

    @Test
    public void test() {
        Feed feed = new Feed();
        feed.registerObserver((String tweet) -> { //实现接口的方法 notify
            if (null != tweet && tweet.contains("东方")) {
                System.out.println("东方消息：" + tweet);
            }
        });
        feed.registerObserver(new NorthNews());
        feed.registerObserver(new SouthNews());
        feed.registerObserver(new WestNews());
        feed.notifyObservers("东方新闻");
    }
}
