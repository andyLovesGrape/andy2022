package com.zhou.cache.guava;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyuanke
 * @date 2022/9/23 12:53
 */
public class GuavaLoadingCacheDemo {

    public static void main(String[] args) {
        guavaCallableCache();
    }

    @SneakyThrows
    public static void guavaLoadingCache() {
        LoadingCache<String, List<CityInfo>> listLoadingCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<CityInfo>>() {
                    @Override
                    public List<CityInfo> load(String key) throws Exception {
                        List<CityInfo> cityInfos = MockDb.getCityListFromDb(key);
                        return cityInfos;
                    }
                });
        System.out.println("load from cache once: " + listLoadingCache.get("city"));
        Thread.sleep(2000);
        System.out.println("load from cache twice: " + listLoadingCache.get("city"));
        Thread.sleep(2000);
        System.out.println("load from cache threeTimes: " + listLoadingCache.get("city"));
        Thread.sleep(2000);
        System.out.println("load not exist key from cache: " + listLoadingCache.get("country"));
    }

    @SneakyThrows
    public static void guavaCallableCache() {
        String key = "city";
        Cache<String, List<CityInfo>> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener(removalNotification ->
                        System.out.println("cache expired, remove key: " + removalNotification.getKey())).build();
        System.out.println("load from cache once: " + cache.get(key, () -> MockDb.getCityListFromDb(key)));
        Thread.sleep(2000);
        System.out.println("load from cache twice: " + cache.get(key, () -> MockDb.getCityListFromDb(key)));
        Thread.sleep(2000);
        System.out.println("load from cache three times: " + cache.get(key, () -> MockDb.getCityListFromTail(key)));
        Thread.sleep(2000);
        System.out.println("load not exist key from cache: " + cache.get(key, () -> MockDb.getCityListFromTail("country")));
    }
}