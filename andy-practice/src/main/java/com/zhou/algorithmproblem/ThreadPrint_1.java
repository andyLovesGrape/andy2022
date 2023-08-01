package com.zhou.algorithmproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个线程交替打印 1-100
 * 使用volatile关键字
 */
public class ThreadPrint_1 {
    static volatile boolean flag = true;

    public static void main(String[] args) {
        MyThread_1 thread_1 = new MyThread_1(1);
        MyThread_2 thread_2 = new MyThread_2(2);
        thread_1.start();
        thread_2.start();
        List<Integer> nums = new ArrayList<>();
        nums.stream().mapToInt(Integer::intValue).toArray();
    }

    static class MyThread_1 extends Thread {

        private int number;

        public MyThread_1(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number <= 100) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + ": " + number);
                    flag = !flag;
                    number += 2;
                }
            }
        }
    }

    static class MyThread_2 extends Thread {

        private int number;

        public MyThread_2(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number <= 100) {
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + ": " + number);
                    flag = !flag;
                    number += 2;
                }
            }
        }
    }
}