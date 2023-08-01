package com.zhou.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhou.common.TestEntity;
import lombok.SneakyThrows;
import scala.Int;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhouyuanke
 * @date 2022/9/9 16:28
 */
public class Test {

    static boolean flag = true;

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    @SneakyThrows
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(2), r -> new Thread(r, "myThread"), new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + " " + i);
            }
            semaphore.release();
        });
        executor.execute(() -> {
            threadLocal.set("test");
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + " test " + i);
            }
            System.out.println(threadLocal.get());
            semaphore.release();
        });
        Future<String> future = executor.submit(() -> "test");
        System.out.println(future.get());
        test5();
    }

    /**
     * 死锁
     */
    private static void test5() {
        Object lock1 = new Object();
        Object lock2= new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                    System.out.println("lock1 test1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("lock2 test1");
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(1000);
                    System.out.println("lock2 test2");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {
                    System.out.println("lock1 test2");
                }
            }
        }, "t1");
        t1.start();
        t2.start();
    }

    /**
     * 多线程的三种方法
     * 实现Runnable方法
     * 实现Callable方法
     * 继承Thread类
     */
    private static void test4() throws InterruptedException {
        Thread t2 = new Thread(() -> {
            while (flag) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("test");
            }
        });
        t2.start();
        Thread.sleep(1000);
        flag = false;
        Thread cur = Thread.currentThread();
        System.out.println(cur.getName());
    }

    class testThread extends Thread {
        @Override
        public void run() {
            System.out.println();
        }
    }

    private static void test3() {
        TestEntity entity = new TestEntity();
        entity.setCode(1);
        entity.setMap(null);
        Map<Integer, String> map = Optional
                .ofNullable(entity)
                .map(TestEntity::getMap)
                .orElseGet(Collections::emptyMap);
        System.out.println(map.get(1));
    }

    private static void test2() {
        String str = "pwwkew";
        System.out.println(str.substring(0, 2));
        System.out.println(str.substring(3));
        System.out.println(str.contains("w"));
        System.out.println(str.charAt(3));
        System.out.println(str.length());
        System.out.println(str.indexOf(3));
        System.out.println(str.substring(3, 4));
        System.out.println(str.toString());
    }

    private static void test1() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
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