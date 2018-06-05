package com.chen.java8.example.ticketrobbing;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: TestTickeRobbing
 * Author:   SunEee
 * Date:     2018/6/5 13:42
 * Description:
 */
public class TestTickeRobbing {
    //private String url;
    public static void main(String[] args) throws Exception {
        String url = "https://www.baidu.com/";
        Map<String, Object> param = new HashMap<>();
        param.put("tn", "98012088_9_dg");
        param.put("ch", "4");

        executeUrl(url, param);

    }

    private static void executeUrl(String url, Map<String, Object> param) throws IOException {
        LocalTime later = LocalTime.now();
        System.out.println(later);
        // LocalTime start = LocalTime.parse("09:59:58");
        // LocalTime end = LocalTime.parse("10:02:00");
        LocalTime start = LocalTime.parse("14:49:00");
        LocalTime end = LocalTime.parse("14:50:00");


        while (later.compareTo(end) < 0) {
            if (later.compareTo(start) > 0) {
                //System.out.println(HttpClientUtils.httpGet(url, param));
                System.out.println("成功");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            later = LocalTime.now();
        }
    }

}
