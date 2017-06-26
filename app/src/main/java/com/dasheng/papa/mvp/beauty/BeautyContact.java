package com.dasheng.papa.mvp.beauty;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ImgBean;

import rx.Observable;

public interface BeautyContact {

    public static interface View extends BaseView {
        void onRefresh(ApiSingleResBean<ImgBean> apiBean);

        void onLoadMoreSuccess(ApiSingleResBean<ImgBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiSingleResBean<ImgBean>> refresh();

        Observable<ApiSingleResBean<ImgBean>> loadMore(int page);
    }
}
