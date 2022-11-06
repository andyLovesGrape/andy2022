package com.zhou.synchronize;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 新建了两个condition，分别对应消息队列满和空的两种情况
 * condition的好处在于，用await()替换wait()、用signal()替换notify()、用signalAll()替换notifyAll()，可以唤醒指定的线程
 *
 * @author zhouyuanke
 * @date 2022/9/15 23:06
 */
public class ProducerAndConsumer3Test {
    private static final int CAPACITY = 10;
    private static final int FIVE = 5;
    private static final Lock lock = new ReentrantLock();
    private static final Condition fullCondition = lock.newCondition();
    private static final Condition emptyCondition = lock.newCondition();

    public class Producer extends Thread {
        private Queue<Integer> queue;

        public Producer(Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                lock.lock();
                try {
                    while (queue.size() >= CAPACITY) {
                        System.out.println("消息队列已满，等待消费");
                        try {
                            fullCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(FIVE);
                    System.out.println("[" + Thread.currentThread().getName() + "] 生产了一条消息,当前消息总数: " + queue.size());
                    fullCondition.signalAll();
                    emptyCondition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public class Consumer extends Thread {
        private Queue<Integer> queue;

        public Consumer (Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        System.out.println("消息队列已空，等待生产");
                        try {
                            emptyCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("[" + Thread.currentThread().getName() + "] 消费了一条消息,当前消息总数: " + queue.size());
                    fullCondition.signalAll();
                    emptyCondition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void test() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < FIVE; i++) {
            new Consumer(queue).start();
            new Producer(queue).start();
        }
        for(;;){}
    }
}
