package com.zhou.synchronize;

import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 这个同样是用synchronize来实现，只不过这个锁的是消息队列
 * 可以使用ReentrantLock可重入锁，性能比synchronize更好
 *
 * @author zhouyuanke
 * @date 2022/9/14 22:49
 */
public class ProducerAndConsumer2Test {
    private int capacity = 10;
    private int FIVE = 5;
    private int message;

    public class Producer implements Runnable {
        private Queue<Integer> queue;

        public Producer(Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                synchronized (queue) {
                    while (queue.size() >= capacity) {
                        System.out.println("消息队列已满，等待消费");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(message++);
                    System.out.println("[" + Thread.currentThread().getName() + "] 生产了一条消息,当前消息总数: " + queue.size());
                    queue.notifyAll();
                }
            }
        }
    }

    public class Consumer implements Runnable {
        private Queue<Integer> queue;

        public Consumer(Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        System.out.println("消息队列已空，等待生产");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("[" + Thread.currentThread().getName() + "] 消费了一条消息,当前消息总数: " + queue.size());
                    queue.notifyAll();
                }
            }
        }
    }

    @Test
    public void test() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < FIVE; i++) {
            new Thread(new Producer(queue)).start();
            new Thread(new Consumer(queue)).start();
        }
        for(;;){}
    }
}