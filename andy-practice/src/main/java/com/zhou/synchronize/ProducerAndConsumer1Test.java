package com.zhou.synchronize;

import org.junit.Test;

/**
 * 构建消息队列 MessageQueen
 * 消息队列的添加消息和消费消息方法被 synchronized 关键词修饰，保证多线程操作的一致性
 * 创建生产者和消费者，实现 Runnable 接口，分别调用消息队列的添加消息和消费消息方法
 *
 * synchronized 锁的是添加消息和消费消息的方法
 *
 * @author zhouyuanke
 * @date 2022/9/14 22:20
 */
public class ProducerAndConsumer1Test {
    private int FIVE = 5;

    public class MessageQueen {
        private int count;

        private int capacity = 10;

        public synchronized void putMessage() {
            while (count >= capacity) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ++count;
            System.out.println("[" + Thread.currentThread().getName() + "] 生产了一条消息,当前消息总数: " + count);
            this.notifyAll();
        }

        public synchronized void takeMessage() {
            while (count <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            --count;
            System.out.println("[" + Thread.currentThread().getName() + "] 消费了一条消息,当前消息总数: " + count);
            this.notifyAll();
        }
    }

    public class Consumer implements Runnable {
        private MessageQueen messageQueen;

        public Consumer(MessageQueen messageQueen) {
            this.messageQueen = messageQueen;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageQueen.putMessage();
            }
        }
    }

    public class Producer implements Runnable {
        private MessageQueen messageQueen;

        public Producer(MessageQueen messageQueen) {
            this.messageQueen = messageQueen;
        }

        @Override
        public void run() {
            for (int i = 0; i < FIVE; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageQueen.takeMessage();
            }
        }
    }

    @Test
    public void test() {
        MessageQueen messageQueen = new MessageQueen();
        for (int i = 0; i < FIVE; i++) {
            new Thread(new Producer(messageQueen)).start();
            new Thread(new Consumer(messageQueen)).start();
        }
        for(;;){}
    }
}