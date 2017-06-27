package com.dasheng.papa.mvp.article;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.VideoDetailBean;

import rx.Observable;

public class ArticleDetailContact {

    public static interface View extends BaseView {
        void onRefreshSuccess(ApiSingleResBean<VideoDetailBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiSingleResBean<VideoDetailBean>> refresh(int id);
    }
}
