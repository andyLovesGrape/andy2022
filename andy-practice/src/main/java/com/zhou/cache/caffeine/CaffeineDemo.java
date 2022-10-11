package com.zhou.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouyuanke
 * @date 2022/9/21 23:17
 */
public class CaffeineDemo {

    private Cache<String, Object> myCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .expireAfterAccess(1000, TimeUnit.SECONDS)
            .maximumSize(10)
            .build();
    
}
