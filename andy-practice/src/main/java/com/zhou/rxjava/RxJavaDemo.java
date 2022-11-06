package com.zhou.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * @author zhouyuanke
 * @date 2022/10/25 19:44
 */
public class RxJavaDemo {

    public static void main(String[] args) {
        ReplaySubject<Integer> subject = ReplaySubject.create();
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.onComplete();
        subject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
