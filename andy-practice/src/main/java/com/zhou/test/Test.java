package com.zhou.test;

import com.alibaba.fastjson.JSON;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author zhouyuanke
 * @date 2022/9/9 16:28
 */
public class Test {
    public static void main(String[] args) {
        int num = 2;
        System.out.println(--num);
    }

    private static void test1() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -20);
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