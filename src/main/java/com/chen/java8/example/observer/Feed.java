package com.chen.java8.example.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: Feed
 * Author:   SunEee
 * Date:     2018/5/30 17:37
 * Description:
 */
public class Feed implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(observer -> observer.notify(tweet));
    }
}
