package com.zhou.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.SneakyThrows;
import javax.annotation.Nonnull;
import java.util.concurrent.*;

/**
 * @author zhouyuanke
 * @date 2022/9/21 23:17
 */
public class CaffeineDemo {

    @SneakyThrows
    private static String query(int code) {
        System.out.println("query");
        Thread.sleep(1000);
        return "hello";
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        AsyncLoadingCache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .buildAsync(new AsyncCacheLoader<Integer, String>() {
                    @Nonnull
                    @Override
                    public CompletableFuture<String> asyncLoad(@Nonnull Integer o, @Nonnull Executor executor) {
                        return CompletableFuture.supplyAsync(() -> query(o), executorService);
                    }
                });
        CompletableFuture<String> future = cache.get(1).thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " haha"));
        System.out.println(future.join());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}