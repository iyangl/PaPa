package com.dasheng.papa.mvp.home.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.HomeResponseBean;

import rx.Observable;

public interface HomeCategoryContact {

    public static interface View extends BaseView {
        void onRefresh(ApiBean<HomeResponseBean> apiBean);

        void onLoadMoreSuccess(ApiBean<HomeResponseBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiBean<HomeResponseBean>> refresh(String type_id,
                                                      String status);

        Observable<ApiBean<HomeResponseBean>> loadMore(String type_id,
                                                       String page);
    }

}
