package com.dasheng.papa.rx;

import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.BaseView;
import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper(final BaseView view) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> transformer) {
                return transformer.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {//显示进度条
                                view.onShowLoading();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(new Action0() {
                            @Override
                            public void call() {
                                view.onLoadingDismiss();//隐藏进度条
                            }
                        }).compose(RxUtils.<T>bindToLifecycle(view));
            }
        };
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).<T>bindToLifecycle();
        } else if (view instanceof BaseFragment) {
            return ((BaseFragment) view).<T>bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }

}
