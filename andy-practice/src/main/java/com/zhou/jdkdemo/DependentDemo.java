package com.zhou.jdkdemo;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @createTime: 2024-01-27 19:55
 * @description: 依赖demo
 */
public class DependentDemo {

    private static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            stringThreadLocal.set("hello");
            integerThreadLocal.set(555);
            System.out.println(stringThreadLocal.get());
            System.out.println(integerThreadLocal.get());
            stringThreadLocal.remove();
            System.out.println(stringThreadLocal.get());
            System.out.println(integerThreadLocal.get());
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            integerThreadLocal.set(123);
            System.out.println(stringThreadLocal.get());
            System.out.println(integerThreadLocal.get());
        }).start();
    }
}
