package com.zhou.test;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.junit.Test;
import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * @author zhouyuanke
 * @date 2022/10/26 11:03
 */
public class HystrixTest<R> extends HystrixObservableCommand<R> {

    public HystrixTest() {
        super(HystrixCommandGroupKey.Factory.asKey(HystrixTest.class.getSimpleName()));
    }

    @Override
    protected Observable<R> construct() {
        System.out.println("construct");
        return ReplaySubject.create();
    }

    @Test
    public void test() {
        super.toObservable();
        System.out.println("over");
    }
}

