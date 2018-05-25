package com.chen.java8.example.stream;

/**
 * FileName: Dish
 * Author:   SunEee
 * Date:     2018/5/25 11:01
 * Description:
 */
public class Dish {
    public enum Type{MEAT,FISH,OTHER}

    private final String name;
    private final boolean vege;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vege, int calories, Type type) {
        this.name = name;
        this.vege = vege;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVege() {
        return vege;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vege=" + vege +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }
}
