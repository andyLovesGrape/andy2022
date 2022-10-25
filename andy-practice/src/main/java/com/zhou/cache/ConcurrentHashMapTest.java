package com.zhou.cache;

import lombok.SneakyThrows;
import org.junit.Test;
import scala.Tuple2;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhouyuanke
 * @date 2022/10/23 16:56
 */
public class ConcurrentHashMapTest {
    private static ConcurrentHashMap<Integer, Tuple2<Long, String>> cache = new ConcurrentHashMap<>(3);
    private static Map<Integer, String> dbData = new HashMap<>(3);
    static {
        dbData.put(1, "shanghai");
        dbData.put(2, "beijing");
        dbData.put(3, "shenzhen");
    }
    private static int expirationTime = 3;
    private static int mill = 1000;

    @Test
    @SneakyThrows
    public void test() {
        System.out.println("the result is " + getCityByCode(1));
        Thread.sleep(1000);
        System.out.println("the result is " + getCityByCode(1));
        Thread.sleep(3000);
        System.out.println("the result is " + getCityByCode(1));
        Thread.sleep(1000);
        System.out.println("the result is " + getCityByCode(2));
    }

    private String getCityByCode(int code) {
        if (!cache.containsKey(code)) {
            return getCityFromDb(code);
        }
        Tuple2<Long, String> tuple2 = cache.get(code);
        if (isOverTime(tuple2._1)) {
            System.out.println("cache is over time");
            return getCityFromDb(code);
        } else {
            return tuple2._2;
        }
    }

    private String getCityFromDb(Integer code) {
        String city = dbData.get(code);
        System.out.println("query city " + city + " from db");
        cache.put(code, new Tuple2<>(System.currentTimeMillis(), city));
        return city;
    }

    private boolean isOverTime(Long time) {
        if ((System.currentTimeMillis() - time) / mill > expirationTime) {
            return true;
        }
        return false;
    }
}