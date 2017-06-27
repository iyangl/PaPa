package com.dasheng.papa.mvp.home.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public interface HomeCategoryContact {

    public static interface View extends BaseView {
        void onRefresh(ApiListResBean<ResponseItemBean> apiBean);

        void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiListResBean<ResponseItemBean>> refresh(String type_id,
                                                      String status);

        Observable<ApiListResBean<ResponseItemBean>> loadMore(String type_id,
                                                       String page);
    }

}
