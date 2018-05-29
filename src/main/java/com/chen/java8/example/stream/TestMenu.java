package com.chen.java8.example.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * FileName: TestMenu
 * Author:   SunEee
 * Date:     2018/5/25 11:06
 * Description: 流的写法
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
        for (int i = 0; i < 100; i++) {
            dishes.add(new Dish("a", true, i, Dish.Type.FISH));
        }
        long c = System.currentTimeMillis();
        //List<String> strings = new ArrayList<>();
        dishes.sort(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o2.getCalories() - o1.getCalories();
            }
        });
        long d = System.currentTimeMillis();
        System.out.println(d - c + "-----" + dishes.get(0));
        long a = System.currentTimeMillis();
        //Optional<Integer> reduce = dishes.stream().map(Dish::getCalories).reduce(Integer::max);
        dishes.sort(Comparator.comparing(Dish::getCalories));
        //Integer integer = reduce.orElse(0);
        long b = System.currentTimeMillis();
        System.out.println(b - a + "-----" + dishes.get(0));

    }
}
