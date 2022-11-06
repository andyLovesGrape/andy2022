package com.zhou.completablefuture;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.*;

import static com.zhou.common.Constant.FIVE;

/**
 * @author zhouyuanke
 * @date 2022/10/13 10:42
 */
@Component
public class CompletableFutureDemo {

    private static ThreadPoolExecutor poolExecutor1 = new ThreadPoolExecutor(8, 20, 60,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new DefaultThreadFactory("pool1"),
            new ThreadPoolExecutor.DiscardPolicy());
    private static ThreadPoolExecutor poolExecutor2 = new ThreadPoolExecutor(8, 20, 60,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new DefaultThreadFactory("pool2"),
            new ThreadPoolExecutor.DiscardPolicy());

    private static void mainDemo() {
        System.out.println("task begin");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            printf("future1 begin");
            sleep(1000);
            printf("future1 is ok");
            return "future1";
        }, poolExecutor1);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            printf("future2 begin");
            sleep(2000);
            printf("future2 is ok");
            return "future2";
        }, poolExecutor2);
        CompletableFuture<String> future3 = future2
                .thenCombine(future1, (s1, s2) -> {
                    printf("future3 thenCombine begin");
                    sleep(1000);
                    printf("future3 thenCombine is ok");
                    return Joiner.on(" combine ").join(s1, s2);
                }).thenCompose(str -> CompletableFuture.supplyAsync(() -> {
                    printf("future3 thenCompose begin");
                    sleep(1000);
                    printf("future3 thenCompose is ok");
                    return str + " is composed";
                }));
        System.out.println("do something other");
        System.out.println("result is " + future3.join());
    }

    @SneakyThrows
    private static void sleep(long millis) {
        Thread.sleep(millis);
    }

    private static void printf(String msg) {
        System.out.println(msg + ", time is " + new Date().getSeconds() + ", thead is " + Thread.currentThread().getName());
    }

    @SneakyThrows
    private static void limiterDemo() {
        // 每秒执行的任务不能超过2个,这里其实是将一秒钟进行拆分判断的,也就是第一个请求是0s,第二个请求时间必须大于0.5s,将流量平均
        RateLimiter rateLimiter = RateLimiter.create(2.0);
        int n = 450;
        for (int i = 0; i < FIVE; i++) {
            boolean token = rateLimiter.tryAcquire();
            Double time = Double.valueOf(n * i) / 1000;
            if (token) {
                System.out.println("task " + i + " pass, time is " + time);
            } else {
                System.out.println("task " + i + " refuse, time is " + time);
            }
            Thread.sleep(n);
        }
    }

    @SneakyThrows
    private static void createDemo() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("hello world");
        System.out.println(future1.join());
        CompletableFuture future2 = CompletableFuture.runAsync(() -> {
            printf("runAsync " + "random number: " + new Random().nextInt(10));
        }, poolExecutor1);
        future2.join();
        CompletableFuture future3 = CompletableFuture.supplyAsync(() -> {
            printf("supplyAsync");
            return "random number: " + new Random().nextInt(10);
        });
        System.out.println(future3.join());
    }

    private static void getDemo() {
        // join和get的区别在于join方法抛出的是RuntimeException,不需要显式进行处理,上面用get就需要显式捕获异常
        CompletableFuture future = CompletableFuture.completedFuture("hello world");
        try {
            System.out.println(future.get());
            System.out.println(future.get(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("get time out");
        }
        System.out.println(future.join());
        System.out.println(future.getNow("default"));
    }

    @SneakyThrows
    private static void futureDemo() {
        // runnable 接口没有返回值，callable 接口有返回值
        Callable callable = () -> "world";
        FutureTask<String> stringFuture = new FutureTask<String>(callable);
        Thread thread = new Thread(stringFuture);
        thread.start();
        System.out.println(stringFuture.get());
    }

    private static void thenDemo() {
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("hello world");
        CompletableFuture future2 = future1
                .thenApplyAsync(s -> {
                    printf("thenApplyAsync");
                    return s + ", java";
                }, poolExecutor1)
                .thenAcceptAsync(s -> {
                    printf("thenAcceptAsync, " + s);
                })
                .thenRun(() -> {
                    printf("thenRunAsync just run");
                });
        future2.join();
    }

    private static void completeDemo() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "hello world";
        });
        future1.complete("java");
        System.out.println(future1.join());
        future1.completeExceptionally(new RuntimeException("test"));
        System.out.println(future1.join());
    }

    private static void otherThenDemo() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "hello world";
        }, poolExecutor1);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "java";
        }, poolExecutor2);
        CompletableFuture<String> future3 = future2.applyToEither(CompletableFuture.supplyAsync(() -> "test"), s -> {
            printf("applyToEither");
            return "applyToEither result: " + s;
        });
        System.out.println(future3.join());
        CompletableFuture future4 = future2.acceptEither(future1, s -> printf("acceptEither string is " + s));
        future4.join();
    }

    private static void otherDemo() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "hello world";
        });
        sleep(1000);
        future1.complete("java");
        System.out.println(future1.join());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "hello world";
        });
        future2.completeExceptionally(new RuntimeException("completeExceptionally"));
        try {
            System.out.println(future2.join());
        } catch (Exception e) {
            System.out.println("error message is: " + e.getMessage());
        }
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "hello world";
        }).whenComplete((s, throwable) -> System.out.println("future3 is ok, result is " + s));
        future3.cancel(true);
        System.out.println(future3.isCompletedExceptionally());
    }

    private static void allOfDemo() {
        CompletableFuture<Integer> num1 = CompletableFuture.supplyAsync(() -> new Random().nextInt(10));
        CompletableFuture<Integer> num2 = CompletableFuture.supplyAsync(() -> new Random().nextInt(10));
        CompletableFuture<Integer> num3 = CompletableFuture.supplyAsync(() -> new Random().nextInt(10));
        System.out.println(CompletableFuture.allOf(num1, num2, num3)
                .thenApply((Function<Void, Object>) unused -> {
                    return "num1: " + num1.join() + " num2: " + num2.join() + " num3: " + num3.join();
                }).join());
    }

    public static void main(String[] args) {
        allOfDemo();
    }
}