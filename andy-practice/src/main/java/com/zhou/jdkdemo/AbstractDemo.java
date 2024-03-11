package com.zhou.jdkdemo;

/**
 * @author: Andy
 * @createTime: 2024-02-28 11:43
 * @description: class
 */
public abstract class AbstractDemo {
    private String msg;

    public void test(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
