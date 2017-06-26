package com.dasheng.papa.mvp.home.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.HomeResponseBean;

import rx.Observable;

public interface HomeCategoryContact {

    public static interface View extends BaseView {
        void onRefresh(ApiListResBean<HomeResponseBean> apiBean);

        void onLoadMoreSuccess(ApiListResBean<HomeResponseBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiListResBean<HomeResponseBean>> refresh(String type_id,
                                                      String status);

        Observable<ApiListResBean<HomeResponseBean>> loadMore(String type_id,
                                                       String page);
    }

}
