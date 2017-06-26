package com.dasheng.papa.mvp.beauty.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;

import rx.Observable;

public interface BeautyListContact {

    public static interface View extends BaseView {
        void onLoadPicsSuccess(ApiSingleResBean<BeautyPicBean> pics);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiSingleResBean<BeautyPicBean>> loadPics(String id);
    }
}
