package com.zhou.cache;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyuanke
 * @date 2022/10/23 18:07
 */
public class GuavaCacheDemo {
    private static final int THREE = 3;
    private static Map<Integer, String> dbData = new HashMap<>(3);
    static {
        dbData.put(1, "shanghai");
        dbData.put(2, "beijing");
        dbData.put(3, "shenzhen");
    }

    private static ListeningExecutorService poolExecutor1 = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

    @SneakyThrows
    private static void sleep(long millis) {
        Thread.sleep(millis);
    }

    private static String getCityFromDb(Integer code) {
        sleep(3000);
        return dbData.get(code) + " " + System.currentTimeMillis();
    }

    @SneakyThrows
    private static void createDemo() {
         LoadingCache<Integer, String> guavaLoadingCache = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer code) {
                        return getCityFromDb(code);
                    }
                });

         Cache<Integer, String> guavaCallableCache = CacheBuilder
                .newBuilder()
                .build();
        System.out.println(guavaLoadingCache.get(1));
        System.out.println(guavaCallableCache.get(1, () -> getCityFromDb(1)));
    }

    private static void querySameOverdueKey() {
        LoadingCache<Integer, String> guavaLoadingCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer code) {
                        return getCityFromDb(code);
                    }
                });
        for (int i = 0; i < THREE; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println(guavaLoadingCache.get(1));
                }
            }).start();
        }
    }

    @SneakyThrows
    private static void queryDiffOverdueKey() {
        LoadingCache<Integer, String> guavaCache = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(3000, TimeUnit.MILLISECONDS)
                .recordStats()
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer code) {
                        System.out.println("load");
                        return getCityFromDb(code);
                    }

                    @Override
                    public ListenableFuture<String> reload(Integer code, String oldValue) {
                        System.out.println("reload");
                        return poolExecutor1.submit(() -> getCityFromDb(code));
                    }
                });
        System.out.println("————— init time is " + new Date().getSeconds() + " ————");
        System.out.println("the city of code 1 is " + guavaCache.get(1) + ", time is " + new Date().getSeconds());
        System.out.println("the city of code 2 is " + guavaCache.get(2) + ", time is " + new Date().getSeconds());
        System.out.println("the city of code 3 is " + guavaCache.get(3) + ", time is " + new Date().getSeconds());
        sleep(3500);
        System.out.println("————— first time is " + new Date().getSeconds() + " ————");
        queryCache(guavaCache);
        sleep(2000);
        System.out.println("————— second time is " + new Date().getSeconds() + " ————");
        queryCache(guavaCache);
        System.out.println(guavaCache.stats());
        guavaCache.invalidateAll();
        System.out.println(guavaCache.get(1));
    }

    private static void queryCache(LoadingCache<Integer, String> guavaLoadingCache) {
        for (int i = 1; i <= THREE; i++) {
            int finalI = i;
            poolExecutor1.execute(() -> {
                try {
                    System.out.println("the city of code " + finalI + " is " +
                            guavaLoadingCache.get(finalI) + ", time is " + new Date().getSeconds());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @SneakyThrows
    private static void expireAndRefreshDemo() {
        LoadingCache<Integer, String> expireCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                .removalListener(removalNotification ->
                        System.out.println("the key " + removalNotification.getKey() + " is removed"))
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) {
                        return getCityFromDb(integer);
                    }
                });
        expireCache.put(1, "init value");
        System.out.println(expireCache.get(1));
        Thread.sleep(1500);
        System.out.println(expireCache.get(1));
        System.out.println("————————————————");

        LoadingCache<Integer, String> refreshCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(1000, TimeUnit.MILLISECONDS)
                .removalListener(removalNotification ->
                        System.out.println("the key " + removalNotification.getKey() + " is removed"))
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) {
                        return getCityFromDb(integer);
                    }
                });
        System.out.println(refreshCache.get(1));
        Thread.sleep(1500);
        System.out.println(refreshCache.get(1));
    }

    @SneakyThrows
    private static void expireDemo() {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1800, TimeUnit.MILLISECONDS)
                .expireAfterAccess(1300, TimeUnit.MILLISECONDS)
                .removalListener(removalNotification ->
                        System.out.println("the key " + removalNotification.getKey() + " is removed"))
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) {
                        return getCityFromDb(integer);
                    }
                });
        cache.put(1, "init value");
        System.out.println(cache.get(1));
        Thread.sleep(1000);
        System.out.println(cache.get(1));
        Thread.sleep(1000);
        System.out.println(cache.get(1));
        Thread.sleep(1500);
        System.out.println(cache.get(1));
    }
}
