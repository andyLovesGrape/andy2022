package com.zhou.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author zhouyuanke
 * @date 2022/9/19 10:01
 */
public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());
        jedis.set("name", "andy");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(jedis.get(key));
        }
    }
}
