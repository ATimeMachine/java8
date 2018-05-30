package com.chen.java8.example.observer;

/**
 * FileName: Subject
 * Author:   SunEee
 * Date:     2018/5/30 17:36
 * Description:
 */
public interface Subject {
    void registerObserver(Observer observer);

    void notifyObservers(String tweet);
}
