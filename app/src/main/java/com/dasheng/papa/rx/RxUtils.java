package com.dasheng.papa.rx;

import android.os.SystemClock;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> transformer) {
                return transformer
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                SystemClock.sleep(300);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
