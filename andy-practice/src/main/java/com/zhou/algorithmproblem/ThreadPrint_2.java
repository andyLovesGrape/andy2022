package com.zhou.algorithmproblem;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhouyuanke
 * @date 2023/7/28
 * 两个线程交替打印100
 * 使用wait和notify，两个线程交替打印，打印完成进入wait状态，等待另一线程唤醒
 */
public class ThreadPrint_2 {

    AtomicInteger integer = new AtomicInteger(1);

    @Test
    public void test() {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new MyThread(lock1, lock2).start();
        new MyThread(lock2, lock1).start();
        while (integer.get() <= 100) {

        }
    }

    public static void main(String[] args) {

    }

    class MyThread extends Thread {

        private Object lock1;
        private Object lock2;

        public MyThread(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            while (integer.get() <= 100) {
                System.out.println(Thread.currentThread() + " " + integer.get());
                integer.addAndGet(1);
                synchronized (lock2) {
                    lock2.notify();
                }
                synchronized (lock1) {
                    try {
                        lock1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
