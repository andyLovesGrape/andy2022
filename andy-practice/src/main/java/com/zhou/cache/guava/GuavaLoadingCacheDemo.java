package com.zhou.cache.guava;


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
public class GuavaLoadingCache {

    public static void main(String[] args) {
        demo1();
    }

    @SneakyThrows
    public static void demo1() {
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

    public static void demo2() {
        
    }
}
