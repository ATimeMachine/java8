package com.chen.java8.example.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: TestMenu
 * Author:   SunEee
 * Date:     2018/5/25 11:06
 * Description:
 */
public class TestMenu {
    public static void main(String[] args) {
        /*List<Dish> menu = Arrays.asList(
                new Dish("a", false, 500, Dish.Type.FISH),
                new Dish("b", true, 700, Dish.Type.MEAT),
                new Dish("aa", false, 400, Dish.Type.OTHER),
                new Dish("bbb", true, 5500, Dish.Type.MEAT),
                new Dish("dddd", false, 300, Dish.Type.OTHER),
                new Dish("cc", true, 510, Dish.Type.FISH),
                new Dish("ca", false, 400, Dish.Type.FISH),
                new Dish("cb", false, 100, Dish.Type.MEAT),
                new Dish("aaaa", true, 300, Dish.Type.FISH),
                new Dish("hh", false, 500, Dish.Type.MEAT)
        );*/

        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            dishes.add( new Dish("a", false, i, Dish.Type.FISH));
        }
        long l = System.currentTimeMillis();
        List<String> strings = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.getCalories() > 5000) {
                strings.add(dish.getName());
            }
        }
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l + "-----"+ strings.size());
        long l2 = System.currentTimeMillis();
        List names = dishes.stream().filter(d -> d.getCalories() > 5000).map(Dish::getName).collect(Collectors.toList());
        long l3 = System.currentTimeMillis();
        System.out.println(l3 - l2 + "-----"+ names.size());

    }
}
