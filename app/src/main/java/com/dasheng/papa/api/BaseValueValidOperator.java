package com.dasheng.papa.api;

import com.dasheng.papa.bean.ApiBean;

import rx.Observable;
import rx.Subscriber;

/**
 * 对接口返回值进行预处理
 */
public class BaseValueValidOperator<T extends ApiBean>
        implements Observable.Operator<T, T> {

    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(e);
                }
            }

            @Override
            public void onNext(T data) {
                if (!subscriber.isUnsubscribed()) {
                    if (data == null) {
                        subscriber.onError(new Exception("服务器异常"));
                        return;
                    }
                    if (data.getStatus() == 1) {
                        subscriber.onNext(data);
                        return;
                    }
                    subscriber.onError(new Exception(data.getMsg()));
                }
            }
        };
    }

}
