package com.zhou.algorithmproblem;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhouyuanke
 * @date 2023/7/29
 * 两个线程交替打印1-100，使用ReentrantLock和Condition
 */
public class ThreadPrint_3 {

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition cd1 = reentrantLock.newCondition();
    Condition cd2 = reentrantLock.newCondition();

    @Test
    public void test() {
        new MyThread_3(cd1, cd2, 1).start();
        new MyThread_3(cd2, cd1, 2).start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected class MyThread_3 extends Thread{

        private Condition cd1;

        private Condition cd2;

        private int num;

        public MyThread_3 (Condition cd1, Condition cd2, int num) {
            this.cd1 = cd1;
            this.cd2 = cd2;
            this.num = num;
        }

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                while (num <= 100) {
                    System.out.println(Thread.currentThread() + " : " + num);
                    cd1.signal();
                    try {
                        cd2.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    num += 2;
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

}
