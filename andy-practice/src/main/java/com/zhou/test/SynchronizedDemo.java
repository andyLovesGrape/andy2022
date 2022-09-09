package com.zhou.test;

/**
 * @author zhouyuanke
 * @date 2022/9/8 23:32
 */
public class SynchronizedDemo {
    private static final int TEN = 10;

    static class TestRunnable implements Runnable {
        private String name;

        public TestRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < TEN; i++) {
                System.out.println(this.name + " no " + i);
            }
            synchronized (this) {
                for (int i = 0; i < TEN; i++) {
                    System.out.println(this.name + " yes " + i);
                }
            }
        }
    }

    public static void main(String[] args) {
        TestRunnable thread = new TestRunnable("synchronized");
        new Thread(thread).start();
        new Thread(thread).start();
    }
}
