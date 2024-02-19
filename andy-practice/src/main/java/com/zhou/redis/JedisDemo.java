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
        System.out.println(jedis.ttl("table:stu3"));
        System.out.println(jedis.hget("table", "stu3"));
    }
}
