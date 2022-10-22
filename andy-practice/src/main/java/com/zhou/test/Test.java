package com.zhou.test;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author zhouyuanke
 * @date 2022/9/9 16:28
 */
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(null + "");
    }

    private static void test2() {
        String str = "pwwkew";
        System.out.println(str.substring(0, 2));
        System.out.println(str.substring(3));
        System.out.println(str.contains("w"));
        System.out.println(str.charAt(3));
        System.out.println(str.length());
        System.out.println(str.indexOf(3));
        System.out.println(str.substring(3, 4));
        System.out.println(str.toString());
    }

    private static void test1() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE, 10);
        System.out.println(calendar.getTimeInMillis());
    }

    private static String handleResponse(String response) {
        Object o = Optional.ofNullable(response)
                .map(JSON::parseObject)
                .map(it -> it.get("data"))
                .map(it -> JSON.parseArray(it.toString()))
                .map(it -> it.getJSONObject(0))
                .map(it -> it.get("stats"))
                .map(it -> JSON.parseObject(it.toString()))
                .map(it -> it.get("max"))
                .orElse(null);
        System.out.println(o);
        return ((BigDecimal) o).divide(new BigDecimal(60), 0, BigDecimal.ROUND_DOWN).toString();
    }
}